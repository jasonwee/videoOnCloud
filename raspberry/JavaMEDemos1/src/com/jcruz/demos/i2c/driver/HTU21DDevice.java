/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c.driver;

import com.jcruz.demos.i2c.HTU21D;
import com.jcruz.demos.i2c.I2CRpi;
import com.jcruz.demos.i2c.I2CUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface to HTU21D device
 *
 * @author jcruz
 */
public class HTU21DDevice extends I2CRpi {

    private static final int HTDU21D_ADDRESS = 0x40;

    /**
     *
     * @throws IOException
     */
    public HTU21DDevice() throws IOException {
        super(HTDU21D_ADDRESS);
    }

    /**
     * Read the humidity
     *
     * @return Calc humidity and return it to the user.Returns 998 if I2C timed
     * out.Returns 999 if CRC is wrong
     */
    public float readHumidity() {
        try {
            device.write(HTU21D.TRIGGER_HUMD_MEASURE_NOHOLD.cmd);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }

        //Hang out while measurement is taken. 50mS max, page 4 of datasheet.
        I2CUtils.I2Cdelay(200);

        ByteBuffer rxBuf = ByteBuffer.allocateDirect(3);
        try {
            device.read(rxBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        rxBuf.clear();

        short msb, lsb, checksum;
        msb = (short) I2CUtils.asInt(rxBuf.get(0));
        lsb = (short) I2CUtils.asInt(rxBuf.get(1));
        checksum = (short) I2CUtils.asInt(rxBuf.get(2));
        /* //Used for testing
         byte msb, lsb, checksum;
         msb = 0x4E;
         lsb = 0x85;
         checksum = 0x6B;*/
        short rawHumidity = (short) (((short) msb << 8) | (short) lsb);

        if (check_crc(rawHumidity, checksum) != 0) {
            return (999); //Error out
        }
        //sensorStatus = rawHumidity & 0x0003; //Grab only the right two bits
        rawHumidity &= 0xFFFC; //Zero out the status bits but keep them in place

        //Given the raw humidity data, calculate the actual relative humidity
        float tempRH = rawHumidity / (float) 65536; //2^16 = 65536
        float rh = -6 + (125 * tempRH); //From page 14

        return (rh);
    }

    /**
     * Read the temperature
     *
     * @return Calc temperature and return it to the user. Returns 998 if I2C
     * timed out. Returns 999 if CRC is wrong
     */
    public float readTemperature() {
        try {
            device.write(HTU21D.TRIGGER_TEMP_MEASURE_NOHOLD.cmd);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        //Hang out while measurement is taken. 50mS max, page 4 of datasheet.
        I2CUtils.I2Cdelay(200);

        ByteBuffer rxBuf = ByteBuffer.allocateDirect(3);
        try {
            device.read(rxBuf);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.WARNING,ex.getMessage());
        }
        rxBuf.clear();

        short msb, lsb, checksum;
        msb = (short) I2CUtils.asInt(rxBuf.get(0));
        lsb = (short) I2CUtils.asInt(rxBuf.get(1));
        checksum = (short) I2CUtils.asInt(rxBuf.get(2));

        //Used for testing
//         short msb, lsb, checksum;
//         msb = 0x68;
//         lsb = 0x3A;
//         checksum = 0x7C; 
        short rawTemperature = (short) (((short) msb << 8) | (short) lsb);

        if (check_crc(rawTemperature, (short) checksum) != 0) {
            return (999); //Error out
        }
        //sensorStatus = rawTemperature & 0x0003; //Grab only the right two bits
        rawTemperature &= 0xFFFC; //Zero out the status bits but keep them in place

        //Given the raw temperature data, calculate the actual temperature
        float tempTemperature = rawTemperature / (float) 65536; //2^16 = 65536
        float realTemperature = -46.85F + (175.72F * tempTemperature); //From page 14

        return (realTemperature);
    }

    /**
     *
     * Sets the sensor resolution to one of four levels Page 12: 0/0 = 12bit RH,
     * 14bit Temp 0/1 = 8bit RH, 12bit Temp 1/0 = 10bit RH, 13bit Temp 1/1 =
     * 11bit RH, 11bit Temp Power on default is 0/0
     *
     * @param resolution
     * @throws java.io.IOException
     */
    public void setResolution(byte resolution) throws IOException {
        byte userRegister = read_user_register(); //Go get the current register state
        userRegister &= 0b01111110; //Turn off the resolution bits
        resolution &= 0b10000001; //Turn off all other bits but resolution bits
        userRegister |= resolution; //Mask in the requested resolution bits

        HTU21D.WRITE_USER_REG.write(device, userRegister);

    }

    /**
     * Read the user register
     *
     * @return @throws IOException
     */
    public byte read_user_register() throws IOException {
        return (byte) HTU21D.READ_USER_REG.read(device);
    }

    //Give this function the 2 byte message (measurement) and the check_value byte from the HTU21D
    //If it returns 0, then the transmission was good
    //If it returns something other than 0, then the communication was corrupted
    //From: http://www.nongnu.org/avr-libc/user-manual/group__util__crc.html
    //POLYNOMIAL = 0x0131 = x^8 + x^5 + x^4 + 1 : http://en.wikipedia.org/wiki/Computation_of_cyclic_redundancy_checks
    private long SHIFTED_DIVISOR = (long) 0x988000; //This is the 0x0131 polynomial shifted to farthest left of three bytes

    /**
     *
     * @param message_from_sensor
     * @param check_value_from_sensor
     * @return CRC check value
     */
    private long check_crc(short message_from_sensor, short check_value_from_sensor) {
        //Test cases from datasheet:
        //message = 0xDC, checkvalue is 0x79
        //message = 0x683A, checkvalue is 0x7C
        //message = 0x4E85, checkvalue is 0x6B

        long remainder = (long) message_from_sensor << 8; //Pad with 8 bits because we have to add in the check value
        remainder |= check_value_from_sensor; //Add on the check value

        long divsor = (long) SHIFTED_DIVISOR;

        for (int i = 0; i < 16; i++) //Operate on only 16 positions of max 24. The remaining 8 are our remainder and should be zero when we're done.
        {
            //Serial.print("remainder: ");
            //Serial.println(remainder, BIN);
            //Serial.print("divsor:    ");
            //Serial.println(divsor, BIN);
            //Serial.println();

            if ((remainder & (long) 1 << (23 - i)) > 0) //Check if there is a one in the left position
            {
                remainder ^= divsor;
            }

            divsor >>= 1; //Rotate the divsor max 16 times so that we have 8 bits left of a remainder
        }

        return remainder;
    }

}
