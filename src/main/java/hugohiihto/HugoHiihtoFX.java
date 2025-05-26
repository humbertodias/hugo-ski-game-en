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
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.awt.Component; 
import java.awt.event.InputEvent;


public class HugoHiihtoFX extends Application {

    private MediaView mediaView;
    private ImageView backgroundImageView; 
    private Pane gameRoot; 

    private Game_Display gameDisplay;
    private HugoHiihto gameLogic;


    private static final int WINDOW_WIDTH = 630;
    private static final int WINDOW_HEIGHT = 500;

    private ImageView hugoView;
    private ImageView cloudView;
    private ImageView[] treeViews = new ImageView[8];
    private ImageView[] hazardViews = new ImageView[4];
    private ImageView[] scoreDigitViews = new ImageView[6];
    private ImageView[] lifeViews = new ImageView[3];
    private ImageView scorebarView;
    private ImageView pauseView;

    private ImageView asteriskView, bellView, clockView, diamondView, hashtagView, starView;
    private ImageView[] uSelectionViews = new ImageView[3]; // u1b, u2b, u3b
    private ImageView[] dSelectionViews = new ImageView[3]; // d1b, d2b, d3b
    private ImageView[] uSelectedViews = new ImageView[3];  // u1w, u2w, u3w
    private ImageView[] dSelectedViews = new ImageView[3];  // d1w, d2w, d3w

    private Map<String, Image> imageCache = new HashMap<>();
    private Game_Display.AL keyAdapterInstance;
    private static final DummyAwtComponent DUMMY_AWT_COMPONENT = new DummyAwtComponent();

    private static class DummyAwtComponent extends Component {} 

    public Game_Display getGameDisplay() {
        return gameDisplay;
    }

    @Override
    public void start(Stage primaryStage) {
        Game_Display.hugoHiihtoFXInstance = this; 

        try {
            gameDisplay = new Game_Display();
            keyAdapterInstance = gameDisplay.new AL(); 
        } catch (Exception e) {
            Logger.getLogger(HugoHiihtoFX.class.getName()).log(Level.SEVERE, "Failed to initialize Game_Display.", e);
            Platform.exit();
            return;
        }

        mediaView = new MediaView();
        mediaView.setFitWidth(WINDOW_WIDTH);
        mediaView.setFitHeight(WINDOW_HEIGHT);
        mediaView.setVisible(false);

        backgroundImageView = new ImageView();
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);
        backgroundImageView.setPreserveRatio(false);

        gameRoot = new Pane();
        gameRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        initializeImageViewPool();

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, gameRoot, mediaView);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        scene.setOnKeyPressed(this::handleFxKeyPress);
        scene.setOnKeyReleased(this::handleFxKeyRelease);

        primaryStage.setTitle("HugoHiihtoFX - v" + Game_Display.VERSION);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            if (HugoHiihto.timerTask != null) HugoHiihto.timerTask.cancel(); 
            if (gameDisplay != null) {
                gameDisplay.stopAllSoundsAndMusic(); 
            }
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        HugoHiihto.gameReset(Game_Display.GAMESPEED);
        gameLogic = HugoHiihto.hugoHiihto;
        gameDisplay.game_state = 0;
        Game_Display.nextState = 0;
        gameDisplay.constructFrames(gameDisplay.game_state); 

        AnimationTimer gameLoopTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameDisplay.game_state != Game_Display.nextState) {
                    gameDisplay.game_state = Game_Display.nextState;
                    gameDisplay.constructFrames(gameDisplay.game_state);
                }
                if (gameDisplay.game_state == 3 && !Game_Display.gamePaused) {
                    updateAnimatedElementPositions();
                }
                render();
            }
        };
        gameLoopTimer.start();
    }
    
    private void updateAnimatedElementPositions() {
        // Cloud
        if (System.currentTimeMillis() % 19 == 0) { 
            if (Game_Display.leftWind) gameDisplay.cloud_x_position--; else gameDisplay.cloud_x_position++;
        }
        double cloudWidth = gameDisplay.cloud != null ? gameDisplay.cloud.getWidth() : 300;
        if (gameDisplay.cloud_x_position < -cloudWidth && Game_Display.leftWind) {
            gameDisplay.cloud_x_position = WINDOW_WIDTH;
        }
        if (gameDisplay.cloud_x_position > WINDOW_WIDTH && !Game_Display.leftWind) {
            gameDisplay.cloud_x_position = - (int)cloudWidth;
        }

        // Trees
        int[] treeX = {gameDisplay.possibleTree1_x_position, gameDisplay.possibleTree2_x_position, gameDisplay.possibleTree3_x_position, gameDisplay.possibleTree4_x_position, gameDisplay.possibleTree5_x_position, gameDisplay.possibleTree6_x_position, gameDisplay.possibleTree7_x_position, gameDisplay.possibleTree8_x_position};
        int[] treeY = {gameDisplay.possibleTree1_y_position, gameDisplay.possibleTree2_y_position, gameDisplay.possibleTree3_y_position, gameDisplay.possibleTree4_y_position, gameDisplay.possibleTree5_y_position, gameDisplay.possibleTree6_y_position, gameDisplay.possibleTree7_y_position, gameDisplay.possibleTree8_y_position};
        Image[] treeImages = {gameDisplay.possibleTree1, gameDisplay.possibleTree2, gameDisplay.possibleTree3, gameDisplay.possibleTree4, gameDisplay.possibleTree5, gameDisplay.possibleTree6, gameDisplay.possibleTree7, gameDisplay.possibleTree8};
        int[] treeInitialX = {WINDOW_WIDTH / 8 - 7, WINDOW_WIDTH / 4 - 4, WINDOW_WIDTH / 5 - 4, WINDOW_WIDTH / 2 + 40, WINDOW_WIDTH / 2 + 40, WINDOW_WIDTH / 2 + 40, WINDOW_WIDTH / 2 + 42, WINDOW_WIDTH / 9};
        int[] treeInitialY = {WINDOW_HEIGHT / 12 + 20, WINDOW_HEIGHT / 8 + 20, WINDOW_HEIGHT / 17 + 20, WINDOW_HEIGHT / 3 - 100, WINDOW_HEIGHT / 3 - 80, WINDOW_HEIGHT / 3 - 80, WINDOW_HEIGHT / 3 - 82, WINDOW_HEIGHT / 7 + 32};
        
        for (int i = 0; i < 8; i++) {
            if (System.currentTimeMillis() % 2 == 0) { 
                if (i < 3 || i == 7) treeX[i] -= 2; 
                else treeX[i] += 2; 
                treeY[i]++;
            }
            
            double currentTreeWidth = treeImages[i] != null ? treeImages[i].getWidth() : 60; 
            boolean offScreenLeft = (i < 3 || i == 7) && treeX[i] < -currentTreeWidth;
            boolean offScreenRight = !(i < 3 || i == 7) && treeX[i] > WINDOW_WIDTH;
            if (offScreenLeft || offScreenRight || treeY[i] > WINDOW_HEIGHT) { 
                treeX[i] = treeInitialX[i];
                treeY[i] = treeInitialY[i];
            }
        }
        gameDisplay.possibleTree1_x_position = treeX[0]; gameDisplay.possibleTree1_y_position = treeY[0];
        gameDisplay.possibleTree2_x_position = treeX[1]; gameDisplay.possibleTree2_y_position = treeY[1];
        gameDisplay.possibleTree3_x_position = treeX[2]; gameDisplay.possibleTree3_y_position = treeY[2];
        gameDisplay.possibleTree4_x_position = treeX[3]; gameDisplay.possibleTree4_y_position = treeY[3];
        gameDisplay.possibleTree5_x_position = treeX[4]; gameDisplay.possibleTree5_y_position = treeY[4];
        gameDisplay.possibleTree6_x_position = treeX[5]; gameDisplay.possibleTree6_y_position = treeY[5];
        gameDisplay.possibleTree7_x_position = treeX[6]; gameDisplay.possibleTree7_y_position = treeY[6];
        gameDisplay.possibleTree8_x_position = treeX[7]; gameDisplay.possibleTree8_y_position = treeY[7];
    }


    private Image loadImage(String path) {
        if (path == null || path.isEmpty() || path.endsWith("null.png") || path.endsWith("numbersnull.png") ) return null;
        return imageCache.computeIfAbsent(path, p -> {
            try {
                Image img = new Image(getClass().getResourceAsStream(p));
                if (img.isError()) {
                    Logger.getLogger(HugoHiihtoFX.class.getName()).log(Level.WARNING, "Failed to load image, error in image: " + p, img.getException());
                    return null;
                }
                return img;
            } catch (Exception e) {
                Logger.getLogger(HugoHiihtoFX.class.getName()).log(Level.WARNING, "Failed to load image resource: " + p, e);
                return null;
            }
        });
    }

    private void initializeImageViewPool() {
        hugoView = new ImageView(); gameRoot.getChildren().add(hugoView);
        cloudView = new ImageView(); gameRoot.getChildren().add(cloudView);
        scorebarView = new ImageView(); gameRoot.getChildren().add(scorebarView);
        pauseView = new ImageView(); gameRoot.getChildren().add(pauseView);

        for (int i = 0; i < treeViews.length; i++) { treeViews[i] = new ImageView(); gameRoot.getChildren().add(treeViews[i]); }
        for (int i = 0; i < hazardViews.length; i++) { hazardViews[i] = new ImageView(); gameRoot.getChildren().add(hazardViews[i]); }
        for (int i = 0; i < scoreDigitViews.length; i++) { scoreDigitViews[i] = new ImageView(); gameRoot.getChildren().add(scoreDigitViews[i]); }
        for (int i = 0; i < lifeViews.length; i++) { lifeViews[i] = new ImageView(); gameRoot.getChildren().add(lifeViews[i]); }
        
        asteriskView = new ImageView(); gameRoot.getChildren().add(asteriskView);
        bellView = new ImageView(); gameRoot.getChildren().add(bellView);
        clockView = new ImageView(); gameRoot.getChildren().add(clockView);
        diamondView = new ImageView(); gameRoot.getChildren().add(diamondView);
        hashtagView = new ImageView(); gameRoot.getChildren().add(hashtagView);
        starView = new ImageView(); gameRoot.getChildren().add(starView);

        for(int i=0; i<3; i++) {
            uSelectionViews[i] = new ImageView(); gameRoot.getChildren().add(uSelectionViews[i]);
            dSelectionViews[i] = new ImageView(); gameRoot.getChildren().add(dSelectionViews[i]);
            uSelectedViews[i] = new ImageView(); gameRoot.getChildren().add(uSelectedViews[i]);
            dSelectedViews[i] = new ImageView(); gameRoot.getChildren().add(dSelectedViews[i]);
        }
        gameRoot.getChildren().forEach(node -> node.setVisible(false));
    }

    private void setImageViewProperties(ImageView iv, Image img, double x, double y, boolean visible) {
        if (iv == null) return;
        iv.setImage(img);
        iv.setX(x);
        iv.setY(y);
        iv.setVisible(visible && img != null);
        if (img == null && visible) iv.setVisible(false);
    }
    
    private void setImageViewPropertiesSized(ImageView iv, Image img, double x, double y, double w, double h, boolean visible) {
        if (iv == null) return;
        setImageViewProperties(iv, img, x, y, visible);
        if (visible && img != null) {
            iv.setFitWidth(w);
            iv.setFitHeight(h);
        }
    }

    private void render() {
        gameRoot.getChildren().forEach(node -> node.setVisible(false));
        backgroundImageView.setImage(null);
        mediaView.setVisible(false);

        switch (gameDisplay.game_state) {
            case 0:
                backgroundImageView.setImage(gameDisplay.theVeryFirst);
                break;
            case 1:
                Image img = (HugoHiihto.currentStateAtTheLevel >= 71 && !HugoHiihto.gameOver) ?
                            gameDisplay.creditsScreen : gameDisplay.titleScreen;
                backgroundImageView.setImage(img);
                break;
            case 2:
                backgroundImageView.setImage(gameDisplay.videoimg);
                break;
            case 3:
                renderMainGame();
                break;
            case 4:
                renderRememberItemsScreen();
                break;
            case 5:
                backgroundImageView.setImage(gameDisplay.scoreBGR);
                renderScoreScreenElements();
                break;
            default:
                backgroundImageView.setImage(null); 
                break;
        }
    }

    private void renderMainGame() {
        backgroundImageView.setImage(gameDisplay.bg);
        
        setImageViewProperties(cloudView, gameDisplay.cloud, gameDisplay.cloud_x_position, gameDisplay.cloud_y_position, true);
        
        setImageViewProperties(treeViews[0], gameDisplay.possibleTree1, gameDisplay.possibleTree1_x_position, gameDisplay.possibleTree1_y_position, true);
        setImageViewProperties(treeViews[1], gameDisplay.possibleTree2, gameDisplay.possibleTree2_x_position, gameDisplay.possibleTree2_y_position, true);
        setImageViewProperties(treeViews[2], gameDisplay.possibleTree3, gameDisplay.possibleTree3_x_position, gameDisplay.possibleTree3_y_position, true);
        setImageViewProperties(treeViews[3], gameDisplay.possibleTree4, gameDisplay.possibleTree4_x_position, gameDisplay.possibleTree4_y_position, true);
        setImageViewProperties(treeViews[4], gameDisplay.possibleTree5, gameDisplay.possibleTree5_x_position, gameDisplay.possibleTree5_y_position, true);
        setImageViewProperties(treeViews[5], gameDisplay.possibleTree6, gameDisplay.possibleTree6_x_position, gameDisplay.possibleTree6_y_position, true);
        setImageViewProperties(treeViews[6], gameDisplay.possibleTree7, gameDisplay.possibleTree7_x_position, gameDisplay.possibleTree7_y_position, true);
        setImageViewProperties(treeViews[7], gameDisplay.possibleTree8, gameDisplay.possibleTree8_x_position, gameDisplay.possibleTree8_y_position, true);

        // Visibility check for hazards: ensure image is loaded, dimensions are valid, and it's within screen bounds.
        setImageViewPropertiesSized(hazardViews[0], gameDisplay.currentHazardOrMoney1_image, Game_Display.currentHazardOrMoney1_x_position, Game_Display.currentHazardOrMoney1_y_position, Game_Display.currentHazardOrMoney1w, Game_Display.currentHazardOrMoney1h, gameDisplay.currentHazardOrMoney1_image != null && Game_Display.currentHazardOrMoney1w > 1 && Game_Display.currentHazardOrMoney1_y_position < WINDOW_HEIGHT && Game_Display.currentHazardOrMoney1_x_position < WINDOW_WIDTH && Game_Display.currentHazardOrMoney1_x_position + Game_Display.currentHazardOrMoney1w > 0 && Game_Display.currentHazardOrMoney1_y_position + Game_Display.currentHazardOrMoney1h > 0);
        setImageViewPropertiesSized(hazardViews[1], gameDisplay.currentHazardOrMoney2_image, Game_Display.currentHazardOrMoney2_x_position, Game_Display.currentHazardOrMoney2_y_position, Game_Display.currentHazardOrMoney2w, Game_Display.currentHazardOrMoney2h, gameDisplay.currentHazardOrMoney2_image != null && Game_Display.currentHazardOrMoney2w > 1 && Game_Display.currentHazardOrMoney2_y_position < WINDOW_HEIGHT && Game_Display.currentHazardOrMoney2_x_position < WINDOW_WIDTH && Game_Display.currentHazardOrMoney2_x_position + Game_Display.currentHazardOrMoney2w > 0 && Game_Display.currentHazardOrMoney2_y_position + Game_Display.currentHazardOrMoney2h > 0);
        setImageViewPropertiesSized(hazardViews[2], gameDisplay.currentHazardOrMoney3_image, Game_Display.currentHazardOrMoney3_x_position, Game_Display.currentHazardOrMoney3_y_position, Game_Display.currentHazardOrMoney3w, Game_Display.currentHazardOrMoney3h, gameDisplay.currentHazardOrMoney3_image != null && Game_Display.currentHazardOrMoney3w > 1 && Game_Display.currentHazardOrMoney3_y_position < WINDOW_HEIGHT && Game_Display.currentHazardOrMoney3_x_position < WINDOW_WIDTH && Game_Display.currentHazardOrMoney3_x_position + Game_Display.currentHazardOrMoney3w > 0 && Game_Display.currentHazardOrMoney3_y_position + Game_Display.currentHazardOrMoney3h > 0);
        setImageViewPropertiesSized(hazardViews[3], gameDisplay.currentHazardOrMoney4_image, Game_Display.currentHazardOrMoney4_x_position, Game_Display.currentHazardOrMoney4_y_position, Game_Display.currentHazardOrMoney4w, Game_Display.currentHazardOrMoney4h, gameDisplay.currentHazardOrMoney4_image != null && Game_Display.currentHazardOrMoney4w > 1 && Game_Display.currentHazardOrMoney4_y_position < WINDOW_HEIGHT && Game_Display.currentHazardOrMoney4_x_position < WINDOW_WIDTH && Game_Display.currentHazardOrMoney4_x_position + Game_Display.currentHazardOrMoney4w > 0 && Game_Display.currentHazardOrMoney4_y_position + Game_Display.currentHazardOrMoney4h > 0);
        
        setImageViewProperties(scorebarView, gameDisplay.scorebar, gameDisplay.scorebar_x_position, gameDisplay.scorebar_y_position, true);

        setImageViewProperties(scoreDigitViews[0], loadImage(gameDisplay.getScoreDigitPath(Game_Display.hundredThousands)), gameDisplay.digitFromLeft1_x_position, gameDisplay.digitFromLeft1_y_position, Game_Display.hundredThousandsVisible);
        setImageViewProperties(scoreDigitViews[1], loadImage(gameDisplay.getScoreDigitPath(Game_Display.tenThousands)), gameDisplay.digitFromLeft2_x_position, gameDisplay.digitFromLeft2_y_position, Game_Display.tenThousandsVisible);
        setImageViewProperties(scoreDigitViews[2], loadImage(gameDisplay.getScoreDigitPath(Game_Display.thousands)), gameDisplay.digitFromLeft3_x_position, gameDisplay.digitFromLeft3_y_position, Game_Display.thousandsVisible);
        setImageViewProperties(scoreDigitViews[3], loadImage(gameDisplay.getScoreDigitPath(Game_Display.hundreds)), gameDisplay.digitFromLeft4_x_position, gameDisplay.digitFromLeft4_y_position, Game_Display.hundredsVisible);
        setImageViewProperties(scoreDigitViews[4], loadImage(gameDisplay.getScoreDigitPath(Game_Display.tens)), gameDisplay.digitFromLeft5_x_position, gameDisplay.digitFromLeft5_y_position, Game_Display.tensVisible);
        setImageViewProperties(scoreDigitViews[5], loadImage(gameDisplay.getScoreDigitPath(Game_Display.ones)), gameDisplay.digitFromLeft6_x_position, gameDisplay.digitFromLeft6_y_position, Game_Display.onesVisible);

        setImageViewProperties(lifeViews[0], gameDisplay.hugolife1, gameDisplay.hugolife1_x_position, gameDisplay.hugolife1_y_position, Game_Display.number_of_lives > 1.5);
        setImageViewProperties(lifeViews[1], gameDisplay.hugolife2, gameDisplay.hugolife2_x_position, gameDisplay.hugolife2_y_position, Game_Display.number_of_lives > 2.5);
        setImageViewProperties(lifeViews[2], gameDisplay.hugolife3, gameDisplay.hugolife3_x_position, gameDisplay.hugolife3_y_position, Game_Display.number_of_lives > 3.5);
        
        Image hugoImg = (Game_Display.currentGrid < 2) ? 
                        ((Game_Display.currentGrid == 0) ? gameDisplay.sprite_L2 : gameDisplay.sprite_L) :
                        ((Game_Display.currentGrid == 3) ? gameDisplay.sprite_R2 : gameDisplay.sprite_R);
        setImageViewProperties(hugoView, hugoImg, Game_Display.x, Game_Display.y, true);

        setImageViewProperties(pauseView, gameDisplay.pause, gameDisplay.pause_x_position, gameDisplay.pause_y_position, Game_Display.gamePaused && Game_Display.pausedWithEnter);
    }

    private void renderScoreScreenElements() {
        int scoreXOffset = WINDOW_WIDTH / 11; 
        int scoreYPos = WINDOW_HEIGHT / 2 - 40; 
        
        setImageViewProperties(scoreDigitViews[0], loadImage(gameDisplay.getScoreDigitPath(Game_Display.hundredThousands)), scoreXOffset, scoreYPos, Game_Display.hundredThousandsVisible);
        setImageViewProperties(scoreDigitViews[1], loadImage(gameDisplay.getScoreDigitPath(Game_Display.tenThousands)), scoreXOffset + 50, scoreYPos, Game_Display.tenThousandsVisible);
        setImageViewProperties(scoreDigitViews[2], loadImage(gameDisplay.getScoreDigitPath(Game_Display.thousands)), scoreXOffset + 100, scoreYPos, Game_Display.thousandsVisible);
        setImageViewProperties(scoreDigitViews[3], loadImage(gameDisplay.getScoreDigitPath(Game_Display.hundreds)), scoreXOffset + 150, scoreYPos, Game_Display.hundredsVisible);
        setImageViewProperties(scoreDigitViews[4], loadImage(gameDisplay.getScoreDigitPath(Game_Display.tens)), scoreXOffset + 200, scoreYPos, Game_Display.tensVisible);
        setImageViewProperties(scoreDigitViews[5], loadImage(gameDisplay.getScoreDigitPath(Game_Display.ones)), scoreXOffset + 250, scoreYPos, Game_Display.onesVisible);

        Image ropeImageToDraw = Game_Display.pulled_rope_1 ? gameDisplay.r1 : (Game_Display.pulled_rope_3 ? gameDisplay.r3 : gameDisplay.r2);
        if (ropeImageToDraw != null) { 
             setImageViewPropertiesSized(hugoView, ropeImageToDraw, scoreXOffset - 40, scoreYPos + 70, ropeImageToDraw.getWidth() - 30, ropeImageToDraw.getHeight() - 30, true);
        } else {
            hugoView.setVisible(false);
        }
    }
    
    private void renderRememberItemsScreen() {
        backgroundImageView.setImage(gameDisplay.bgCave);

        setImageViewProperties(asteriskView, gameDisplay.asterisk, gameDisplay.asterisk_x_position, gameDisplay.asterisk_y_position, gameDisplay.asterisk != null);
        setImageViewProperties(bellView, gameDisplay.bell, gameDisplay.bell_x_position, gameDisplay.bell_y_position, gameDisplay.bell != null);
        setImageViewProperties(clockView, gameDisplay.clock, gameDisplay.clock_x_position, gameDisplay.clock_y_position, gameDisplay.clock != null);
        setImageViewProperties(diamondView, gameDisplay.diamond, gameDisplay.diamond_x_position, gameDisplay.diamond_y_position, gameDisplay.diamond != null);
        setImageViewProperties(hashtagView, gameDisplay.hashtag, gameDisplay.hashtag_x_position, gameDisplay.hashtag_y_position, gameDisplay.hashtag != null);
        setImageViewProperties(starView, gameDisplay.star, gameDisplay.star_x_position, gameDisplay.star_y_position, gameDisplay.star != null);
        
        // Default black-bordered selection boxes
        setImageViewProperties(uSelectionViews[0], gameDisplay.u1b, gameDisplay.u1b_x_position, gameDisplay.u1b_y_position, true);
        setImageViewProperties(uSelectionViews[1], gameDisplay.u2b, gameDisplay.u2b_x_position, gameDisplay.u2b_y_position, true);
        setImageViewProperties(uSelectionViews[2], gameDisplay.u3b, gameDisplay.u3b_x_position, gameDisplay.u3b_y_position, true);
        
        // Hide white-bordered (selected) views by default
        for(int i=0; i<3; i++) { uSelectedViews[i].setVisible(false); dSelectedViews[i].setVisible(false); }

        if (Game_Display.secondPhase || Game_Display.allCorrectInTheEnd) {
            setImageViewProperties(dSelectionViews[0], gameDisplay.d1b, gameDisplay.d1b_x_position, gameDisplay.d1b_y_position, true);
            setImageViewProperties(dSelectionViews[1], gameDisplay.d2b, gameDisplay.d2b_x_position, gameDisplay.d2b_y_position, true);
            setImageViewProperties(dSelectionViews[2], gameDisplay.d3b, gameDisplay.d3b_x_position, gameDisplay.d3b_y_position, true);
        } else {
             for(int i=0; i<3; i++) dSelectionViews[i].setVisible(false); // Hide lower row if not yet in second phase
        }
        
        // Logic for highlighting selected items (showing white-bordered ImageViews)
        if (Game_Display.secondPhase) { 
            char[] tr = Game_Display.thingsToRemember.toCharArray(); // e.g., "AcdBhs"
            if (tr.length >= 6) { 
                // Upper row highlights (u1w, u2w, u3w)
                if (Character.isUpperCase(tr[0])) setImageViewProperties(uSelectedViews[0], gameDisplay.u1w, gameDisplay.u1b_x_position, gameDisplay.u1b_y_position, true);
                if (Character.isUpperCase(tr[1])) setImageViewProperties(uSelectedViews[1], gameDisplay.u2w, gameDisplay.u2b_x_position, gameDisplay.u2b_y_position, true);
                if (Character.isUpperCase(tr[2])) setImageViewProperties(uSelectedViews[2], gameDisplay.u3w, gameDisplay.u3b_x_position, gameDisplay.u3b_y_position, true);

                // Lower row highlights (d1w, d2w, d3w) - only if allCorrectInTheEnd
                if (Game_Display.allCorrectInTheEnd) {
                    if (Character.isUpperCase(tr[3])) setImageViewProperties(dSelectedViews[0], gameDisplay.d1w, gameDisplay.d1b_x_position, gameDisplay.d1b_y_position, true);
                    if (Character.isUpperCase(tr[4])) setImageViewProperties(dSelectedViews[1], gameDisplay.d2w, gameDisplay.d2b_x_position, gameDisplay.d2b_y_position, true);
                    if (Character.isUpperCase(tr[5])) setImageViewProperties(dSelectedViews[2], gameDisplay.d3w, gameDisplay.d3b_x_position, gameDisplay.d3b_y_position, true);
                }
            }
        }
    }
    
    private void handleFxKeyPress(javafx.scene.input.KeyEvent fxEvent) {
        java.awt.event.KeyEvent awtEvent = convertFxToAwtKeyEvent(fxEvent, java.awt.event.KeyEvent.KEY_PRESSED);
        if (awtEvent != null && keyAdapterInstance != null) {
            keyAdapterInstance.keyPressed(awtEvent);
        }
        fxEvent.consume(); 
    }

    private void handleFxKeyRelease(javafx.scene.input.KeyEvent fxEvent) {
        java.awt.event.KeyEvent awtEvent = convertFxToAwtKeyEvent(fxEvent, java.awt.event.KeyEvent.KEY_RELEASED);
        if (awtEvent != null && keyAdapterInstance != null) {
            keyAdapterInstance.keyReleased(awtEvent);
        }
        fxEvent.consume();
    }

    private java.awt.event.KeyEvent convertFxToAwtKeyEvent(javafx.scene.input.KeyEvent fxEvent, int awtEventId) {
        int awtKeyCode = fxKeyCodeToAwtKeyCode(fxEvent.getCode());
        // If VK_UNDEFINED, try to get from character for letters/digits if text is present
        if (awtKeyCode == java.awt.event.KeyEvent.VK_UNDEFINED && fxEvent.getCode() != KeyCode.UNDEFINED) {
            if (fxEvent.getText() != null && !fxEvent.getText().isEmpty()) {
                char typedChar = fxEvent.getText().toUpperCase().charAt(0);
                if (typedChar >= 'A' && typedChar <= 'Z') {
                    awtKeyCode = typedChar; 
                } else if (typedChar >= '0' && typedChar <= '9') {
                    awtKeyCode = typedChar;
                } else {
                    return null; 
                }
            } else {
                return null; 
            }
        }

        long time = System.currentTimeMillis();
        int modifiers = 0;
        if (fxEvent.isShiftDown()) modifiers |= InputEvent.SHIFT_DOWN_MASK;
        if (fxEvent.isControlDown()) modifiers |= InputEvent.CTRL_DOWN_MASK;
        if (fxEvent.isAltDown()) modifiers |= InputEvent.ALT_DOWN_MASK;
        if (fxEvent.isMetaDown()) modifiers |= InputEvent.META_DOWN_MASK; 
        
        char keyChar = java.awt.event.KeyEvent.CHAR_UNDEFINED;
        if (fxEvent.getText() != null && !fxEvent.getText().isEmpty()) {
            if (fxEvent.getText().length() == 1) keyChar = fxEvent.getText().charAt(0);
        } else if (awtKeyCode >= java.awt.event.KeyEvent.VK_A && awtKeyCode <= java.awt.event.KeyEvent.VK_Z') {
            keyChar = (char)awtKeyCode; // Simplification, doesn't handle shift state for case
        } else if (awtKeyCode >= java.awt.event.KeyEvent.VK_0 && awtKeyCode <= java.awt.event.KeyEvent.VK_9) {
            keyChar = (char)awtKeyCode; 
        } else if (awtKeyCode >= java.awt.event.KeyEvent.VK_NUMPAD0 && awtKeyCode <= java.awt.event.KeyEvent.VK_NUMPAD9) {
            keyChar = (char) (awtKeyCode - java.awt.event.KeyEvent.VK_NUMPAD0 + '0');
        }


        return new java.awt.event.KeyEvent(DUMMY_AWT_COMPONENT, awtEventId, time, modifiers, awtKeyCode, keyChar);
    }

    private int fxKeyCodeToAwtKeyCode(KeyCode fxCode) {
        // This mapping needs to be comprehensive for all keys used by Game_Display.AL
        switch (fxCode) {
            case ENTER: return java.awt.event.KeyEvent.VK_ENTER;
            case ESCAPE: return java.awt.event.KeyEvent.VK_ESCAPE;
            case LEFT: return java.awt.event.KeyEvent.VK_LEFT;
            case RIGHT: return java.awt.event.KeyEvent.VK_RIGHT;
            case UP: return java.awt.event.KeyEvent.VK_UP;
            case DOWN: return java.awt.event.KeyEvent.VK_DOWN;
            case DIGIT0: return java.awt.event.KeyEvent.VK_0;
            case DIGIT1: return java.awt.event.KeyEvent.VK_1;
            case DIGIT2: return java.awt.event.KeyEvent.VK_2;
            case DIGIT3: return java.awt.event.KeyEvent.VK_3;
            case DIGIT4: return java.awt.event.KeyEvent.VK_4;
            case DIGIT5: return java.awt.event.KeyEvent.VK_5;
            case DIGIT6: return java.awt.event.KeyEvent.VK_6;
            case DIGIT7: return java.awt.event.KeyEvent.VK_7;
            case DIGIT8: return java.awt.event.KeyEvent.VK_8;
            case DIGIT9: return java.awt.event.KeyEvent.VK_9;
            case NUMPAD0: return java.awt.event.KeyEvent.VK_NUMPAD0;
            case NUMPAD1: return java.awt.event.KeyEvent.VK_NUMPAD1;
            case NUMPAD2: return java.awt.event.KeyEvent.VK_NUMPAD2;
            case NUMPAD3: return java.awt.event.KeyEvent.VK_NUMPAD3;
            case NUMPAD4: return java.awt.event.KeyEvent.VK_NUMPAD4;
            case NUMPAD5: return java.awt.event.KeyEvent.VK_NUMPAD5;
            case NUMPAD6: return java.awt.event.KeyEvent.VK_NUMPAD6;
            case NUMPAD7: return java.awt.event.KeyEvent.VK_NUMPAD7;
            case NUMPAD8: return java.awt.event.KeyEvent.VK_NUMPAD8;
            case NUMPAD9: return java.awt.event.KeyEvent.VK_NUMPAD9;
            case A: return java.awt.event.KeyEvent.VK_A; case B: return java.awt.event.KeyEvent.VK_B;
            case C: return java.awt.event.KeyEvent.VK_C; case D: return java.awt.event.KeyEvent.VK_D;
            case E: return java.awt.event.KeyEvent.VK_E; case F: return java.awt.event.KeyEvent.VK_F;
            case G: return java.awt.event.KeyEvent.VK_G; case H: return java.awt.event.KeyEvent.VK_H;
            case I: return java.awt.event.KeyEvent.VK_I; case J: return java.awt.event.KeyEvent.VK_J;
            case K: return java.awt.event.KeyEvent.VK_K; case L: return java.awt.event.KeyEvent.VK_L;
            case M: return java.awt.event.KeyEvent.VK_M; case N: return java.awt.event.KeyEvent.VK_N;
            case O: return java.awt.event.KeyEvent.VK_O; case P: return java.awt.event.KeyEvent.VK_P;
            case Q: return java.awt.event.KeyEvent.VK_Q; case R: return java.awt.event.KeyEvent.VK_R;
            case S: return java.awt.event.KeyEvent.VK_S; case T: return java.awt.event.KeyEvent.VK_T;
            case U: return java.awt.event.KeyEvent.VK_U; case V: return java.awt.event.KeyEvent.VK_V;
            case W: return java.awt.event.KeyEvent.VK_W; case X: return java.awt.event.KeyEvent.VK_X;
            case Y: return java.awt.event.KeyEvent.VK_Y; case Z: return java.awt.event.KeyEvent.VK_Z;
            default: return java.awt.event.KeyEvent.VK_UNDEFINED;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
