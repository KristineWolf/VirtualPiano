import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Ellipse;
import java.util.ArrayList;

/**
 * Created by Kristine on 13.01.2016.
 */

/*
 * The class Recorder represents the recording and play-back of a played melody.
 */

public class Recorder {

    private Ellipse outerCircle;
    private Ellipse innerCircle;
    public boolean recorderIsPressed;
    private ArrayList<Sound> playedMelody;

    public Recorder(){
        recorderIsPressed=false;
        getRecorder();
        playedMelody = new ArrayList<Sound>();

    }

    private void getRecorder(){
        outerCircle = new Ellipse(Constants.RECORDER_X_POS, Constants.RECORDER_Y_POS, Constants.OUTER_CIRCLE_RADIUS, Constants.OUTER_CIRCLE_RADIUS, Constants.STOP_RECORDING_COLOR);
        outerCircle.setBorder(Constants.BORDER_COLOR, Constants.BORDER_WIDTH);
        innerCircle = new Ellipse(Constants.RECORDER_X_POS, Constants.RECORDER_Y_POS, Constants.INNER_CIRCLE_RADIUS, Constants.INNER_CIRCLE_RADIUS, Constants.INNER_CIRCLE_COLOR);
    }

    /*
     * the recorder has only the Color RED if you record a song or if you play the recorded song.
     */

    public void draw(){
        if(recorderIsPressed) {
            changeColor(Constants.RECORDING_COLOR);
        }else{
            changeColor(Constants.STOP_RECORDING_COLOR);
        }
    }

    private void changeColor(Color recorderColor){
        outerCircle.setColor(recorderColor);
        outerCircle.draw();
        innerCircle.draw();
    }

    public Ellipse getOuterCircle(){
        return outerCircle;
    }

    public ArrayList<Sound> getPlayedMeldody(){
        return  playedMelody;
    }
}
