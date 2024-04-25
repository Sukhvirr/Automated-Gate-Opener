import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) throws InterruptedException, IOException {
        String myPort = "COM5"; // The USB port name varies.
        List<Integer> servoPositions = new ArrayList<>();
        try {
            IODevice myGroveBoard = BoardInitialization.initializeBoard(myPort);
            SSD1306 myOLED = BoardInitialization.initializeOLED(myGroveBoard);

            Pin triggerPin = myGroveBoard.getPin(Pins.D2); // triggerPin
            triggerPin.setMode(Pin.Mode.OUTPUT);

            Pin echoPin = myGroveBoard.getPin(Pins.D3); // echoPin
            echoPin.setMode(Pin.Mode.INPUT);

            Pin ServoMotor = myGroveBoard.getPin(Pins.A1);  // Servo Motor
            ServoMotor.setMode(Pin.Mode.SERVO);

            Pin redLED = myGroveBoard.getPin(Pins.D4); // red LED
            redLED.setMode(Pin.Mode.OUTPUT);

            Pin Button = myGroveBoard.getPin(Pins.D6); // Button
            Button.setMode(Pin.Mode.INPUT);

            ButtonListener theButtonListener = new ButtonListener(Button, redLED, ServoMotor); // State Machine which sweeps servo when button is pressed
            myGroveBoard.addEventListener(theButtonListener);

            // GUI setup
            JFrame window = new JFrame();
            window.setTitle("Servo Motor Position");
            window.setSize(800, 600);
            window.setLayout(new BorderLayout());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            XYSeries series = new XYSeries("Servo Motor Position");
            XYSeriesCollection dataset = new XYSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYLineChart("Servo Motor Position", "Time (seconds)", "Position", dataset);
            window.add(new ChartPanel(chart), BorderLayout.CENTER);

            window.setVisible(true);
            while (true) {
                long duration = readUltrasonicDistance(triggerPin, echoPin);
                int cm = (int) (0.01723 * duration);
                if (cm < 30) {
                    System.out.println(cm + "cm");
                    myOLED.clear();
                    myOLED.getCanvas().setCursor(0, 0);
                    myOLED.getCanvas().write("Distance: " + cm + "cm");
                    myOLED.display();
                    // Sweep servo from 0 to 120 degrees
                    for (int pos = 0; pos <= 120; pos++) {
                        ServoMotor.setValue(pos);
                        servoPositions.add(pos); // Collect servo positions
                        if (!servoPositions.isEmpty()) { // Check if the list is not empty
                            updateChart(series, servoPositions);
                        }
                        Thread.sleep(15);
                        redLED.setValue(1);
                    }
                    Thread.sleep(500);
                    redLED.setValue(0);

                    // Sweep servo from 120 to 0 degrees
                    for (int pos = 120; pos >= 0; pos--) {
                        ServoMotor.setValue(pos);
                        servoPositions.add(pos); // Collect servo positions
                        if (!servoPositions.isEmpty()) { // Check if the list is not empty
                            updateChart(series, servoPositions);
                        }
                        Thread.sleep(15);
                    }
                    Thread.sleep(500);
                }
            }
        } catch (Exception ex) {
            System.out.println("An error occurred: " + ex.getMessage());
        }
    }

    private static long readUltrasonicDistance(Pin triggerPin, Pin echoPin) throws Exception {
        triggerPin.setValue(0); // Ensure trigger pin is initially LOW
        Thread.sleep(2); // Wait for any previous pulse to clear
        // Send a short pulse to trigger the ultrasonic sensor
        triggerPin.setValue(1); // Set trigger pin HIGH
        Thread.sleep(10); // Wait for 10 milliseconds
        triggerPin.setValue(0); // Set trigger pin LOW

        // Now, read the duration of the pulse on the echo pin
        long duration = echoPin.getValue(); // Read pulse duration with timeout of 20ms
        return duration;
    }

    private static void updateChart(XYSeries series, List<Integer> servoPositions) {
        // Clear the series and re-add all the points
        series.clear();
        for (int i = 0; i < servoPositions.size(); i++) {
            series.add(i, servoPositions.get(i));
        }
    }
}


