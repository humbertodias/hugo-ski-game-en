package hugohiihto;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Hugo Ski Game Logic Engine (Instance-Based)
 * UI-independent game logic.
 */
public class HugoHiihtoLogic {

    // --- Game Event Constants (for communication with FX UI) ---
    public static final int EVENT_NONE = 0;
    // Add more specific events as needed, e.g., for hits, score, special items
    public static final int EVENT_ENEMY_HIT = 100;
    public static final int EVENT_GAME_OVER_LOGIC = 101;
    public static final int EVENT_LEVEL_COMPLETE_LOGIC = 102;


    // --- Game State Constants (internal or for UI) ---
    public static final int STATE_PRE_TITLE = 0;
    public static final int STATE_TITLE_SCREEN = 1; // Example
    public static final int STATE_VIDEO_PLAYING = 2;
    public static final int STATE_GAME_PLAYING = 3;
    public static final int STATE_GAME_OVER_SCREEN = 4; // Example
    public static final int STATE_LEVEL_COMPLETE_SCREEN = 5; // Example


    // --- Instance Variables for Core Game State ---
    private Timer timer;
    private TimerTask gameLoopTask;

    private boolean gameOver;
    private int currentLevelSegment; // Represents progress through the ski level
    private int numberOfLives;
    private int score; // Simplified score for now
    private String[] stageHazardDefinitions; // Holds the 'EEMM', 'oBEE' etc. strings

    private boolean gameLogicActive; // Whether the game loop should be processing updates
    private int currentOverallState; // Tracks the broader state of the game (title, playing, video, etc.)
    private int pendingGameEvent; // For signaling specific occurrences to the UI

    private int currentHugoLane; // Hugo's current lane (0-3)

    // Constants for game design
    private static final int MAX_LEVEL_SEGMENTS = 70; // End of the ski part
    private static final int INITIAL_LIVES = 4;
    private static final String[] DEFAULT_HAZARD_PATTERNS = {
        "EEEE", "ME8E", "EM8E", "EEME", "E8EM", "8EEE", "EEE8", "EoEE", // 0-7
        "EEEE", "QQEQ", "QEQQ", "EQQE", "QEMQ", "BMEE", "oBEE", "EEBo", // 8-15 (item1@15)
        "EEEE", "QEEB", "EoQQ", "QQoE", "ME8E", "EM8E", "EEME", "E8EM", // 16-23
        "8EEE", "EEE8", "EoEE", "EEEE", "QQEQ", "QEQQ", "EQQE", "QEMQ", // 24-31 (item2@26)
        "BMEE", "oBEE", "EEBo", "EEEE", "QEEB", "EoQQ", "QQoE", "SSSS", // 32-39 (Scylla@39)
        // Fill up to 70, these are placeholders
        "QQEQ", "QEQQ", "EQQE", "QEMQ", "BMEE", "oBEE", "EEBo", "QEEB", 
        "EoQQ", "QQoE", "ME8E", "EM8E", "EEME", "E8EM", "8EEE", "EEE8", 
        "EoEE", "EEEE", "QQEQ", "QEQQ", "EQQE", "QEMQ", "BMEE", "oBEE", 
        "EEBo", "QEEB", "EoQQ", "QQoE", "ME8E", "FFFF" // Finish@70
    };


    public HugoHiihtoLogic() {
        this.stageHazardDefinitions = DEFAULT_HAZARD_PATTERNS; // Simplified hazard loading
        // Initialize with pre-game defaults
        this.gameOver = true;
        this.currentLevelSegment = -5; 
        this.numberOfLives = INITIAL_LIVES;
        this.score = 0;
        this.gameLogicActive = false;
        this.currentOverallState = STATE_PRE_TITLE;
        this.pendingGameEvent = EVENT_NONE;
        this.currentHugoLane = 1; // Default starting lane (0, 1, 2, 3)
        System.out.println("HugoHiihtoLogic: Instance created.");
    }

    public void gameReset(int gameSpeedMs) {
        if (gameSpeedMs <= 500 || gameSpeedMs >= 5000) {
            gameSpeedMs = 1700; // Default speed
        }
        System.out.println("HugoHiihtoLogic: Game reset called. Speed: " + gameSpeedMs + "ms");
        
        this.gameOver = false;
        this.currentLevelSegment = -5; // Start before actual gameplay segments
        this.numberOfLives = INITIAL_LIVES;
        this.score = 0;
        this.currentHugoLane = 1;
        this.pendingGameEvent = EVENT_NONE;
        this.currentOverallState = STATE_GAME_PLAYING; // Game is now considered active
        this.gameLogicActive = true; // Start the logic ticking

        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer(true); 
        this.gameLoopTask = new GameLoop();
        this.timer.scheduleAtFixedRate(this.gameLoopTask, 0, gameSpeedMs);
        System.out.println("HugoHiihtoLogic: Game loop started.");
    }

    // --- Getters for UI to poll game state ---
    public int getCurrentLevelSegment() { return this.currentLevelSegment; }
    public int getNumberOfLives() { return this.numberOfLives; }
    public boolean isGameOver() { return this.gameOver; }
    public int getScore() { return this.score; }
    public boolean isGameLogicActive() { return this.gameLogicActive; }
    public int getCurrentOverallState() { return this.currentOverallState; }
    public int getPendingGameEvent() { return this.pendingGameEvent; }
    public int getCurrentHugoLane() { return this.currentHugoLane; }
    
    public String getCurrentHazardString() {
        if (currentLevelSegment >= 0 && currentLevelSegment < stageHazardDefinitions.length) {
            return stageHazardDefinitions[currentLevelSegment];
        }
        return "EEEE"; // Default empty if out of bounds
    }
    
    // --- Public methods to be called by UI (e.g., based on input) ---
    public void setHugoLane(int lane) {
        if (lane >= 0 && lane <= 3) { // Assuming 4 lanes (0, 1, 2, 3)
            this.currentHugoLane = lane;
        }
    }
    public void setCurrentOverallState(int state) { this.currentOverallState = state; }
    public void clearPendingGameEvent() { this.pendingGameEvent = EVENT_NONE; }
    
    public void requestPauseGame() { this.gameLogicActive = false; }
    public void requestResumeGame() { this.gameLogicActive = true; }


    // --- Inner GameLoop Class (Simplified for this refactoring stage) ---
    public class GameLoop extends TimerTask {
        @Override
        public void run() {
            if (!gameLogicActive || gameOver) {
                if (gameOver && timer != null) {
                    timer.cancel(); // Stop timer if game is definitively over
                }
                return; // Do nothing if logic is paused or game is over
            }

            currentLevelSegment++;
            // System.out.println("HugoHiihtoLogic: Loop tick, segment: " + currentLevelSegment);

            // Simplified hazard check (example: lose life on specific segments for testing)
            if (currentLevelSegment >= 0 && currentLevelSegment < stageHazardDefinitions.length) {
                String hazardsOnThisSegment = stageHazardDefinitions[currentLevelSegment];
                char hazardOnHugoLane = hazardsOnThisSegment.charAt(currentHugoLane);

                if (hazardOnHugoLane == '8' || hazardOnHugoLane == 'o' || hazardOnHugoLane == 'Q' || hazardOnHugoLane == 'B') {
                    numberOfLives--;
                    score -= 50; // Penalty
                    pendingGameEvent = EVENT_ENEMY_HIT; // Signal UI about the hit
                    gameLogicActive = false; // Pause logic for UI to handle event
                    System.out.println("HugoHiihtoLogic: Hit hazard '" + hazardOnHugoLane + "' at segment " + currentLevelSegment + " on lane " + currentHugoLane + ". Lives: " + numberOfLives);
                } else if (hazardOnHugoLane == 'M') {
                    score += 100; // Collect money
                }
            }


            if (numberOfLives <= 0) {
                gameOver = true;
                pendingGameEvent = EVENT_GAME_OVER_LOGIC;
                gameLogicActive = false; // Stop logic
                System.out.println("HugoHiihtoLogic: Game Over. Final Score: " + score + ", Segment: " + currentLevelSegment);
            }

            if (currentLevelSegment >= MAX_LEVEL_SEGMENTS) {
                // gameOver = true; // Or set a specific "level complete" state
                pendingGameEvent = EVENT_LEVEL_COMPLETE_LOGIC;
                currentOverallState = STATE_LEVEL_COMPLETE_SCREEN; // Example state
                gameLogicActive = false; // Stop ski logic
                System.out.println("HugoHiihtoLogic: Reached end of level. Final Score: " + score + ", Segment: " + currentLevelSegment);
            }
        }
    }
    
    public void stopGameLogic() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null; // Allow it to be GC'd
        }
        this.gameLogicActive = false;
        System.out.println("HugoHiihtoLogic: Game loop explicitly stopped.");
    }
}
