import de.mi.ur.midi.Note;
import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Rect;

/**
 * Created by Kristine on 11.01.2016.
 */

/*
 * Class which defines a single piano key.
 */

public class PianoKey {

    private Rect key;
    private Note note;
    private Color keyColor;

    public PianoKey(Rect key, Note note){
        this.key=key;
        key.setBorder(Constants.BORDER_COLOR, Constants.BORDER_WIDTH);
        this.note=note;
        keyColor=key.getColor();
    }

    public void draw(){
        key.draw();
    }

    /*
     * This method changes the color of a key when and after the key is pressed by the mouse or the key pad.
     */

    public void toggleColor() {
        if (key.getColor() == keyColor) {
            key.setColor(Constants.PRESSED_KEY_COLOR);
        } else {
            key.setColor(keyColor);

        }
    }

    public Rect getKey(){
        return key;
    }

    public Color getKeyColor(){
        return keyColor;
    }

    public Note getNote(){
        return note;
    }
}
