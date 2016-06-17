/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.test;

import com.jcruz.demos.gpio.driver.DFR0076Device;
import com.jcruz.demos.gpio.driver.HCSR04Device;
import com.jcruz.demos.gpio.driver.HCSR501Device;
import com.jcruz.demos.i2c.I2CUtils;
import com.jcruz.demos.i2c.driver.HMC5883LDevice;
import com.jcruz.demos.i2c.driver.HTU21DDevice;
import com.jcruz.demos.i2c.driver.PCA9685Device;
import com.jcruz.demos.i2c.driver.VCNL4000Device;
import com.jcruz.demos.log.LoggingHandler;
import java.io.IOException;
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
    
    //Part 2 
    //Define HMC5883L compass module object
    HMC5883LDevice hmc;
    //Define Light and proximity sensor object
    VCNL4000Device vcnl;
    //Define Hummidity and Temperature sensor object
    HTU21DDevice htu;
    //Define Control Servo Motors object
    PCA9685Device servo;
           
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
        Logger.getGlobal().log(Level.INFO, "************************************");
        Logger.getGlobal().log(Level.INFO, "*     Starting TestSensors...      *");
        Logger.getGlobal().log(Level.INFO, "************************************");
        
        //Initialize Flame Sensor
        flame = new DFR0076Device(FLAME_DETECTOR_PIN);
        flame.setListener(new FlameSensor());
        //Initialize PIR sensor
        pir = new HCSR501Device(MOTION_DETECTOR_PIN);
        pir.setListener(new PirSensor());
        //Initialize Ultrasound sensor
        hcsr04=new HCSR04Device(TRIGGER_PIN, ECHO_PIN);
        
        //Part 2
        //Initialize Compass Module 
        try {
            hmc = new HMC5883LDevice();
            hmc.setScale(1.3F);
            hmc.setMeasurementMode(HMC5883LDevice.Measurement.Continuous);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE,ex.getMessage());
        }
       
        //Initialize Light and proximity sensor
        try {
            vcnl = new VCNL4000Device();
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE,ex.getMessage());
        }
        
        //Initialize Hummidity and Temperature sensor
        try {
            htu= new HTU21DDevice();
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE,ex.getMessage());
        }
        
        //Initialize control servo motors
        try {
            servo = new PCA9685Device();
            servo.setPWMFreq(60);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE,ex.getMessage());
        }
        
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
        //Part 2
        hmc.close();
        vcnl.close();
        htu.close();
        servo.close();
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
                
                //Part 2
                //Display Heading degrees point North
                if (hmc != null) {
                    System.out.println("Heading degrees:"+hmc.calculateHeading());
                    I2CUtils.I2Cdelay(2000);
                    //Display magnetometer coordinates
                    HMC5883LDevice.MagnetometerRaw values = hmc.readRawAxis();
                    System.out.print("X:" + values.XAxis);
                    System.out.print(" Y:" + values.YAxis);
                    System.out.println(" Z:" + values.ZAxis);
                }
                //Detect objects and ambient light
                if (vcnl!=null){
                    System.out.println("VCNL4000 Ambient light:"+ vcnl.readAmbientLight());
                    vcnl.setProximityAdjust();
                    I2CUtils.I2Cdelay(2000);
                    System.out.println("VCNL4000 Proximity (ctms):"+ vcnl.readProximity());
                }
                //Read temperature and humidity
                if (htu!=null){
                    System.out.println("HTU21D Temperature centigrades:"+ htu.readTemperature());
                    I2CUtils.I2Cdelay(2000);
                    System.out.println("HTU21D Humidity:"+ htu.readHumidity());
                }
                //Move servo motor to right and left
                System.out.println("Servo moves right");
                //Move servo right 
                for (int i=150;i<600;i+=50){
                    servo.setPWM((byte) 0, (short) 0, (short) i);
                    I2CUtils.I2Cdelay(1000);
                }    
                
                System.out.println("Servo moves left");
                //Move servo left 
                for (int i=600;i>150;i-=50){
                    servo.setPWM((byte) 0, (short) 0, (short) i);
                    I2CUtils.I2Cdelay(1000);
                }   
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
