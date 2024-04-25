/*Maps the Arduino pin labels to those of Firmata */
/*Encapsulation where pin constants are encapsulated withing the class*/
/*Provides a single point of access and management for pin definitions.*/
public class Pins {
    static final int A0 = 14; // Potentiometer
    static final int A1 = 15; // Servo Motor
    static final int A2 = 16; // Sound
    static final int D2 = 2; // Ultrasonic Sensor triggerPin
    static final int D3 = 3; // Ultrasonic Sensor echoPin
    static final int D6 = 6; // Button
    static final int D4 = 4; // red LED
    static final int D13 = 13; // default LED on arduino
    static final byte I2C0 = 0x3C; // OLED Display
    private Pins() {
    }
}