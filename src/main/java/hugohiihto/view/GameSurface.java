package hugohiihto.view;

import hugohiihto.audio.SoundBoard;
import hugohiihto.model.GameSession;
import javax.media.Player;

/**
 * What the rules and the keyboard are allowed to ask the window to do.
 */
public interface GameSurface {
    GameSession session();

    SoundBoard audio();

    int screen();

    int nextScreen();

    void setNextScreen(int nextScreen);

    int video();

    void setVideo(int video);

    void openCutscene(int video, int nextScreen);

    void constructFrames();

    void flushVideoFrame();

    Player voice();

    boolean paused();

    void setPaused(boolean paused);

    int lane();

    void setLane(int lane);

    int skierX();

    int skierY();

    int screenWidth();

    int screenHeight();

    void exitGame();

    void repaintSurface();

    void resetGame();

    void preloadIntroFrames();
}
