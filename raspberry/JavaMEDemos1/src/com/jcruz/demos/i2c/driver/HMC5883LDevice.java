/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c.driver;

import com.jcruz.demos.i2c.HMC5883L;
import com.jcruz.demos.i2c.I2CRpi;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcruz
 */
public class HMC5883LDevice extends I2CRpi {

    public enum Measurement {

        Continuous(0x00),
        SingleShot(0x01),
        Idle(0x03);
        /**
         * Read Measurement type
         */
        public byte value;

        Measurement(int value) {
            this.value = (byte) value;
        }

    }

    public class MagnetometerScaled {

        public float XAxis;
        public float YAxis;
        public float ZAxis;
    };

    public class MagnetometerRaw {

        public int XAxis;
        public int YAxis;
        public int ZAxis;
    };

    private static final int HMC5883L_ADDRESS = 0x1E;
    private float m_Scale = 0.0F;
    private int ErrorCode_1_Num=1;

    public HMC5883LDevice() throws IOException {
        super(HMC5883L_ADDRESS);
        m_Scale = 1;
    }

    /**
     *
     * @return
     */
    public MagnetometerRaw readRawAxis() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(6);
        try {
            device.read(HMC5883L.DataRegBegin.cmd, 1, buffer);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
       
        // Read each of the pairs of data as a signed short
        buffer.rewind();
        byte[] data = new byte[6];
        buffer.get(data);
                
        MagnetometerRaw raw = new MagnetometerRaw();
        raw.XAxis = (data[0] << 8) | data[1];
        raw.ZAxis = (data[2] << 8) | data[3];
        raw.YAxis = (data[4] << 8) | data[5];
        return raw;
    }

    /**
     *
     * @return
     */
    public MagnetometerScaled readScaledAxis() {
        MagnetometerRaw raw = readRawAxis();
        MagnetometerScaled scaled = new MagnetometerScaled();
        scaled.XAxis = raw.XAxis * m_Scale;
        scaled.ZAxis = raw.ZAxis * m_Scale;
        scaled.YAxis = raw.YAxis * m_Scale;
        return scaled;
    }

    /**
     *
     * @param gauss
     * @return
     */
    public int setScale(float gauss) {
        int regValue = 0x00;
        if (gauss == 0.88) {
            regValue = 0x00;
            m_Scale = 0.73F;
        } else if (gauss == 1.3) {
            regValue = 0x01;
            m_Scale = 0.92F;
        } else if (gauss == 1.9) {
            regValue = 0x02;
            m_Scale = 1.22F;
        } else if (gauss == 2.5) {
            regValue = 0x03;
            m_Scale = 1.52F;
        } else if (gauss == 4.0) {
            regValue = 0x04;
            m_Scale = 2.27F;
        } else if (gauss == 4.7) {
            regValue = 0x05;
            m_Scale = 2.56F;
        } else if (gauss == 5.6) {
            regValue = 0x06;
            m_Scale = 3.03F;
        } else if (gauss == 8.1) {
            regValue = 0x07;
            m_Scale = 4.35F;
        } else {
            return ErrorCode_1_Num;
        }
    
        // Setting is in the top 3 bits of the register.
        regValue = regValue << 5;
        HMC5883L.ConfigRegB.write(device, (byte) regValue);
        return 0;
    }

    /**
     *
     * @param mode
     */
    public void setMeasurementMode(Measurement mode) {
        HMC5883L.ModeReg.write(device, mode.value);
       // Write(ModeRegister, mode);
    }
    
    /**
     *
     * @return
     */
    public double calculateHeading(){
            MagnetometerRaw raw = readRawAxis();
            // Retrived the scaled values from the compass (scaled to the configured scale).
            MagnetometerScaled scaled = readScaledAxis();

            // Values are accessed like so:
            //int MilliGauss_OnThe_XAxis = scaled.XAxis;// (or YAxis, or ZAxis)

            // Calculate heading when the magnetometer is level, then correct for signs of axis.
            double heading = Math.atan2((double) scaled.YAxis, (double) scaled.XAxis);

 // Once you have your heading, you must then add your 'Declination Angle', which is the 'Error' of the magnetic field in your location.
            // Find yours here: http://www.magnetic-declination.com/
            // Mine is: 2� 37' W, which is 2.617 Degrees, or (which we need) 0.0456752665 radians, I will use 0.0457
            // If you cannot find your Declination, comment out these two lines, your compass will be slightly off.
            double declinationAngle = - 0.2021672989739025;
            heading += declinationAngle;

            // Correct for when signs are reversed.
            if (heading < 0) {
                heading += 2 * Math.PI;
            }

            // Check for wrap due to addition of declination.
            if (heading > 2 * Math.PI) {
                heading -= 2 * Math.PI;
            }

            // Convert radians to degrees for readability.
            return  heading * 180 / Math.PI;
    }

//    public void HMC5883L::Write(int address, int data) {
//        Wire.beginTransmission(HMC5883L_Address);
//        Wire.write(address);
//        Wire.write(data);
//        Wire.endTransmission();
//    }
//
//    uint8_t * HMC5883L::Read(int address, int length) {
//        Wire.beginTransmission(HMC5883L_Address);
//        Wire.write(address);
//        Wire.endTransmission();
//
//        Wire.beginTransmission(HMC5883L_Address);
//        Wire.requestFrom(HMC5883L_Address, length);
//
//        uint8_t buffer[length];
// if (Wire.available() == length) {
//            for (uint8_t i = 0; i < length; i++) {
//                buffer[i] = Wire.read();
//            }
//        }
//        Wire.endTransmission();
//
//        return buffer;
//    }

//    char * HMC5883L::GetErrorText(int errorCode) {
//        if (ErrorCode_1_Num == 1) {
//            return ErrorCode_1;
//        }
//
//        return "Error not defined.";
//    }

}
