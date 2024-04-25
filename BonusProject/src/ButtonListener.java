import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;

import java.io.IOException;

/* Implement the button listener. This is an example of inheritance. (implemented an interface) */
public class ButtonListener implements IODeviceEventListener {
    // Data hiding
    private final Pin Button;
    private final Pin redLED;
    private final Pin ServoMotor;

    // The constructor
    public ButtonListener(Pin Button, Pin redLED, Pin ServoMotor) {
        this.Button = Button;
        this.redLED = redLED;
        this.ServoMotor = ServoMotor;
    }

    // Over-riding is an example of polymorphism.
    @Override
    public void onPinChange(IOEvent ioEvent) {

        /* first, return right away if the event isn't from a button */
        if (ioEvent.getPin().getIndex() != Button.getIndex()) {
            return;
        }

        try {
            // Check if the button is pressed
            System.out.println("Button Event!!");
            for (int pos = 0; pos <= 120; pos++) {
                ServoMotor.setValue(pos);
                Thread.sleep(15);
                redLED.setValue(1);
            }
            Thread.sleep(500);
            redLED.setValue(0);

            for (int pos = 120; pos >= 0; pos--) {
                ServoMotor.setValue(pos);
                Thread.sleep(15);
            }
                Thread.sleep(500);

        }catch(IOException except){
            except.printStackTrace();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // Don't implement the methods below. Don't need them. Leave blank.
    @Override
    public void onStart(IOEvent ioEvent) {

    }

    @Override
    public void onStop(IOEvent ioEvent) {

    }

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {

    }
}
