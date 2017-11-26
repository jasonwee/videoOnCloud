package play.learn.jfreechart;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Flight extends ApplicationFrame {

	public Flight(String title) {
		super(title);
		XYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(560, 370));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}
	
	private XYDataset createDataset() {

	    TimeSeriesCollection dataset = new TimeSeriesCollection();
	    String line = "";
	    
		TimeSeries series1 = new TimeSeries("JSA538");
		TimeSeries series2 = new TimeSeries("CTV527");
		TimeSeries series3 = new TimeSeries("MXD132");
		TimeSeries series4 = new TimeSeries("BBC086");
		TimeSeries series5 = new TimeSeries("TGW713");
		TimeSeries series6 = new TimeSeries("AXM823");
		TimeSeries series7 = new TimeSeries("SIA323");
		TimeSeries series8 = new TimeSeries("THY55");
	    
		try (BufferedReader br = new BufferedReader(new FileReader("flight.csv"))) {
			
			String target = "2017-11-25 16:12:37+0000";
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssZ");
		    Date result =  df.parse(target);  
		    //System.out.println(result);
		    Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		    calendar.setTime(result);
		    //System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		    
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		    //System.out.println(sdf.format(result));

		    
			while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(",");
                
                switch (data[3]) {
                case "JSA538":
                	//System.out.println("data [flight= " + data[3] +" , time= " + data[1] + " , altitude=" + data[2] + "]");
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series1.add(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                	
                case "CTV527":
                	//System.out.println("data [flight= " + data[3] +" , time= " + data[1] + " , altitude=" + data[2] + "]");
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series2.add(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "MXD132":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series3.add(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "BBC086":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series4.add(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "TGW713":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series5.add(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "AXM823":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series6.addOrUpdate(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "SIA323":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series7.addOrUpdate(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                case "THY55":
                	result =  df.parse(data[1]);
        		    calendar.setTime(result);
        		    
                	series8.addOrUpdate(
                			new Second(
                					calendar.get(Calendar.SECOND), 
                					calendar.get(Calendar.MINUTE),
                					calendar.get(Calendar.HOUR_OF_DAY), 
                					calendar.get(Calendar.DAY_OF_MONTH),
                					calendar.get(Calendar.MONTH),
                					calendar.get(Calendar.YEAR))
                			, Double.parseDouble(data[2])
                			);
                }

            }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		dataset.addSeries(series4);
		dataset.addSeries(series5);
		dataset.addSeries(series6);
		dataset.addSeries(series7);
		dataset.addSeries(series8);
		
		return dataset;
	}
	
	private JFreeChart createChart(XYDataset dataset) {
		return ChartFactory.createTimeSeriesChart("Flight altitude over times", "Seconds", "Altitude", dataset, false, true, false);
	}

	public static void main(String[] args) {
		String title = "Flight Altitude";
		Flight demo = new Flight(title);
		demo.pack();
		RefineryUtilities.positionFrameRandomly(demo);
		demo.setVisible(true);
	}

}
