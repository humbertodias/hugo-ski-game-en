package hugohiihto.model;

import hugohiihto.view.GameSurface;
import java.util.Timer;

/**
 * One run: score, lives, the course, the puzzle and the timer that walks the course.
 */
public final class GameSession {
    public static final int SPEED_MS = 1750;

    private Score score = new Score();
    private MemoryPuzzle memory = MemoryPuzzle.fixed("dsHAcb");
    private final CheatCode cheat = new CheatCode();
    private final SkiField slope = new SkiField();
    private StageCourse stage;
    private SlopeRules rules = new SlopeRules();
    private Timer timer;

    private boolean gameOver = true;
    private boolean paused;
    private boolean pausedByPlayer;
    private boolean windToTheLeft;
    private boolean beat = true;
    private int progress = -5;
    private int furthest = -4;
    private int lane;
    private int lives = 4;
    private boolean ropeGood;
    private boolean ropeBad;
    private boolean ropeBest;

    public void start(GameSurface surface, int gameSpeed) {
        if (gameSpeed > 1500 && gameSpeed < 3000 && gameOver) {
            System.out.println("Game reset called");
            System.gc();
            lane = 0;
            ropeGood = false;
            ropeBad = false;
            ropeBest = false;
            gameOver = false;
            score = new Score();
            lives = 4;
            progress = -5;
            furthest = -4;
            beat = true;
            memory = MemoryPuzzle.random();
            stage = StageCourse.generate(cheat.isActive());
            windToTheLeft = Math.random() < 0.5;
            rules = new SlopeRules();
            cancelLoop();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new SessionTick(this, surface, rules), 0, SPEED_MS);
            return;
        }
        gameOver = true;
        start(surface, SPEED_MS);
    }

    public void cancelLoop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public Score score() {
        return score;
    }

    public MemoryPuzzle memory() {
        return memory;
    }

    public CheatCode cheat() {
        return cheat;
    }

    public SkiField slope() {
        return slope;
    }

    public StageCourse stage() {
        return stage;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean paused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean pausedByPlayer() {
        return pausedByPlayer;
    }

    public void setPausedByPlayer(boolean pausedByPlayer) {
        this.pausedByPlayer = pausedByPlayer;
    }

    public void togglePlayerPause() {
        pausedByPlayer = true;
        if (!paused) {
            paused = true;
            return;
        }
        pausedByPlayer = false;
        paused = false;
    }

    public boolean windToTheLeft() {
        return windToTheLeft;
    }

    public boolean beat() {
        return beat;
    }

    public int progress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int furthest() {
        return furthest;
    }

    public void setFurthest(int furthest) {
        this.furthest = furthest;
    }

    public int lane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public int lives() {
        return lives;
    }

    public void loseLife() {
        int next = lives - 1;
        if (next < 5 && next > -1) {
            lives = next;
        }
    }

    public boolean ropeGood() {
        return ropeGood;
    }

    public boolean ropeBad() {
        return ropeBad;
    }

    public boolean ropeBest() {
        return ropeBest;
    }

    public void chooseRope(int rope) {
        ropeGood = rope == 1;
        ropeBad = rope == 2;
        ropeBest = rope == 3;
        if (rope == 2) {
            progress = -5;
        }
    }

    /**
     * Timer task kept beside the session so the rules stay free of {@code java.util.Timer}.
     */
    private static final class SessionTick extends java.util.TimerTask {
        private final GameSession session;
        private final GameSurface surface;
        private final SlopeRules rules;

        private SessionTick(GameSession session, GameSurface surface, SlopeRules rules) {
            this.session = session;
            this.surface = surface;
            this.rules = rules;
        }

        @Override
        public void run() {
            rules.tick(session, surface);
        }
    }
}
