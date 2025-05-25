package hugohiihto;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HugoSkiFX extends Application {

    private static final int WIDTH = 630;
    private static final int HEIGHT = 500;
    private static final int HUGO_GAME_SPEED = 1700; // ms per game logic tick

    private AnimationTimer gameLoop;
    private Pane root;
    private Scene scene;

    // UI Elements
    private ImageView backgroundView;
    private Image backgroundImage;
    private ImageView cloudView;
    private Image cloudImage;
    private ImageView firstTextsView; // Title/Instructions
    private ImageView hugoView;
    private Image hugoSkiLImage;
    private Image hugoSkiRImage;
    private ImageView[] hazardViews = new ImageView[4];
    private Map<Character, Image> hazardImageMap = new HashMap<>();
    private Text scoreTextNode;
    private Text livesTextNode;

    // Positioning and animation state
    private double cloudX, cloudY;
    private boolean isCloudMovingLeft;
    private long lastCloudUpdateTime = 0;
    private static final long CLOUD_UPDATE_INTERVAL_NS = 50 * 1_000_000;
    private final double[] laneXPositions = new double[4];
    private double hugoYPosition;
    private double hazardYPosition; // Static Y for hazards for now

    // Scylla Intro Video
    private ImageView scyllaGifView;
    private Image scyllaGifImage;
    private MediaPlayer scyllaAudioPlayer;
    private Pane videoPane;
    private boolean isVideoPlaying = false;

    // Hugo's game logic and state
    private HugoHiihtoLogic gameLogicEngine;
    // private int currentHugoLaneFX = 1; // FX-side representation, to be synced with gameLogicEngine


    private Image loadImage(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        InputStream stream = HugoSkiFX.class.getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Could not load image: " + path + " (stream is null)");
            return null;
        }
        try {
            return new Image(stream);
        } catch (Exception e) {
            System.err.println("Exception while creating Image object for " + path + ": " + e.getMessage());
            return null;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (Exception e) { /* ignore */ }
        }
    }

    private void loadHazardImages() {
        hazardImageMap.put('8', loadImage("res/enemy_snowman.png"));
        hazardImageMap.put('o', loadImage("res/enemy_snowball.png"));
        hazardImageMap.put('Q', loadImage("res/enemy_bomb.png"));
        hazardImageMap.put('B', loadImage("res/enemy_beaver_masi.png"));
        hazardImageMap.put('M', loadImage("res/money.png"));
        // 'E' (empty) will naturally result in null from map.get, which is handled.
        // '1' and '2' for scroll items would need their own images if displayed directly as hazards.
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        gameLogicEngine = new HugoHiihtoLogic();

        // Define lane positions (approximate centers, adjust based on actual sprite widths)
        // Original calculation: x = 55; maxW = 630 - 220 = 410; trackWidth = 410 / 3 = ~136
        // laneXPositions[0] = 55 + (hugoSkiLImage != null ? hugoSkiLImage.getWidth()/2 : 45); // Centered
        // laneXPositions[1] = 55 + 136 + (hugoSkiLImage != null ? hugoSkiLImage.getWidth()/2 : 45);
        // laneXPositions[2] = 55 + 2*136 + (hugoSkiRImage != null ? hugoSkiRImage.getWidth()/2 : 45);
        // laneXPositions[3] = 55 + 3*136 + (hugoSkiRImage != null ? hugoSkiRImage.getWidth()/2 : 45);
        // For simplicity, using estimated fixed points for now. Assume sprites are ~90px wide.
        laneXPositions[0] = 55;    // Center for ~90px sprite: 55 - 45 = 10 (original x)
        laneXPositions[1] = 190;   // 55 + 135
        laneXPositions[2] = 325;   // 55 + 2*135
        laneXPositions[3] = 460;   // 55 + 3*135
        hugoYPosition = HEIGHT * 0.60; // Adjusted Y for Hugo
        hazardYPosition = HEIGHT * 0.30; // Y for hazards appearing


        // --- Initialize UI elements ---
        loadHazardImages(); // Populate the map

        backgroundImage = loadImage("res/background1.gif");
        if (backgroundImage != null) {
            backgroundView = new ImageView(backgroundImage);
            backgroundView.setFitWidth(WIDTH);
            backgroundView.setFitHeight(HEIGHT);
            root.getChildren().add(backgroundView);
        }

        firstTextsView = new ImageView(loadImage("res/_the_very_1st_texts.png"));
        if (firstTextsView.getImage() != null) {
             root.getChildren().add(firstTextsView);
             firstTextsView.setVisible(true); // Visible initially
        } else {
            firstTextsView = null;
        }

        cloudImage = loadImage("res/cloud.png");
        if (cloudImage != null) {
            cloudView = new ImageView(cloudImage);
            cloudX = WIDTH / 3.0; cloudY = HEIGHT / 25.0; isCloudMovingLeft = false;
            cloudView.setTranslateX(cloudX); cloudView.setTranslateY(cloudY);
            root.getChildren().add(cloudView);
        }

        hugoSkiLImage = loadImage("res/hugo_ski_L.gif");
        hugoSkiRImage = loadImage("res/hugo_ski_R.gif");
        if (hugoSkiLImage != null) {
            hugoView = new ImageView(hugoSkiLImage); // Default to L
            hugoView.setTranslateY(hugoYPosition);
            hugoView.setVisible(false); // Initially hidden
            root.getChildren().add(hugoView);
        }

        for (int i = 0; i < hazardViews.length; i++) {
            hazardViews[i] = new ImageView();
            hazardViews[i].setTranslateY(hazardYPosition);
            hazardViews[i].setVisible(false); // Initially hidden
            root.getChildren().add(hazardViews[i]);
        }

        scoreTextNode = new Text("Score: 0");
        scoreTextNode.setFont(Font.font("Verdana", 20));
        scoreTextNode.setFill(Color.WHITE);
        scoreTextNode.setX(10); scoreTextNode.setY(30);
        root.getChildren().add(scoreTextNode);

        livesTextNode = new Text("Lives: " + INITIAL_LIVES); // Use constant from logic if available, or define here
        livesTextNode.setFont(Font.font("Verdana", 20));
        livesTextNode.setFill(Color.WHITE);
        livesTextNode.setX(WIDTH - 100); livesTextNode.setY(30); // Position top right
        root.getChildren().add(livesTextNode);


        // --- Setup Scylla Intro Media ---
        scyllaGifImage = loadImage("res/scylla_intro_s.gif");
        if (scyllaGifImage != null) {
            scyllaGifView = new ImageView(scyllaGifImage);
            scyllaGifView.setFitWidth(WIDTH); scyllaGifView.setFitHeight(HEIGHT);
        }
        URL audioResource = HugoSkiFX.class.getResource("/res/scylla_intro.aiff");
        if (audioResource != null) {
            try {
                Media scyllaAudioMedia = new Media(audioResource.toExternalForm());
                scyllaAudioPlayer = new MediaPlayer(scyllaAudioMedia);
                scyllaAudioPlayer.setOnEndOfMedia(this::stopScyllaIntroAndSetTitleState);
            } catch (Exception e) { scyllaAudioPlayer = null; System.err.println("Err Scylla Audio: "+e.getMessage());}
        } else { System.err.println("No Scylla audio res."); }
        
        videoPane = new Pane();
        if (scyllaGifView != null) videoPane.getChildren().add(scyllaGifView);
        videoPane.setVisible(false);
        root.getChildren().add(videoPane);

        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Hugo Ski FX");
        primaryStage.setScene(scene);
        primaryStage.show();

        setupInputHandlers();
        gameLoop = new AnimationTimer() { @Override public void handle(long now) { updateGameFX(now); } };
        gameLoop.start();
        
        if (gameLogicEngine != null) {
            gameLogicEngine.setCurrentOverallState(HugoHiihtoLogic.STATE_PRE_TITLE);
        }
    }

    private void setupInputHandlers() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ESCAPE) { Platform.exit(); event.consume(); }
            else if (code == KeyCode.ENTER) { handleEnterKey(); event.consume(); }
            else if (code == KeyCode.LEFT || code == KeyCode.DIGIT4 || code == KeyCode.NUMPAD4) { handleLaneChange(false); event.consume(); }
            else if (code == KeyCode.RIGHT || code == KeyCode.DIGIT6 || code == KeyCode.NUMPAD6) { handleLaneChange(true); event.consume(); }
            else if (code == KeyCode.V) {
                if (!isVideoPlaying && gameLogicEngine != null && gameLogicEngine.getCurrentOverallState() != HugoHiihtoLogic.STATE_GAME_PLAYING) {
                    triggerScyllaIntro();
                }
                event.consume();
            }
        });
    }

    private void handleEnterKey() {
        if (isVideoPlaying) {
            stopScyllaIntroAndSetTitleState();
        } else if (gameLogicEngine != null && !gameLogicEngine.isGameLogicActive() && 
                   (gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_PRE_TITLE ||
                    gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_TITLE_SCREEN || /* Add if you define this state */
                    gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_GAME_OVER_SCREEN ||
                    gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_LEVEL_COMPLETE_SCREEN 
                   )) {
            System.out.println("Enter key: Starting/Restarting game logic.");
            if (firstTextsView != null) firstTextsView.setVisible(false);
            gameLogicEngine.gameReset(HUGO_GAME_SPEED);
        } else if (gameLogicEngine != null && gameLogicEngine.isGameLogicActive() &&
                   gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_GAME_PLAYING) {
            System.out.println("Enter key during gameplay: Pausing/Resuming (placeholder).");
            if(gameLogicEngine.isGameLogicActive()) gameLogicEngine.requestPauseGame(); else gameLogicEngine.requestResumeGame();
        }
    }

    private void handleLaneChange(boolean isRight) {
        if (!isVideoPlaying && gameLogicEngine != null && gameLogicEngine.isGameLogicActive()) {
            int currentLane = gameLogicEngine.getCurrentHugoLane();
            if (isRight) { currentLane++; if (currentLane > 3) currentLane = 3; }
            else { currentLane--; if (currentLane < 0) currentLane = 0; }
            gameLogicEngine.setHugoLane(currentLane);
            // Visual update of Hugo's sprite will happen in updateGameFX
        }
    }

    private void triggerScyllaIntro() {
        if (isVideoPlaying || scyllaAudioPlayer == null || scyllaGifView == null) return;
        if (gameLogicEngine != null) {
             if(gameLogicEngine.isGameLogicActive()) gameLogicEngine.requestPauseGame();
             gameLogicEngine.setCurrentOverallState(HugoHiihtoLogic.STATE_VIDEO_PLAYING);
        }
        isVideoPlaying = true;
        if (firstTextsView != null) firstTextsView.setVisible(false);
        if (cloudView != null) cloudView.setVisible(false);
        if (backgroundView != null) backgroundView.setVisible(false);
        if (hugoView != null) hugoView.setVisible(false);
        for(ImageView hv : hazardViews) if(hv!=null) hv.setVisible(false);
        
        videoPane.setVisible(true);
        scyllaAudioPlayer.seek(Duration.ZERO); scyllaAudioPlayer.play();
    }

    private void stopScyllaIntroAndSetTitleState() {
        isVideoPlaying = false;
        if (scyllaAudioPlayer != null) scyllaAudioPlayer.stop();
        videoPane.setVisible(false);

        if (firstTextsView != null) firstTextsView.setVisible(true);
        if (cloudView != null) cloudView.setVisible(true);
        if (backgroundView != null) backgroundView.setVisible(true);
        
        if (gameLogicEngine != null) {
            gameLogicEngine.setCurrentOverallState(HugoHiihtoLogic.STATE_PRE_TITLE);
        }
    }

    private void updateGameFX(long now) {
        if (isVideoPlaying) {
            // Hide all game elements if video is playing
            if (hugoView != null) hugoView.setVisible(false);
            for(ImageView hv : hazardViews) if(hv!=null) hv.setVisible(false);
            if (scoreTextNode != null) scoreTextNode.setVisible(false); // Or keep score visible
            if (livesTextNode != null) livesTextNode.setVisible(false); // Or keep lives visible
            if (firstTextsView != null) firstTextsView.setVisible(false);
            if (cloudView != null) cloudView.setVisible(true); // Clouds can stay
            if (backgroundView != null) backgroundView.setVisible(false); // Hide game background
            return;
        }

        // Cloud Animation (always active unless video is playing)
        if (cloudImage != null && cloudView != null) { /* ... cloud logic same as before ... */ 
            if ((now - lastCloudUpdateTime) > CLOUD_UPDATE_INTERVAL_NS) {
                lastCloudUpdateTime = now; double cloudSpeed = 1.0;
                if (isCloudMovingLeft) { cloudX -= cloudSpeed; if (cloudX < -cloudImage.getWidth()) cloudX = WIDTH; }
                else { cloudX += cloudSpeed; if (cloudX > WIDTH) cloudX = -cloudImage.getWidth(); }
                cloudView.setTranslateX(cloudX);
            }
        }

        if (gameLogicEngine != null) {
            boolean isPlaying = gameLogicEngine.isGameLogicActive() && gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_GAME_PLAYING;
            
            // Visibility based on game state
            if (firstTextsView != null) firstTextsView.setVisible(!isPlaying && !isVideoPlaying && gameLogicEngine.getCurrentOverallState() == HugoHiihtoLogic.STATE_PRE_TITLE);
            if (hugoView != null) hugoView.setVisible(isPlaying);
            for(ImageView hv : hazardViews) if(hv!=null) hv.setVisible(isPlaying); // Visibility will be fine-tuned below
            if (scoreTextNode != null) scoreTextNode.setVisible(isPlaying || gameLogicEngine.isGameOver()); // Show score if playing or game over
            if (livesTextNode != null) livesTextNode.setVisible(isPlaying || gameLogicEngine.isGameOver());

            if (isPlaying) {
                // Update Hugo's position and sprite
                int currentLane = gameLogicEngine.getCurrentHugoLane();
                if (hugoView != null) {
                    hugoView.setTranslateX(laneXPositions[currentLane] - (hugoView.getImage()!=null ? hugoView.getImage().getWidth()/2 : 0) );
                    hugoView.setImage((currentLane < 2) ? hugoSkiLImage : hugoSkiRImage);
                }

                // Update Hazards
                String hazardStr = gameLogicEngine.getCurrentHazardString();
                for (int i = 0; i < hazardViews.length; i++) {
                    if (i < hazardStr.length()) {
                        char hazardChar = hazardStr.charAt(i);
                        Image hazardImg = hazardImageMap.get(hazardChar);
                        hazardViews[i].setImage(hazardImg);
                        if (hazardImg != null) {
                            hazardViews[i].setTranslateX(laneXPositions[i] - hazardImg.getWidth()/2);
                            // Y position of hazards might need animation later
                            hazardViews[i].setVisible(true);
                        } else {
                            hazardViews[i].setVisible(false);
                        }
                    } else {
                        hazardViews[i].setVisible(false); // Should not happen if hazardStr always length 4
                    }
                }

                // Update Score and Lives Text
                scoreTextNode.setText("Score: " + gameLogicEngine.getScore());
                livesTextNode.setText("Lives: " + gameLogicEngine.getNumberOfLives());

                // Handle game events from logic engine
                int gameEvent = gameLogicEngine.getPendingGameEvent();
                if (gameEvent != HugoHiihtoLogic.EVENT_NONE) {
                    System.out.println("FX: Game Event: " + gameEvent);
                    if (gameEvent == HugoHiihtoLogic.EVENT_ENEMY_HIT || gameEvent == HugoHiihtoLogic.EVENT_GAME_OVER_LOGIC || gameEvent == HugoHiihtoLogic.EVENT_LEVEL_COMPLETE_LOGIC) {
                        // For these events, game logic pauses itself. FX might show something then resume.
                        // For now, just log and clear. If it's game over/level complete, logic is already stopped.
                        if(gameEvent == HugoHiihtoLogic.EVENT_ENEMY_HIT) {
                             // FX could show hit effect, then:
                             gameLogicEngine.clearPendingGameEvent();
                             gameLogicEngine.requestResumeGame(); // Tell logic to continue after hit processing
                        } else {
                             gameLogicEngine.clearPendingGameEvent(); // For game over / level complete
                        }
                    } else {
                         gameLogicEngine.clearPendingGameEvent(); // Clear other events
                    }
                }
                if (gameLogicEngine.isGameOver()) {
                     System.out.println("FX: Game Over detected. UI should reflect this.");
                     // Additional FX-side game over logic (e.g., show game over screen)
                }
            } else if (gameLogicEngine.isGameOver()){
                // Logic for game over screen if needed (e.g. keep score visible)
                 if (firstTextsView != null) firstTextsView.setText("GAME OVER! Score: " + gameLogicEngine.getScore() + "\nPress Enter to Restart"); // Example
                 if (firstTextsView != null) firstTextsView.setVisible(true);

            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (gameLoop != null) gameLoop.stop();
        if (scyllaAudioPlayer != null) { scyllaAudioPlayer.stop(); scyllaAudioPlayer.dispose(); }
        if (gameLogicEngine != null) gameLogicEngine.stopGameLogic();
        System.out.println("Application stopped.");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
