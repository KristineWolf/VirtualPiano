import de.ur.mi.graphicsapp.GraphicsApp;
import processing.event.MouseEvent;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;

public class MainApp extends GraphicsApp {

    private Claviature claviature;
    private double time;

    public void setup() {
        initCanvas();
        initPiano();
    }

    private void initCanvas() {
        size(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        frameRate(Constants.FRAME_RATE);
        smooth(Constants.SMOOTH_LEVEL);
    }

    private void initPiano() {
        try {
            claviature = new Claviature();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void draw() {
        background(Constants.CANVAS_BACKGROUND_COLOR);
        claviature.draw();
    }

    /*
     * time safes the time while you press the mouse.
     */
    @Override
    public void mousePressed(MouseEvent event) {
        time = millis();
        int mouseX = event.getX();
        int mouseY = event.getY();

        if (claviature.mPressed(mouseX, mouseY, true, Constants.ZERO)) {
        }

    }

    /*
     * timeDiff represents the time during pressing a key.
     */

    @Override
    public void mouseReleased(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        double timeDiff = millis() - time;
        if (timeDiff > Constants.MAX_VELOCITY) {
            timeDiff = Constants.MAX_VELOCITY;
        }

        if (claviature.mPressed(mouseX, mouseY, false, timeDiff)) {
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        time = millis();
        char pressedKeyPad = event.getKeyChar();
        if (claviature.kPressed(pressedKeyPad, true, Constants.ZERO)) {
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        double timeDiff = millis() - time;
        char pressedKeyPad = event.getKeyChar();
        if (timeDiff > Constants.MAX_VELOCITY) {
            timeDiff = Constants.MAX_VELOCITY;
        }
        if (claviature.kPressed(pressedKeyPad, false, (int) timeDiff)) {

        }
    }
}
