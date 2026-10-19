package hugohiihto.input;

import hugohiihto.model.GameSession;
import hugohiihto.model.MemoryPuzzle;
import hugohiihto.view.GameSurface;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Sends each key to the screen that is showing. The slope, the cave and the title do not share one switch.
 */
public final class KeyboardController extends KeyAdapter {
    private final GameSurface surface;

    public KeyboardController(GameSurface surface) {
        this.surface = surface;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.gc();
            surface.exitGame();
            return;
        }
        if (key == KeyEvent.VK_ENTER) {
            if (surface.video() != 3) {
                surface.flushVideoFrame();
            }
            surface.audio().stopForSkip();
            if (surface.voice() != null && surface.video() != 2 && surface.video() != 3) {
                surface.audio().stopVoice();
            }
        }
        switch (surface.screen()) {
            case 0 -> intro(key);
            case 1 -> title(key);
            case 2 -> cutscene(key);
            case 3 -> ski(key);
            case 4 -> memory(key);
            case 5 -> result(key);
            default -> {
                surface.setNextScreen(2);
                System.out.println(" --- keyPressed --- to state " + surface.nextScreen());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (surface.paused() || surface.screen() != 3) {
            return;
        }
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            surface.audio().playLaneChange();
        }
        if (key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4) {
            surface.audio().playPhone4();
        }
        if (key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6) {
            surface.audio().playPhone6();
        }
    }

    private void intro(int key) {
        if (key != KeyEvent.VK_ENTER) {
            return;
        }
        surface.constructFrames();
        surface.audio().stopPopcorn();
        surface.audio().stopAlternate();
        surface.setVideo(0);
        surface.setNextScreen(6);
    }

    private void title(int key) {
        GameSession session = surface.session();
        if (key == KeyEvent.VK_ENTER) {
            surface.preloadIntroFrames();
            if (session.progress() >= 71 && (session.ropeBest() || session.ropeGood())) {
                surface.setNextScreen(0);
                session.setProgress(-5);
                return;
            }
            if (session.isGameOver()) {
                session.cancelLoop();
            }
            surface.resetGame();
            surface.setVideo(1);
            surface.setNextScreen(6);
            surface.constructFrames();
        }
        int digit = digit(key);
        if (digit < 0) {
            return;
        }
        boolean alreadyCheating = session.cheat().isActive();
        session.cheat().accept(digit);
        if (alreadyCheating) {
            surface.audio().playMoney();
        }
    }

    private void cutscene(int key) {
        int video = surface.video();
        if (video == 3) {
            ropes(key);
            return;
        }
        if (video == 14 || video == 15 || video == 16 || video == 17) {
            crash(key);
            return;
        }
        if (key != KeyEvent.VK_ENTER) {
            return;
        }
        GameSession session = surface.session();
        switch (video) {
            case 0 -> {
                surface.audio().playAfterRun(session.progress() >= 71 && !session.isGameOver());
                surface.setPaused(true);
                surface.setNextScreen(1);
            }
            case 1, 2, 8, 9 -> {
                surface.setNextScreen(3);
                surface.setPaused(false);
            }
            case 4 -> surface.setNextScreen(4);
            case 5 -> {
                surface.audio().stopPopcorn();
                surface.audio().stopAlternate();
                surface.setVideo(3);
                surface.setNextScreen(6);
            }
            case 6, 10 -> {
                session.setGameOver(true);
                surface.setNextScreen(5);
            }
            case 7 -> surface.openCutscene(4, 6);
            case 11, 12, 13 -> surface.setNextScreen(5);
            default -> {
            }
        }
    }

    private void ropes(int key) {
        surface.audio().stopPopcorn();
        if (surface.voice() != null && (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1
                || key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2
                || key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3)) {
            surface.audio().stopVoice();
        }
        GameSession session = surface.session();
        if (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1) {
            session.chooseRope(1);
            System.out.println("1 chosen!");
            session.score().awardGoodRope();
            surface.openCutscene(11, 6);
        }
        if (key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2) {
            session.chooseRope(2);
            System.out.println("2 chosen!");
            surface.openCutscene(12, 6);
        }
        if (key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3) {
            session.chooseRope(3);
            System.out.println("3 chosen!");
            session.score().awardBestRope();
            surface.openCutscene(13, 6);
        }
    }

    private void crash(int key) {
        surface.setLane(0);
        surface.session().slope().dropAll(800);
        if (key != KeyEvent.VK_ENTER) {
            return;
        }
        int lives = surface.session().lives();
        if (lives >= 2) {
            surface.openCutscene(8, 6);
            return;
        }
        if (lives >= 1) {
            surface.openCutscene(9, 6);
            return;
        }
        surface.openCutscene(10, 6);
    }

    private void ski(int key) {
        if (left(key)) {
            moveLane(-1);
        } else if (right(key)) {
            moveLane(1);
        }
        if (key == KeyEvent.VK_ENTER) {
            surface.session().togglePlayerPause();
        }
    }

    private void moveLane(int direction) {
        if (surface.paused()) {
            return;
        }
        int lane = surface.lane();
        if (direction < 0) {
            if (surface.skierX() <= -25 || lane < 1) {
                return;
            }
            surface.setLane(lane - 1);
            return;
        }
        if (surface.skierX() >= surface.screenWidth() - 220 || lane > 2) {
            return;
        }
        surface.setLane(lane + 1);
    }

    private void memory(int key) {
        int column = switch (key) {
            case KeyEvent.VK_1, KeyEvent.VK_NUMPAD1 -> 0;
            case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2 -> 1;
            case KeyEvent.VK_3, KeyEvent.VK_NUMPAD3 -> 2;
            default -> -1;
        };
        if (column < 0) {
            return;
        }
        MemoryPuzzle puzzle = surface.session().memory();
        boolean stillCorrect = puzzle.choose(column);
        if (stillCorrect) {
            surface.audio().playCorrect();
        }
        if (!puzzle.isAllCorrect() && !puzzle.isSolved()) {
            System.out.println("Wrong guess, not proceeding to ropes!");
            surface.setNextScreen(5);
        }
        if (puzzle.isSolved()) {
            System.out.println("Proceeding to ropes!");
            surface.openCutscene(3, 2);
        }
    }

    private void result(int key) {
        if (key != KeyEvent.VK_ENTER) {
            return;
        }
        GameSession session = surface.session();
        if (session.ropeGood() || session.ropeBest()) {
            session.setProgress(71);
            surface.setNextScreen(1);
            surface.audio().playAfterRun(session.progress() >= 71);
            return;
        }
        surface.setNextScreen(0);
    }

    private static boolean left(int key) {
        return key == KeyEvent.VK_LEFT || key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4;
    }

    private static boolean right(int key) {
        return key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6;
    }

    private static int digit(int key) {
        return switch (key) {
            case KeyEvent.VK_0, KeyEvent.VK_NUMPAD0 -> 0;
            case KeyEvent.VK_4, KeyEvent.VK_NUMPAD4 -> 4;
            case KeyEvent.VK_5, KeyEvent.VK_NUMPAD5 -> 5;
            case KeyEvent.VK_7, KeyEvent.VK_NUMPAD7 -> 7;
            case KeyEvent.VK_9, KeyEvent.VK_NUMPAD9 -> 9;
            default -> -1;
        };
    }
}
