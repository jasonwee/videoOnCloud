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
import jdk.dio.gpio.PinListener;

/**
 * PIR Sensor HCSR501 device
 * @author Jose Cuz
 */
public class HCSR501Device {

    private GPIOPin pin = null;

    /**
     * Define GPIO pin to listen for move detected
     *
     * @param pinGPIO
     */
    public HCSR501Device(int pinGPIO) {
        try {
            pin = (GPIOPin) DeviceManager.open(new GPIOPinConfig(
                    0, pinGPIO, GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN,
                    GPIOPinConfig.TRIGGER_RISING_EDGE, false));

            I2CUtils.I2Cdelay(3000);    //wait for 3 seconds
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING, ex.getMessage());
        }
    }

    public GPIOPin getPin() {
        return pin;
    }

    /**
     * Defined listener to pir GPIO pin. Pin change value for some time depends
     * PIR time delay potentiometer adjust.
     *
     * @param pirListener
     */
    public void setListener(PinListener pirListener) {
        try {
            if (pin!=null)
                pin.setInputListener(pirListener);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING, ex.getMessage());
        }
    }

    /**
     * Free PIR GPIO
     */
    public void close() {
        try {
            //Remove listener for GPIO pin of PIR
            if (pin!=null){
                pin.setInputListener(null);
                pin.close();
            }    
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING, ex.getMessage());
        }
    }
}
