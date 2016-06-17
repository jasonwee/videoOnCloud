/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.gpio.driver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinListener;

/**
 * Flame sensor device
 * @author Jose Cruz
 */
public class DFR0076Device {
    
    private GPIOPin pin = null;

    public DFR0076Device(int pinGPIO) {
        try {
            pin = (GPIOPin) DeviceManager.open(new GPIOPinConfig(
                    0, pinGPIO, GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_UP,
                    GPIOPinConfig.TRIGGER_RISING_EDGE, false));
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }

    public GPIOPin getPin() {
        return pin;
    }
    
    /**
     * Defined listener to flame sensor pin
     *
     * @param pirListener
     */
    public void setListener(PinListener pirListener) {
        try {
            if (pin!=null)
                pin.setInputListener(pirListener);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }
    
    /**
     * Free PIR GPIO
     */
    public void close() {
        try {
            //Remove listener for flame sensor
            if (pin!=null){
                pin.setInputListener(null); 
                pin.close(); 
            }    
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }
    
}
