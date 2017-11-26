package play.learn.jfreechart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PieChart_File extends ApplicationFrame {
	
	public PieChart_File(String title) {
		super(title);
		setContentPane(createDemoPanel());
	}
	
	public static JPanel createDemoPanel() {

		String mobilebrands[] = { "Iphone 5S", "Samsung Grand", "MOTO G", "Lumia" };
		StringBuilder out = null;
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			InputStream in = new FileInputStream(new File("/home/jason/test.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			out = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringTokenizer s = new StringTokenizer(out.toString(), ",");
		int i = 0;

		while (s.hasMoreTokens() && (mobilebrands[i] != null)) {
			s.nextToken();
			String current = s.nextToken();
			System.out.println(mobilebrands[i] + " -> " +current);
			dataset.setValue(mobilebrands[i], Double.parseDouble(current));
			i++;
		}

		JFreeChart chart = createChart(dataset);
		return new ChartPanel(chart);
	}

	private static JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Mobile Sales", // chart title
				dataset, // data
				true, // include legend
				true, false);

		return chart;
	}

	public static void main(String[] args) {

		PieChart_File demo = new PieChart_File("Mobile Sales");
		demo.setSize(560, 370);
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
