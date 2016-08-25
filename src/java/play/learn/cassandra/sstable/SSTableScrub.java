package play.learn.cassandra.sstable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.db.Directories;
import org.apache.cassandra.db.compaction.LeveledManifest;
import org.apache.cassandra.db.compaction.Scrubber;
import org.apache.cassandra.io.sstable.Component;
import org.apache.cassandra.io.sstable.Descriptor;
import org.apache.cassandra.io.sstable.format.SSTableReader;
import org.apache.cassandra.tools.BulkLoader.CmdLineOptions;
import org.apache.cassandra.utils.OutputHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;


// cassandra 1.2
public class SSTableScrub {
	
	private static final String TOOL_NAME = "sstablescrub";
	private static final String VERBOSE_OPTION = "verbose";
	private static final String DEBUG_OPTION = "debug";
	private static final String HELP_OPTION = "help";
	private static final String MANIFEST_CHECK_OPTION = "manifest-check";
	private static final String MIGRATE_OPTION = "migrate";

	public static void main(String[] args) {
		
		Options options = Options.parseArgs(args);
		
		try {
			OutputHandler handler = new OutputHandler.SystemOutput(options.verbose, options.debug);
			
            if (Directories.sstablesNeedsMigration())
            {
                if (!options.migrate)
                {
                    System.err.println("Detected a pre-1.1 data directory layout.  For this tool to work, a migration " +
                                       "must be performed to the 1.1+ format for directories and filenames.  Re-run " +
                                       TOOL_NAME + " with the --" + MIGRATE_OPTION + " option to automatically " +
                                       "migrate *all* keyspaces and column families to the new layout.");
                    System.exit(1);
                }
                handler.output("Detected a pre-1.1 data directory layout. All keyspace and column family directories " +
                               "will be migrated to the 1.1+ format.");
                Directories.migrateSSTables();
            }
            
            // load keyspace descriptions.
            DatabaseDescriptor.loadSchemas();
			
            if (Schema.instance.getCFMetaData(options.tableName, options.cfName) == null)
                throw new IllegalArgumentException(String.format("Unknown keyspace/columnFamily %s.%s",
                                                                 options.tableName,
                                                                 options.cfName));
            
            // Do not load sstables since they might be broken
            Table table = Table.openWithoutSSTables(options.tableName);
            ColumnFamilyStore cfs = table.getColumnFamilyStore(options.cfName);
            String snapshotName = "pre-scrub-" + System.currentTimeMillis();
            
            Directories.SSTableLister lister = cfs.directories.sstableLister().skipTemporary(true);
            
            List<SSTableReader> sstables = new ArrayList<SSTableReader>();
            
            // snapshot
            for (Map.Entry<Descriptor, Set<Component>> entry : lister.list().entrySet())
            {
                Set<Component> components = entry.getValue();
                // sstable consist of multiple files, data, toc, summary, etc. 
                // if no data(Data.db) or primary index(Index.db), skip it.
                if (!components.contains(Component.DATA) || !components.contains(Component.PRIMARY_INDEX))
                    continue;

                try
                {
                	// dont validate while read the sstable.
                    SSTableReader sstable = SSTableReader.openNoValidation(entry.getKey(), components, cfs.metadata);
                    sstables.add(sstable);

                    // copy files?
                    File snapshotDirectory = Directories.getSnapshotDirectory(sstable.descriptor, snapshotName);
                    sstable.createLinks(snapshotDirectory.getPath());
                }
                catch (Exception e)
                {
                    System.err.println(String.format("Error Loading %s: %s", entry.getKey(), e.getMessage()));
                    if (options.debug)
                        e.printStackTrace(System.err);
                }
            }
            System.out.println(String.format("Pre-scrub sstables snapshotted into snapshot %s", snapshotName));
            
            // If leveled, load the manifest
            LeveledManifest manifest = null;
            if (cfs.directories.tryGetLeveledManifest() != null)
            {
                cfs.directories.snapshotLeveledManifest(snapshotName);
                System.out.println(String.format("Leveled manifest snapshotted into snapshot %s", snapshotName));

                int maxSizeInMB = (int)((cfs.getCompactionStrategy().getMaxSSTableSize()) / (1024L * 1024L));
                manifest = LeveledManifest.create(cfs, maxSizeInMB, sstables);
            }
            
            // scrub
            if (!options.manifestCheckOnly)
            {
                for (SSTableReader sstable : sstables)
                {
                    try
                    {
                        Scrubber scrubber = new Scrubber(cfs, sstable, handler, true);
                        try
                        {
                            scrubber.scrub();
                        }
                        finally
                        {
                            scrubber.close();
                        }

                        if (manifest != null)
                        {
                            if (scrubber.getNewInOrderSSTable() != null)
                                manifest.add(scrubber.getNewInOrderSSTable());

                            List<SSTableReader> added = scrubber.getNewSSTable() == null
                                ? Collections.<SSTableReader>emptyList()
                                : Collections.singletonList(scrubber.getNewSSTable());
                            manifest.replace(Collections.singletonList(sstable), added);
                        }

                        // Remove the sstable (it's been copied by scrub and snapshotted)
                        sstable.markCompacted();
                        sstable.releaseReference();
                    }
                    catch (Exception e)
                    {
                        System.err.println(String.format("Error scrubbing %s: %s", sstable, e.getMessage()));
                        if (options.debug)
                            e.printStackTrace(System.err);
                    }
                }
            }
            
            // Check (and repair) manifest
            if (manifest != null)
                checkManifest(manifest);

            SSTableDeletingTask.waitForDeletions();
            System.exit(0); // We need that to stop non daemonized threads
            
		} catch (Exception e) {
			System.err.println(e.getMessage());
			if (options.debug)
				e.printStackTrace(System.err);
			System.exit(1);
		}

	}
	
	private static void checkManifest(LeveledManifest manifest) {
		System.out.println(String.format("Checking leveled manifest"));
		for (int i = 1; i <= manifest.getLevelCount(); ++i)
			manifest.repairOverlappingSSTables(i);
	}
	
	private static class Options {
		
		public final String tableName; 
		public final String cfName;
		
		public boolean debug;
		public boolean verbose;
		public boolean manifestCheckOnly;
		public boolean migrate;
		
		private Options(String tableName, String cfName) {
			this.tableName = tableName;
			this.cfName = cfName;
		}
		
		public static Options parseArgs(String cmdArgs[]) {
			CommandLineParser parser = new GnuParser();
			CmdLineOptions options = getCmdLineOptions();
			
			try {
				CommandLine cmd = parser.parse(options, cmdArgs, false);
				
				if (cmd.hasOption(HELP_OPTION)) {
					printUsage(options);
					System.exit(0);
				}
				
				String[] args = cmd.getArgs();
				if (args.length != 2) {
					String msg = args.length < 2 ? "Missing arguments" : "Too many arguments";
					System.err.println(msg);
					printUsage(options);
					System.exit(1);
				}
				
				String tableName = args[0];
				String cfName = args[1];
				
				Options opts = new Options(tableName, cfName);
				
				opts.debug = cmd.hasOption(DEBUG_OPTION);
				opts.verbose = cmd.hasOption(VERBOSE_OPTION);
				opts.manifestCheckOnly = cmd.hasOption(MANIFEST_CHECK_OPTION);
				opts.migrate = cmd.hasOption(MIGRATE_OPTION);
				
				return opts;
			} catch (ParseException e) {
				errorMsg(e.getMessage(), options);
				return null;
			}
		}
		
		private static void errorMsg(String msg, CmdLineOptions options) {
			System.err.println(msg);
			printUsage(options);
			System.exit(1);
		}
		
		private static CmdLineOptions getCmdLineOptions() {
			CmdLineOptions options = new CmdLineOptions();
			options.addOption(null, DEBUG_OPTION, "display stack traces");
			options.addOption("v", VERBOSE_OPTION, "verbose output");
			options.addOption("h", HELP_OPTION, "display this help message");
			options.addOption("m", MANIFEST_CHECK_OPTION, "only check and repair the leveled manifest, without actually scrubbing the sstables");
			options.addOption(null, MIGRATE_OPTION, "convert directory layout and filenames to 1.1+ structure");
			return options;
		}
		
		public static void printUsage(CmdLineOptions options) {
			String usage = String.format("%s [options] <keyspace> <column_family>", TOOL_NAME);
			StringBuilder header = new StringBuilder();
			header.append("--\n");
			header.append("Scrub the sstable for the provided column family.");
			header.append("\n--\n");
			header.append("Options are:");
			new HelpFormatter().printHelp(usage, header.toString(), options, "");
		}
	}

}
