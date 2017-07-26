import de.mi.ur.midi.Note;

/**
 * Created by Kristine on 17.01.2016.
 */

/*
 * This class represents an audio.
 */

public class Sound {

    Note note;
    int velocity;

    public Sound(Note note, int velocity){
        this.note = note;
        this.velocity = velocity;
    }
}
