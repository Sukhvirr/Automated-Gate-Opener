import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;

public class BoardInitialization {
    public static IODevice initializeBoard(String port)
            throws IOException, InterruptedException {
        IODevice myGroveBoard = new FirmataDevice(port); // Board object, using the name of a port
        try {
            myGroveBoard.start(); // start comms with board;
            System.out.println("Board started.");
            myGroveBoard.ensureInitializationIsDone(); // make sure connection is good to board.
            return myGroveBoard;
        } catch (Exception ex) {
            System.out.println("Couldn't connect to the board."); // message if the connection didn’t happen
            throw ex;
        }
    }

    public static SSD1306 initializeOLED(IODevice board)
            throws IOException {
        I2CDevice i2cObject = board.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
        SSD1306 myOLED = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515
        // Initialize the OLED (SSD1306) object
        myOLED.init();
        return myOLED;
    }
}
