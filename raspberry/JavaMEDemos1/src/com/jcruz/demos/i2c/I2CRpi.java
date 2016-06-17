/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;

/**
 * Base definitions for create a device and its config
 *
 * @author Jose Cuz
 */
public class I2CRpi {

    private I2CDeviceConfig config;

    /**
     * Save device address establishing
     */
    public I2CDevice device = null;

    /**
     * Define device and config it
     *
     * @param i2cAddress
     * @throws IOException
     */
    public I2CRpi(int i2cAddress) throws IOException {
        config = new I2CDeviceConfig(DeviceConfig.DEFAULT,
                i2cAddress,
                DeviceConfig.DEFAULT,
                DeviceConfig.DEFAULT);

        device = (I2CDevice) DeviceManager.open(I2CDevice.class, config);
    }

    /**
     * free device resource
     *
     */
    public void close() {
        try {
            device.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
