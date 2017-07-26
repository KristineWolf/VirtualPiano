
import de.mi.ur.midi.Note;
import de.mi.ur.midi.Synthesizer;

import de.ur.mi.graphics.Rect;

import javax.sound.midi.MidiUnavailableException;


/**
 * Created by Kristine on 11.01.2016.
 */

/*
 * This class is a subclass of the Synthesizer class.
 * In addition Claviature owns several keys and a recorder.
 */

public class Claviature extends Synthesizer{

    PianoKey[] keys;
    Recorder recorder;

    public Claviature() throws MidiUnavailableException {
        keys= new PianoKey[Constants.NUM_KEYS];
        initKeys();
        recorder=new Recorder();
    }

    private void initKeys() {
        double xPos = Constants.FIRST_WHITE_KEY_X_POS;

        /*
         * Creates white keys.
         */

        for(int i=0; i<keys.length; i++){
            Rect whiteKey = new Rect( xPos, Constants.KEY_Y_POS,Constants.WHITE_PIANO_KEY_WIDTH, Constants.WHITE_PIANO_KEY_HEIGHT, Constants.WHITE_PIANO_KEY_COLOR);
            WhitePianoKey wKey = new WhitePianoKey(whiteKey, Note.values()[i]);
            keys[i] = wKey;
            xPos+= Constants.WHITE_PIANO_KEY_WIDTH;

            /*
             * Creates black keys.
             */

            if(!(i%Constants.NUM_OF_KEYS_IN_OCTAVE == Constants.FIRST_BLACK_GAP || i%Constants.NUM_OF_KEYS_IN_OCTAVE == Constants.SECOND_BLACK_GAP)){
                i++;
                Rect blackKey = new Rect(xPos -(Constants.BLACK_PIANO_KEY_WIDTH/2), Constants.KEY_Y_POS, Constants.BLACK_PIANO_KEY_WIDTH, Constants.BLACK_PIANO_KEY_HEIGHT, Constants.BLACK_PIANO_KEY_COLOR);
                BlackPianoKey bKey = new BlackPianoKey(blackKey, Note.values()[i]);
                keys[i]= bKey;
            }
        }
    }

    public void draw() {
        /*
         * Draws at first white keys then black keys and afterwards the recorder.
         */

        for (int i = 0; i < keys.length; i++) {
            if(keys[i].getKeyColor() == Constants.WHITE_PIANO_KEY_COLOR){
                keys[i].draw();
            }
        }

        for(int i = 0; i < keys.length; i++) {
            if(keys[i].getKeyColor() == Constants.BLACK_PIANO_KEY_COLOR){
                keys[i].draw();
            }
        }
        recorder.draw();
    }

    public boolean mPressed(int mouseXPos, int mouseYPos, boolean isPressed, double time){
        /*
         * Checks at first if a black key was pressed.
         * Then if this is true: key changes his color to red and after the mouse is released the runtime plays the audio and changes the color back to black.
         */

        for (int i = 0; i < keys.length; i++) {
            if(keys[i].getKeyColor() == Constants.BLACK_PIANO_KEY_COLOR){
               if (keys[i].getKey().hitTest(mouseXPos, mouseYPos)) {
                   if (isPressed) {
                   keys[i].toggleColor();
                   return true;
                   } else {
                       keys[i].toggleColor();
                       playSound(i, time);
                       safeSong(i, time);
                       return true;
                   }
               }
            }
        }

        /*
         * The same process as above only with white keys.
         */

        for (int i = 0; i < keys.length; i++) {
            if(keys[i].getKeyColor() == Constants.WHITE_PIANO_KEY_COLOR){
                if (keys[i].getKey().hitTest(mouseXPos, mouseYPos)) {
                    if (isPressed) {
                        keys[i].toggleColor();
                        return true;
                    } else {
                        keys[i].toggleColor();
                        playSound(i, time);
                        safeSong(i, time);
                        return true;
                    }
                }
            }
        }

        checkRecorder(mouseXPos, mouseYPos, isPressed);
        return false;
    }

    public void playSound(int positionInArray, double velocity){
        try {
            playNote(keys[positionInArray].getNote(), (int) velocity);

        } catch (NoteOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    /*
     * Safes played melody in the arrayList of Recorder class only when recorder is red.
     */

    private void safeSong(int notePosition, double velocity){
        if (recorder.recorderIsPressed)
        {
            Sound sound= new Sound(keys[notePosition].getNote(), (int) velocity);
            recorder.getPlayedMeldody().add(sound);
        }
    }

    /*
     * Checks if recorder was pressed.
     * If there are Sounds in the arrayList the runtime plays the melody and then changes the recorder´s color to green.
     */

    private void checkRecorder(int mouseX, int mouseY, boolean isPressed){
        if(recorder.getOuterCircle().hitTest(mouseX,mouseY)){
            if (isPressed){
                recorder.recorderIsPressed = !recorder.recorderIsPressed;
            }

            if (!recorder.getPlayedMeldody().isEmpty() && !recorder.recorderIsPressed){
                playSong();
            }
        }
    }

    /*
     * This method plays one after another a Sound with a little pause in between.
      * After the last Sound of the arrayList the content of this list will be removed.
     */

    public void playSong() {
        for (int i=0; i<recorder.getPlayedMeldody().size();i++)
        {
            try {
                playNote(recorder.getPlayedMeldody().get(i).note, recorder.getPlayedMeldody().get(i).velocity);
                Thread.sleep(Constants.PAUSE);
            } catch (NoteOutOfBoundsException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        recorder.getPlayedMeldody().clear();

    }

    /*
     * The pressed key changes to an integer which stands for the position in the String(KEY_SET).
     * If the pressed button is in the String the key, with the same Position in the array as the position of the button in the String,
     * will be changed by his color.
     * Then after the button is released the runtime playes the dedicated note and then changes the color back to his initial color and safes the melody if
     * the recorder is red.
     */

    public boolean kPressed(char pressed, boolean isPressed, int time){
        int keyNUm = Constants.KEY_SET.indexOf(pressed);
        if (keyNUm>=0) {
            if(isPressed){
                keys[keyNUm].toggleColor();
                return true;
            }else {
                keys[keyNUm].toggleColor();
                playSound(keyNUm, time);
                safeSong(keyNUm, time);
                return true;
            }
        }
        return false;
    }
}






