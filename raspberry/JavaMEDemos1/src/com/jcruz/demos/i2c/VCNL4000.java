/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c;

import jdk.dio.i2cbus.I2CDevice;

/**
 * Commands to control VCNL4000 proximity sensor
 *
 * @author Jose Cuz
 */
public enum VCNL4000 {

    /**
     *
     */
    COMMAND(0x80),
    /**
     *
     */
    PRODUCTID(0x81),
    /**
     *
     */
    IRLED(0x83),
    /**
     *
     */
    AMBIENTPARAMETER(0x84),
    /**
     *
     */
    AMBIENTDATA(0x85),
    /**
     *
     */
    AMBIENTDATA2(0x86),
    /**
     *
     */
    PROXIMITYDATA(0x87),
    /**
     *
     */
    PROXIMITYDATA2(0x88),
    /**
     *
     */
    SIGNALFREQ(0x89),
    /**
     *
     */
    PROXIMITYADJUST(0x8A),
    /**
     *
     */
    MEASUREAMBIENT(0x10),
    /**
     *
     */
    MEASUREPROXIMITY(0x08),
    /**
     *
     */
    AMBIENTREADY(0x40),
    /**
     *
     */
    PROXIMITYREADY(0x20);

    /**
     *
     */
    public int cmd;

    private VCNL4000(int cmd) {
        this.cmd = cmd;
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
        I2CUtils.write(device, (byte) this.cmd, value);
    }
}
