package play.learn.cassandra.sstable;

import java.io.File;

import org.apache.cassandra.config.Config;

import com.google.common.base.Strings;

public class Driver {
	
	static {
		Config.setClientMode(true);
	}

	public static void main(String[] args) {
		
		args = new String[]{"describe", "sstable/ma-837-big-Data.db"};
		
		
		if (args.length == 0) {
			printCommands();
			System.exit(-1);
		}
		switch (args[0].toLowerCase()) {
		case "cqlsh":
			//Cqlsh.main(Arrays.copyOfRange(args, 1, args.length));
		case "describe":
			String path = new File(args[1]).getAbsolutePath();
			try {
				System.out.println("\u001B[1;34m" + path);
				System.out.println(TableTransformer.ANSI_CYAN + Strings.repeat("=", path.length()));
				System.out.println(TableTransformer.ANSI_RESET);
				CassandraUtils.printStats(path, System.out);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "hints":
			//HintsTool.main(Arrays.copyOfRange(args, 1, args.length));
			break;
		default:
			System.err.println("Unknown command: " + args[0]);
			printCommands();
			break;
		}
	}
	
	private static void printCommands() {
		System.err.println("Available commands: describe");
	}

}
