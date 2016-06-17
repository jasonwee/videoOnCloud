/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c.driver;

import com.jcruz.demos.i2c.I2CRpi;
import com.jcruz.demos.i2c.I2CUtils;
import com.jcruz.demos.i2c.PCA9685;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control all servos connected to PCA9685
 *
 * @author Jose Cuz
 */
public class PCA9685Device extends I2CRpi {

    private static final int PWMServoDriverAddr = 0x41;

    private final int PCA9685_SUBADR1 = 0x2;
    private final int PCA9685_SUBADR2 = 0x3;
    private final int PCA9685_SUBADR3 = 0x4;

    private final int ALLLED_ON_L = 0xFA;
    private final int ALLLED_ON_H = 0xFB;
    private final int ALLLED_OFF_L = 0xFC;
    private final int ALLLED_OFF_H = 0xFD;

    /**
     * Define interface to PC9685 PWM and reset it
     *
     * @throws IOException
     */
    public PCA9685Device() throws IOException {
        super(PWMServoDriverAddr);
        reset();
    }

    /**
     * Inicialize PWM
     *
     */
    public void reset() {
        PCA9685.MODE1.write(device, (byte) 0x0);
    }

    /**
     * Set PWM Frequency
     *
     * @param freq
     */
    public void setPWMFreq(float freq) {
        //Serial.print("Attempting to set freq ");
        //Serial.println(freq);

        float prescaleval = 25000000.0F;
        prescaleval /= 4096.0F;
        prescaleval /= freq;
        prescaleval -= 1.0;
        Logger.getGlobal().log(Level.FINE,"Estimated pre-scale: " + prescaleval);
        float prescale = (float) Math.floor(prescaleval + 0.5);
        Logger.getGlobal().log(Level.FINE,"Final pre-scale: " + prescale);

        int oldmode = PCA9685.MODE1.read(device);
        int newmode = (oldmode & 0x7F) | 0x10; // sleep
        PCA9685.MODE1.write(device, (byte) newmode); // go to sleep
        PCA9685.PRESCALE.write(device, (byte) prescale); // set the prescaler
        PCA9685.MODE1.write(device, (byte) oldmode);

        I2CUtils.I2Cdelay(5);
        PCA9685.MODE1.write(device, (byte) (oldmode | 0x80));  //  This sets the MODE1 register to turn on auto increment.
        // This is why the beginTransmission below was not working.
        //  Serial.print("Mode now 0x"); Serial.println(read8(PCA9685_MODE1), HEX);
    }

    /**
     * Set PWM pulse to control servo motor
     *
     * @param num
     * @param on
     * @param off
     */
    public void setPWM(byte num, short on, short off) {
        I2CUtils.write(device, (byte) (PCA9685.LED0_ON_L.cmd + 4 * num), (byte) (on & 0xFF));

        I2CUtils.write(device, (byte) (PCA9685.LED0_ON_H.cmd + 4 * num), (byte) (on >> 8));

        I2CUtils.write(device, (byte) (PCA9685.LED0_OFF_L.cmd + 4 * num), (byte) (off & 0xFF));

        I2CUtils.write(device, (byte) (PCA9685.LED0_OFF_H.cmd + 4 * num), (byte) (off >> 8));

    }

}
