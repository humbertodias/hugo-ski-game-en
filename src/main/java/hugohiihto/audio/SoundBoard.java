package hugohiihto.audio;

import hugohiihto.Resources;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Named clips and the cutscene voice. Callers stop a slot without knowing how the line was opened.
 */
public final class SoundBoard {
    private Clip menu;
    private Clip popcorn;
    private Clip credits;
    private Clip spare;
    private Clip alternate;
    private Clip skiing;
    private Clip money;
    private Clip scoreFanfare;
    private Clip laneChange;
    private Clip phone4;
    private Clip phone6;
    private Clip correct;
    private Player voice;

    public void stopForSkip() {
        stop(menu);
        stop(spare);
        stop(credits);
    }

    public void stopPopcorn() {
        stop(popcorn);
    }

    public void stopAlternate() {
        stop(alternate);
    }

    public void stopVoice() {
        if (voice != null) {
            voice.stop();
        }
    }

    public Player voice() {
        return voice;
    }

    public void playMenu() {
        menu = start("res/music-ps1hugo2menu.wav", 0f);
    }

    public void playCredits() {
        credits = start("res/music_credits.wav", 0f);
    }

    public void playAfterRun(boolean reachedTheEndAlive) {
        if (reachedTheEndAlive) {
            playCredits();
            return;
        }
        playMenu();
    }

    public void playPopcorn() {
        popcorn = start("res/music-djhugopopcorn.wav", -15f);
    }

    public void playSkateboard() {
        alternate = start("res/music_from_classic_skateboard.wav", -4f);
    }

    public void playFinnishTheme() {
        alternate = start("res/music_finnishhugo.wav", null);
    }

    public void playSkiing() {
        skiing = start("res/hiihtoaani.wav", -11f);
    }

    public void playMoney() {
        money = start("res/money.wav", 0f);
    }

    public void playScoreFanfare() {
        scoreFanfare = start("res/points_score.wav", 0f);
    }

    public void playLaneChange() {
        laneChange = start("res/ski_track_change.wav", -23f);
    }

    public void playPhone4() {
        phone4 = start("res/button4sound.wav", null);
    }

    public void playPhone6() {
        phone6 = start("res/button6sound.wav", null);
    }

    public void playCorrect() {
        correct = start("res/correct_selection.wav", 0f);
    }

    public void playVoice(File aiff) {
        try {
            voice = Manager.createPlayer(Resources.file(aiff.getPath()).toURI().toURL());
            voice.start();
            System.out.println("Playing sound for a video");
        } catch (IOException | NoPlayerException ex) {
            Logger.getLogger(SoundBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Clip start(String path, Float gainDb) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Resources.file(path)));
            if (gainDb != null) {
                FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(gainDb);
            }
            clip.start();
            return clip;
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(SoundBoard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static void stop(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
