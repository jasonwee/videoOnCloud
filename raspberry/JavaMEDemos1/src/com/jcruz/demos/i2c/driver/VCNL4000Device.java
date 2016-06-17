/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.i2c.driver;

import com.jcruz.demos.i2c.I2CRpi;
import com.jcruz.demos.i2c.I2CUtils;
import com.jcruz.demos.i2c.VCNL4000;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface to VCNL4000 proximity sensor
 *
 * @author Jose Cuz
 */
public class VCNL4000Device extends I2CRpi {

    /**
     * Set frequency for IR led
     */
    public enum Freq {

        /**
         * 3.125 MHZ
         */
        F3M125(0x00),
        /**
         * 1.5625 Mhz
         */
        F1M5625(0x01),
        /**
         * 781.25 Khz
         */
        F781K25(0x02),
        /**
         * 390.625 Khz
         */
        F390K625(0x03);

        /**
         * Read frequency value
         */
        public byte value;

        Freq(int value) {
            this.value = (byte) value;
        }
    }
    

    private static final int VCNL4000_ADDRESS = 0x13;

    /**
     *
     * @throws IOException
     */
    public VCNL4000Device() throws IOException {
        super(VCNL4000_ADDRESS);

        byte rev = (byte) VCNL4000.PRODUCTID.read(device);
        if ((rev & 0xF0) != 0x10) {
            Logger.getGlobal().log(Level.SEVERE, "Sensor not found");
            return;
        }

        VCNL4000.IRLED.write(device, (byte) 20);        // set to 20 * 10mA = 200mA
        Logger.getGlobal().log(Level.FINE, "IR LED current = " + String.valueOf(VCNL4000.IRLED.read(device) * 10) + " mA");

        //write8(VCNL4000_SIGNALFREQ, 3);
        setSignalFreq(Freq.F390K625);
        Logger.getGlobal().log(Level.FINE, "Proximity measurement frequency = ");
        byte freq = (byte) VCNL4000.SIGNALFREQ.read(device);
        if (freq == Freq.F3M125.value) {
            Logger.getGlobal().log(Level.FINE, "3.125 MHz");
        }
        if (freq == Freq.F1M5625.value) {
            Logger.getGlobal().log(Level.FINE, "1.5625 MHz");
        }
        if (freq == Freq.F781K25.value) {
            Logger.getGlobal().log(Level.FINE, "781.25 KHz");
        }
        if (freq == Freq.F390K625.value) {
            Logger.getGlobal().log(Level.FINE, "390.625 KHz");
        }

        VCNL4000.PROXIMITYADJUST.write(device, (byte) 0x81);
        Logger.getGlobal().log(Level.FINE, "Proximity adjustment register = "+String.valueOf(VCNL4000.PROXIMITYADJUST.read(device)));

        // arrange for continuous conversion
        //write8(VCNL4000_AMBIENTPARAMETER, 0x89);
    }

    /**
     *
     * @param cur
     */
    public void setLEDcurrent(byte cur) {
        if ((cur > 20) || (cur < 0)) {
            cur = 5; //# setting this to 50mA; online ppl report trouble with I2C bus at over 60mA
        }      //more here: http://forums.adafruit.com/viewtopic.php?f=19&t=24263&p=125769&hilit=vcnl#p125769
        VCNL4000.IRLED.write(device, cur);
    }

    /**
     * Activate continuous conversion
     *
     */
    public void continuousConversionOn() {

        VCNL4000.AMBIENTPARAMETER.write(device, (byte) 0x89);
    }

    /**
     * Deactivate continuous conversion
     *
     */
    public void continuousConversionOff() {
        VCNL4000.AMBIENTPARAMETER.write(device, (byte) 0x09);
    }

    /**
     * Set IR Frequency
     *
     * @param freq
     */
    public void setSignalFreq(Freq freq) {
        //# Setting the proximity IR test signal frequency. The proximity measurement is using a square IR 
        //# signal as measurement signal. Four different values are possible: 
        //# 00 = 3.125 MHz
        //# 01 = 1.5625 MHz
        //# 02 = 781.25 kHz (DEFAULT)
        //# 03 = 390.625 kHz
        VCNL4000.SIGNALFREQ.write(device, freq.value);
    }

    /**
     * Get IR IR Frequency
     *
     * @return
     */
    public int getSignalFreq() {
        return VCNL4000.SIGNALFREQ.read(device);
    }

    /**
     * Adjust Proximity
     *
     */
    public void setProximityAdjust() {
        VCNL4000.PROXIMITYADJUST.write(device, (byte) 0x81);
    }

    /**
     * Get Proximity
     *
     * @return
     */
    public int getProximityAdjust() {
        return VCNL4000.PROXIMITYADJUST.read(device);
    }

    /**
     *
     * @return proximity from object to device in cms
     */
    public short readProximity() {
        byte temp = (byte) VCNL4000.COMMAND.read(device);

        VCNL4000.COMMAND.write(device, (byte) (temp | VCNL4000.MEASUREPROXIMITY.cmd));

        while (true) {
            byte result = (byte) VCNL4000.COMMAND.read(device);
            //Serial.print("Ready = 0x"); Serial.println(result, HEX);
            if ((result & VCNL4000.PROXIMITYREADY.cmd) > 0) {
                short data = (short) (VCNL4000.PROXIMITYDATA.read(device) << 8);
                data = (short) (data | VCNL4000.PROXIMITYDATA2.read(device));
                return data;
            }
            I2CUtils.I2Cdelay(10);
        }
    }

    /**
     *
     * @return Ambient light indicator
     */
    public short readAmbientLight() {
        // read ambient light!
        byte temp = (byte) VCNL4000.COMMAND.read(device);

        VCNL4000.COMMAND.write(device, (byte) (temp | VCNL4000.MEASUREAMBIENT.cmd));

        while (true) {
            byte result = (byte) VCNL4000.COMMAND.read(device);
            //Serial.print("Ready = 0x"); Serial.println(result, HEX);
            if ((result & VCNL4000.AMBIENTREADY.cmd) > 0) {

                short data = (short) (VCNL4000.AMBIENTDATA.read(device) << 8);
                data = (short) (data | VCNL4000.AMBIENTDATA2.read(device));
                return data;
            }

            I2CUtils.I2Cdelay(10);
        }
    }

}
