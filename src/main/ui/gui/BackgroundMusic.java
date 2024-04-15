package ui.gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Represents an instance of background music to be played
// while user is interacting with a menu or event
public class BackgroundMusic {
    private final File musicPath;   // file containing audio file
    private Clip clip;              // clip of background music

    // EFFECTS:  constructs a new instance of background music,
    //           setting the file to the one located at the
    //           specified link
    public BackgroundMusic(String path) {
        musicPath = new File(path);
    }

    // MODIFIES: this
    // EFFECTS:  continuously loops given background music file,
    //           until told to stop
    // CREDIT:   https://www.youtube.com/watch?v=P856ukheHeE
    public void loopMusic() {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS:  ends any music currently playing from this
    //           instance of background music
    public void endLoop() {
        clip.stop();
    }
}
