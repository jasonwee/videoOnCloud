/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.i2cbus.I2CDevice;

/**
 * Functions to read and write to I2C Raspberry Pi bus
 * 
 * @author jcruz
 */
public class I2CUtils {

    /**
     *
     * @param mili
     */
    public static void I2Cdelay(int mili) {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException ex) {
        }
    }

    /**
     *
     * @param mili
     * @param nano
     */
    public static void I2CdelayNano(int mili, int nano) {
        try {
            Thread.sleep(mili, nano);
        } catch (InterruptedException ex) {
        }
    }

    /**
     *
     * @param b
     * @return byte values from -127..128 convert 128..255
     */
    public static int asInt(byte b) {
        int i = b;
        if (i < 0) {
            i += 256;
        }
        return i;
    }

    /**
     *
     * @param device Device connected to I2C bus
     * @param cmd Command send to device
     * @return read an int from a device connected to I2C bus
     */
    public static int read(I2CDevice device, int cmd) {
        ByteBuffer rxBuf = ByteBuffer.allocateDirect(1);
        try {
            device.read(cmd, 1, rxBuf);
        } catch (IOException ex) {
           //Logger.getGlobal().log(Level.WARNING,ex.getMessage());
            ex.printStackTrace();
            
        }
        return asInt(rxBuf.get(0));
    }

    /**
     *
     * @param device Device connected to I2C bus
     * @param cmd Command send to Arduino Due
     * @return read a float from Arduino Due connected to I2C bus. All bytes
     * received must be swamp order
     */
    public static float readFloatArduino(I2CDevice device, int cmd) {
        byte[] b = new byte[4];
        ByteBuffer rxBuf = ByteBuffer.allocateDirect(4);
        try {
            device.read(cmd, 4, rxBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        rxBuf.clear();
        for (int i = 0; i < 4; i++) {
            b[i] = rxBuf.get(3 - i);
        }
        return ByteBuffer.wrap(b).getFloat();
    }

    /**
     *
     * @param device Device connected to I2C bus
     * @param cmd Command send to Arduino Due
     * @return read a short from Arduino Due connected to I2C bus. All bytes
     * received must be swamp order
     */
    public static short readShortArduino(I2CDevice device, int cmd) {
        byte[] b = new byte[2];
        ByteBuffer rxBuf = ByteBuffer.allocateDirect(2);
        try {
            device.read(cmd, 2, rxBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        rxBuf.clear();
        for (int i = 0; i < 2; i++) {
            b[i] = rxBuf.get(1 - i);
        }
        return ByteBuffer.wrap(b).getShort();
    }

    /**
     *
     * @param device Device connected to I2C bus
     * @param cmd Command send to a device
     * @return read a short from a device connected to I2C bus
     */
    public static short readShort(I2CDevice device, int cmd) {
        //byte[] b = new byte[2];
        ByteBuffer rxBuf = ByteBuffer.allocateDirect(2);
        try {
            device.read(cmd, 1, rxBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        rxBuf.clear();
        return rxBuf.getShort();
    }

    /**
     *
     * @param device Device connected to I2C bus
     * @param cmd Command send to a device
     * @param value Command value to wite to device
     */
    public static void write(I2CDevice device, byte cmd, byte value) {
        ByteBuffer txBuf = ByteBuffer.allocateDirect(2);
        txBuf.put(0, cmd);
        txBuf.put(1, value);
        try {
            device.write(txBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
    }
}
