/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.gpio.driver;

import com.jcruz.demos.i2c.I2CUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

/**
 * Interface to Ultrasound HC-SR04 device
 * @author Jose Cruz
 */
public class HCSR04Device {

    private final int PULSE = 10000;        // #10us pulse 10.000 ns
    private final int SPEEDOFSOUND = 34029; // Speed Sound 34029 cm/s

    private GPIOPin trigger = null;
    private GPIOPin echo = null;

    /**
     * Inicialize GPIO to echo and trigger pins
     *
     * @param _trigger
     * @param _echo
     */
    public HCSR04Device(int _trigger, int _echo) {
        try {
            // define device for trigger pin
            trigger = (GPIOPin) DeviceManager.open(new GPIOPinConfig(
                    0, _trigger, GPIOPinConfig.DIR_OUTPUT_ONLY, GPIOPinConfig.MODE_OUTPUT_PUSH_PULL,
                    GPIOPinConfig.TRIGGER_NONE, false));
            // define device for echo pin
            echo = (GPIOPin) DeviceManager.open(new GPIOPinConfig(
                    0, _echo, GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_UP,
                    GPIOPinConfig.TRIGGER_NONE, false));

            I2CUtils.I2Cdelay(500);  //wait for 0.5 seconds
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }

    /**
     * Send a pulse to HCSR04 and compute the echo to obtain distance
     * @return distance in cm/s
     */
    public double pulse() {
        long distance = 0;
        try {
            trigger.setValue(true);         //Send a pulse trigger must be 1 and 0 with a 10 us wait
            I2CUtils.I2CdelayNano(0, PULSE);// wait 10 us
            trigger.setValue(false);
            long starttime = System.nanoTime(); //ns
            long stop = starttime;
            long start = starttime;
            //echo will go 0 to 1 and I need save time for that. 2 seconds difference
            while ((!echo.getValue()) && (start < starttime + 1000000000L * 2)) {
                start = System.nanoTime();
            }
            while ((echo.getValue()) && (stop < starttime + 1000000000L * 2)) {
                stop = System.nanoTime();
            }
            long delta = (stop - start);
            distance = delta * SPEEDOFSOUND;       // echo from 0 to 1 depending object distance
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        return distance / 2.0 / (1000000000L); // cm/s
    }

    /**
     * Free device GPIO
     */
    public void close() {
        try {
            if ((trigger!=null) && (echo!=null)){
                trigger.close();
                echo.close();;
            }   
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }
}
