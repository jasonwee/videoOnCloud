/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.test;

import com.jcruz.demos.gpio.driver.DFR0076Device;
import com.jcruz.demos.gpio.driver.HCSR04Device;
import com.jcruz.demos.gpio.driver.HCSR501Device;
import com.jcruz.demos.i2c.I2CUtils;
import com.jcruz.demos.log.LoggingHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.midlet.MIDlet;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

/**
 * Midlet to Test all sensors connected to Raspberry Pi
 *
 * @author Jose Cruz
 */
public class TestSensors extends MIDlet {
    
    private LoggingHandler loggerHandler = LoggingHandler.getInstance();

    //Define DFR0076 Device object
    DFR0076Device flame;
    private static final int FLAME_DETECTOR_PIN = 22;
    
    //Define HCSR501 Device object
    HCSR501Device pir;
    private static final int MOTION_DETECTOR_PIN = 24;
        
    //Define HCSR04 Device object
    HCSR04Device hcsr04;
    private static final int TRIGGER_PIN = 23;
    private static final int ECHO_PIN = 17;
    
    //Define execution of read sensors thread
    private volatile boolean shouldRun = true;
    private ReadSensors sensorsTask;
    
    /**
     * Start MIDLet
     */
    @Override
    public void startApp() {
        //Activate Log handler
        loggerHandler.start();
        //Set Logger to INFO
        Logger.getGlobal().setLevel(Level.INFO);
        //Initialize Flame Sensor
        flame = new DFR0076Device(FLAME_DETECTOR_PIN);
        flame.setListener(new FlameSensor());
        //Initialize PIR sensor
        pir = new HCSR501Device(MOTION_DETECTOR_PIN);
        pir.setListener(new PirSensor());
        //Initialize Ultrasound sensor
        hcsr04=new HCSR04Device(TRIGGER_PIN, ECHO_PIN);
        //Start read sensors data thread
        sensorsTask=new ReadSensors();
        sensorsTask.start();
    }

    /**
     * End MIDLet
     * @param unconditional
     */
    @Override
    public void destroyApp(boolean unconditional) {
        Logger.getGlobal().log(Level.INFO,"Ending test program...");
        loggerHandler.close();
        shouldRun=false;
        flame.close();
        pir.close();
        hcsr04.close();
    }
    
     /**
     * Thread to handle client request.
     */
    class ReadSensors extends Thread {
        private double distance=0.0;
        
        @Override
        public void run() {
            while (shouldRun){
                distance = hcsr04.pulse();
                if (distance>0) 
                    System.out.println("Object detected at " + distance + " cm.");
                I2CUtils.I2Cdelay(5000);
            }
        }
    }
    
    //Check flame sensor for flame detect
    private static int waitnext = 1;

    class FlameSensor implements PinListener {

        @Override
        public void valueChanged(PinEvent event) {
            if (event.getValue() && --waitnext == 0) {
                System.out.println("WARNING Flame detected!!!");
                waitnext = 10;
            }
        }
    }

    //Check PIR Sensor for motion detect
    class PirSensor implements PinListener {

        @Override
        public void valueChanged(PinEvent event) {
            if (event.getValue()) {
                System.out.println("WARNING Motion detected!!!");
            }

        }
    }

}
