package org.just4fun.voc;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class BusinessService {
	
	private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
	
	public void someMethod() {
		EtmPoint point = etmMonitor.createPoint("BusinessService:someMethod");
		
		try {
			Thread.sleep((long)((long)(10d * Math.random())));
			nestedMethod();
		} catch (InterruptedException e ) {
			
		} finally {
			point.collect();
		}
	}
	
	public void nestedMethod() {
		EtmPoint point = etmMonitor.createPoint("BusinessService:nestedMethod");
		
		try {
			Thread.sleep((long)(15d * Math.random()));
		} catch (InterruptedException e) {
			
		} finally {
			point.collect();
		}
		
	}

	public static void main(String[] args) {
		BasicEtmConfigurator.configure(true);
		//etmMonitor = EtmManager.getEtmMonitor();
		etmMonitor.start();
		BusinessService bizz = new BusinessService();
		bizz.someMethod();
		bizz.someMethod();
		bizz.someMethod();
		bizz.someMethod();
		bizz.nestedMethod();
		etmMonitor.render(new SimpleTextRenderer());

		etmMonitor.stop();
	}

}
