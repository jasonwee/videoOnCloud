/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c;

import jdk.dio.i2cbus.I2CDevice;

/**
 * Define commands to control all servo connected
 *
 * @author jcruz
 */
public enum PCA9685 {

    /**
     *
     */
    MODE1(0x0),
    /**
     *
     */
    PRESCALE(0xFE),
    /**
     *
     */
    LED0_ON_L(0x6),
    /**
     *
     */
    LED0_ON_H(0x7),
    /**
     *
     */
    LED0_OFF_L(0x8),
    /**
     *
     */
    LED0_OFF_H(0x9);

    /**
     *
     */
    public byte cmd;

    private PCA9685(int cmd) {
        this.cmd = (byte) cmd;
    }

    /**
     *
     * @param device
     * @return
     */
    public int read(I2CDevice device) {
        return I2CUtils.read(device, this.cmd);
    }

    /**
     *
     * @param device
     * @param value
     */
    public void write(I2CDevice device, byte value) {
        I2CUtils.write(device, this.cmd, value);
    }

}
