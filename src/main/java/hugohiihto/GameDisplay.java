package hugohiihto;

import hugohiihto.thread.grid.GridS01Thread;
import hugohiihto.thread.grid.GridS23Thread;
import hugohiihto.thread.hazard.HAZ1Thread;
import hugohiihto.thread.hazard.HAZ2Thread;
import hugohiihto.thread.hazard.HAZ3Thread;
import hugohiihto.thread.hazard.HAZ4Thread;
import hugohiihto.type.GameState;
import hugohiihto.type.RememberType;
import hugohiihto.type.VideoType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player; // library FMJ - http://fmj-sf.net/
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * Hugo Ski Game v1.1 by Tuomas Hyvönen, 10/2024
 * <p>
 * New in this version:
 * - English texts (but I wanted to keep the Finnish Harri Hyttinen voices).
 * - The background ski tracks have a little animation.
 * - There are old cell phone sounds when pressing 4 and 6.
 * - Some files are edited, for example the GIF files have smaller sizes.
 * - The player does not get stuck when pressing 2 buttons simultaneously.
 * - There are 2 more tree objects and the cloud can move both left and right.
 * - The player has 4 lives instead of 3, the zeroth life counts as a life.
 * - There is a cheat code "9700 4954 4744".
 * - A Commodore64 music has been added to the credits screen.
 * - Scoring is different.
 * - The graphics match a bit better with what is really happening according to the game's logic.
 * - The score bar does not show unnecessary zeros and the lives are on the left side instead of right.
 * <p>
 * - There can still be bugs and other flaws, I am not a perfect programmer.
 * You should be happy if the application even starts up in the first place.
 * If a video cut scene does not start, please move the mouse cursor on the game window.
 * <p>
 * The game window cannot be resized currently, press the Windows key then +Plus/-Minus to zoom in/out.
 * Windows Magnifier/zooming instructions:
 * https://www.androidpolice.com/how-to-zoom-in-out-on-windows-10-11/
 * Something like this should be possible on Linux, MAC etc. too but without Windows button...
 * <p>
 * Thanks for trying out this game! I do not own the Hugo franchise.
 * Sharing Java codes and random files on the Internet should not be a crime - as far as I do not cause harm to people or their money.
 * The purpose is to share the fun with people and keep the Hugo franchise alive.
 * <p>
 * Java must be installed before playing the game:
 * https://www.java.com/en/download/manual.jsp
 * <p>
 * <p>
 * "Work is not man's punishment. It is his reward and his strength and his pleasure."
 * - George Sand, French novelist
 * <p>
 * "Love is a serious mental disease."      (especially for Hugo franchise)
 * - Plato, Greek philosopher
 * <p>
 * "We can easily forgive a child who is afraid of the dark; the real tragedy of life is when men are afraid of the light."
 * - Plato
 * <p>
 * https://www.brainyquote.com
 * <p>
 * ----
 * <p>
 * Please support the original and official Hugo releases! They have been the inspiration for this game.
 * Consider this game to be treated the same way as "Mega Man Unlimited".
 * https://megamanfanon.fandom.com/wiki/Mega_Man_Unlimited
 * <p>
 * Or if you're mean, treat this the same way as Metroid AM2R.
 * https://en.wikipedia.org/wiki/AM2R
 * <p>
 * Or Commodore64 Super Mario Bros. that Nintendo also did not treat well.
 * https://archive.org/details/Super_Mario_Bros_C64_Zeropaige
 * <p>
 * <p>
 * Thanks to this discussion for huge progress in game development when getting started:
 * https://stackoverflow.com/questions/12082660/background-image-for-simple-game
 * <p>
 * "The beginning is the most important part of the work."
 * - also from Plato
 * <p>
 * ----
 * <p>
 * Some Hugo creators not involved in this project, thanks for each one of them!
 * Consider this as "the real credits screen":
 * <p>
 * https://screentroll.fandom.com/wiki/Hugo_(PlayStation)
 * Producers: Ivan Sølvason, Lars Rikart Jensen, Troels Gram, Jens C. Ringdal
 * Design: Niels Krogh Mortensen, Troels Gram, Lars Rikart Jensen
 * Programmers: Peter Marino, Poul-Jesper Olsen, Ole Thomas Jensen, Mario Gomes, Claes Hougaard,
 * Michael Barklund, Erik Schack Andersen, John Dideriksen, Troels Gram, Jørgen Lortentsen
 * Graphics: Chadi Freigeh, Claus Friese, Thomas Skellund, Mark Gregory, Jakob Steffensen, Peter Eide Paulsen,
 * Jørgen Trolle Ørberg, John Madsen, Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup,
 * Anders Morgenthaler, Torben Bakager Larsen, Jesper Laugesen
 * Music and sound: David A. Filskov, Christian Steen Jensen.
 * <p>
 * https://screentroll.fandom.com/wiki/Hugo_2_(PlayStation)
 * Producer: Ivan Sølvason, Lars Rikart Jensen. Piet N. Kargaard
 * Design: Mario Gomes, Peter Eide Paulsen, Poul Engelbrech Madsen, John Dideriksen
 * Programming: Mario Gomes, Jesper Olsen, Anders Emil Hansen, Ole T. Jensen
 * Graphics: Claus Friese, Chadi Freigeh, Peter Eide Paulsen, Piet N. Kargaard, John Madsen, Jørgen Trolle Ørberg
 * Music and sound: David A. Filskov, Christian Steen Jensen, Klaus Mulvad Nielsen, Asbjørn Andersen
 * Other: Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, Anders Morgenthaler, Torben Bakager Larsen,
 * Jesper Laugesen, Tom Westerman, Thomas Skellund, Espen Toft Jacobsen, Laust Palbo Nielsen.
 * <p>
 * https://screentroll.fandom.com/wiki/Hugo_5
 * Production manager: Ivan Sølvason
 * Programmers: Jakob Frandsen, Bo Krohn, Kim Frederiksen, Lasse S. Tassing, Jens Nordahl
 * TV programmers: Kim Frederiksen, Stig Jørgensen
 * Graphics: Lars Krogh Mortensen, Laust Palbo Nielsen, Tom Westermann, Esben Toft Jacobsen,
 * Jakob Steffensen, Jesper Eskildsen, Thomas Skellund
 * Music and sound effects: Mads Kristensen, David Filskov.
 * <p>
 * https://screentroll.fandom.com/wiki/Hugo_(1996_video_game)
 * Producer: Ivan Sølvason
 * PC programming: Jakob Frandsen, Lasse Tassing, Kim Frederisken, Troels Gram
 * TV programming: Stig Jørgansen, Kim Frederisken, Bo Krohn, Morten Hansen, Esben Krag Hansen
 * Graphics: Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, Anders Morgenthaler,
 * Laust Palbo Nielsen, Thomas Skellund, Torben Bakager, Martin De Thurah
 * Music and sound effects: Mads Kriestensen, Nicolai Thilo, Thomas Engell, Jørgen Traun.
 * <p>
 * https://fi.wikipedia.org/wiki/DJ_Hugo
 * https://fi.wikipedia.org/wiki/Hugo_(televisio-ohjelma)
 * Music: Slotmachine featuring Gemini 7, Kata Laurikainen, Anssi Ahonen, Jaana Rinne
 * Other: Harri Hyttinen, Eija Ahvo, Jussi-Pekka Koskiranta, Pekka Kossila, Ari Meriläinen, Taru Valkeapää, Marika Saukkonen.
 * <p>
 * Guyus the Raptor and other uploaders at YouTube etc.
 * Commodore 64 music by Jens-Christian Huus
 * <p>
 * ----
 * <p>
 * <p>
 * The game display class. The other class is HugoHiihto.java.
 * The main method is here, check the end of this file.
 * <p>
 * Tested with Microsoft Windows 11
 * Java developed by Oracle / Sun Microsystems.
 * Created with Apache NetBeans 23
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide
 *
 * @author Tuomas Hyvönen
 * @version 1.1.ENG
 */
public final class GameDisplay extends JPanel {
    public static final String VERSION = "1.1.ENG";
    public static final Duration GAMESPEED = Duration.ofMillis(1700);      // in milliseconds

    // does not update graphics!
    GameState gameState = GameState.PRE_TITLE;
    GameState nextState = GameState.PRE_TITLE;
    boolean useMP4 = false; // if false, use gifs that might flicker if made incorrectly
    // should be false though because mp4s will open a new window currently, Windows Media Player for instance. 
    // The original Hugo graphics and sounds have been edited.
    VideoType video = VideoType.SCYLLA_INTRO;
    ImageIcon videoIMGicon;
    Image videoimg = null; // .gif expected + .aiff for sound 
    boolean cheatBackflip180 = false;
    boolean key1 = false;
    boolean key2 = false;
    boolean key3 = false;
    boolean key4 = false;
    boolean key5 = false;
    boolean key6 = false;
    boolean key7 = false;
    boolean key8 = false;
    boolean key9 = false;
    boolean key10 = false;
    boolean key11 = false;
    boolean key12 = false;
    Player mediaPlayer = null;
    Clip clip0 = null; // music or sound
    Clip clip1 = null; // music or sound
    Clip clip2 = null; // music or sound
    Clip clip3 = null; // music or sound
    Clip clip4 = null; // music or sound
    Clip clipH = null; // music or sound, skiing
    // twistedwave.com sound edit tool is a helpful example tool when editing .wav or .aiff files
    Clip clipMoney = null;
    File fileMoney = new File("res/money.wav");
    Clip clipScore = null;
    File fileScore = new File("res/points_score.wav");

    File fileGameMusic0 = new File("res/music-ps1hugo2menu.wav");
    File fileGameMusic1 = new File("res/music-djhugopopcorn.wav");
    File fileGameMusic2 = new File("res/music_credits.wav");
    // 2 other musics but they go straight to "clip" use.
    //  Hugo 2 PlayStation 1 title/menu
    //  Popcorn Slotmachine featuring Gemini 7 (1993 Finnish DJ Hugo charity CD tr 8)
    //  Hugo classic skateboard music Hugo 1 PlayStation 1
    //  Ending song Commodore64 Hugo rap
    //  Finnish Hugo TV show theme song (1993 Finnish DJ Hugo charity CD tr 1)
    // As the programmer I own nothing. The original creators listed above do not lose money when these tracks are included in this game.
    // Do not upload valuable video games and TV show music to YouTube if they have strict copyrights!

    // ezgif.com  &  redketchup.io/gif-resizer  were useful services for video gif polishing
    boolean pulled_rope_1 = false; // good ending        1
    boolean pulled_rope_2 = false; // bad ending         2
    boolean pulled_rope_3 = false; // the best ending    3
    Image r1;
    Image r2;
    Image r3;

    public boolean gamePaused = false;
    boolean pausedWithEnter = false; // 2 types of pausing: interruption before a video and pause on purpose by the player
    String thingsToRemember = "dsHAcb";   // will be random later , end 0s and 1s are just extra if present
    boolean currentlyAllCorrect = true;
    boolean secondPhase = false; // these are in guessing 123 123 for the skull cave key
    boolean allCorrectInTheEnd = false;      // (Scylla has weird locks and why does she even give the 2 scroll key clues to Hugo?)

    public static Dimension d = new Dimension(630, 500);
    public int maxW = d.width - 220;

    int w_width = (int) d.getWidth() / 7; // hugo skiing animation
    int w_height = (int) d.getHeight() / 3;
    int e_width = (int) d.getWidth() / 7;
    int e_height = (int) d.getHeight() / 3;

    public int x, y;
    public int currentGrid = 0; // or line should be 0, 1, 2 or 3, nothing else
    // Hugo will always go forward and <- & -> change the line on the current phase

    Image theVeryFirst;
    Image titleScreen;  // instructions for how to play
    Image creditsScreen;

    Image sprite_R; // Hugo showing up on 4 lines/grids/tracks
    Image sprite_L;
    Image sprite_R2;
    Image sprite_L2;
    Image bg;

    int cave_x, cave_y;
    Image bgCave;

    public boolean leftWind = false;

    Sprite hugolife1Sprite = new Sprite();
    Sprite hugolife2Sprite = new Sprite();
    Sprite hugolife3Sprite = new Sprite();

    Sprite digitFromLeft1 = new Sprite();
    Sprite digitFromLeft2 = new Sprite();
    Sprite digitFromLeft3 = new Sprite();
    Sprite digitFromLeft4 = new Sprite();
    Sprite digitFromLeft5 = new Sprite();
    Sprite digitFromLeft6 = new Sprite();

    public boolean vanish4Faster = false;
    Image currentHazardOrMoney1_image;
    public int currentHazardOrMoney1_x_position;
    public int currentHazardOrMoney1_y_position;
    Image currentHazardOrMoney2_image;
    public int currentHazardOrMoney2_x_position;
    public int currentHazardOrMoney2_y_position;

    Image currentHazardOrMoney3_image;
    public int currentHazardOrMoney3_x_position;
    public int currentHazardOrMoney3_y_position;

    Image currentHazardOrMoney4_image;
    public int currentHazardOrMoney4_x_position;
    public int currentHazardOrMoney4_y_position;
    String currentHazardOrMoney1 = "E";
    String currentHazardOrMoney2 = "E";
    String currentHazardOrMoney3 = "E";
    String currentHazardOrMoney4 = "E";
    public int currentHazardOrMoney1w;
    public int currentHazardOrMoney1h;
    public int currentHazardOrMoney2w;
    public int currentHazardOrMoney2h;
    public int currentHazardOrMoney3w;
    public int currentHazardOrMoney3h;
    public int currentHazardOrMoney4w;
    public int currentHazardOrMoney4h;

    int position1 = 10;
    int position2 = 130;
    int position3 = 250;
    int heightLevel1 = 5;
    int heightLevel2 = 150;

    // for score digit values:
    int ones = 0;
    int tens = 0;
    int hundreds = 0;
    int thousands = 0;
    int tenThousands = 0;
    int hundredThousands = 0;
    boolean onesVisible = true;
    boolean tensVisible = false;
    boolean hundredsVisible = false;
    boolean thousandsVisible = false;
    boolean tenThousandsVisible = false;
    boolean hundredThousandsVisible = false;

    int number_of_lives = 4;

    Clip clipChangeGrid = null;
    File fileChangeGrid = new File("res/ski_track_change.wav");

    Clip clipChangeGrid4 = null;
    File fileChangeGrid4 = new File("res/button4sound.wav");

    Clip clipChangeGrid6 = null;
    File fileChangeGrid6 = new File("res/button6sound.wav");

    Clip clipCorrect = null;
    File fileCorrect = new File("res/correct_selection.wav");

    Sprite cloudSprite = new Sprite();

    Sprite tree1Sprite = new Sprite();
    Sprite tree2Sprite = new Sprite();
    Sprite tree3Sprite = new Sprite();
    Sprite tree4Sprite = new Sprite();
    Sprite tree5Sprite = new Sprite();
    Sprite tree6Sprite = new Sprite();
    Sprite tree7Sprite = new Sprite();
    Sprite tree8Sprite = new Sprite();

    Sprite pauseSprite = new Sprite();
    Sprite scoreBarSprite = new Sprite();

    Sprite scoreBGSprite = new Sprite();
    Sprite starSprite = new Sprite();

    public final HugoSkiing hugoSkiing;
    private Sprite u1bSprite = new Sprite();
    private Sprite u1wSprite = new Sprite();
    private Sprite u2bSprite = new Sprite();
    private Sprite u2wSprite = new Sprite();
    private Sprite u3bSprite = new Sprite();
    private Sprite u3wSprite = new Sprite();
    private Sprite d1bSprite = new Sprite();
    private Sprite d1wSprite = new Sprite();
    private Sprite d2bSprite = new Sprite();
    private Sprite d2wSprite = new Sprite();
    private Sprite d3bSprite = new Sprite();
    private Sprite d3wSprite = new Sprite();
    private Sprite asteriskSprite = new Sprite();
    private Sprite bellSprite = new Sprite();
    private Sprite clockSprite = new Sprite();
    private Sprite diamondSprite = new Sprite();
    private Sprite hashTagSprite = new Sprite();
    private Sprite bgCaveSprite = new Sprite();

    /**
     * The constructor.
     */
    public GameDisplay() {
        setDoubleBuffered(true);
        hugoSkiing = new HugoSkiing(this);
        addKeyListener(new GameActionListener(hugoSkiing));
        constructFrames(gameState);
        videoFlush();
    }

    /**
     * Called by the constructor at first, then others can call when needed.
     * Creates the base of the game display, makes a screen update.
     *
     * @param gameState
     */
    public void constructFrames(GameState gameState) {
        if (gameState != nextState) {
            return;
        }

        repaint(); // Initial repaint

        switch (gameState) {
            case PRE_TITLE -> preTitle();
            case TITLE_SCREEN -> titleScreen(gameState);
            case SHOWING_VIDEO -> showingVideo();
            case SKI_GAME -> skiGame();
            case REMEMBER_ITEMS -> rememberItems();
            case GAME_OVER -> gameOver();
        }

        videoFlush();
        repaint(); // Final repaint
    }

    private void gameOver() {
        setFocusable(true);
        scoreBGSprite.load("res/title_screen_nothing.png", (int) d.getWidth(), (int) d.getHeight());
        starSprite.load("res/remember_S_star.png", ((int) d.getWidth() / 6), ((int) d.getHeight() / 19));
    }

    private void rememberItems() {
        bgCaveSprite.load("res/cave_entrance00.png", (int) d.getWidth() - 10, (int) d.getHeight() - 35);

        setFocusable(true);

        cave_x = 1;
        cave_y = 1;

        int baseX = (int) d.getWidth() / 6;
        int baseY = (int) d.getHeight() / 19 + 90;

        // Arrays for positions and height levels
        int[] positions = {position1, position2, position3};
        int[] heightLevels = {heightLevel1, heightLevel1, heightLevel1, heightLevel2, heightLevel2, heightLevel2};

        // Arrays of sprites for easier iteration
        Sprite[] blackSprites = {u1bSprite, u2bSprite, u3bSprite, d1bSprite, d2bSprite, d3bSprite};
        Sprite[] whiteSprites = {u1wSprite, u2wSprite, u3wSprite, d1wSprite, d2wSprite, d3wSprite};

        for (int i = 0; i < 6; i++) {
            int posIndex = i % 3; // 0,1,2 index for positions
            int pos = positions[posIndex];
            int height = heightLevels[i];

            // Construct image paths based on index
            String selectPath = "res/num_select" + (posIndex + 1) + ".png";
            String selectedPath = "res/num_selected" + (posIndex + 1) + ".png";

            int x = baseX + (pos - 2);
            int y = baseY + height;

            // Load the sprites
            blackSprites[i].load(selectPath, x, y);
            whiteSprites[i].load(selectedPath, x, y);

            // Position for the reminder icon
            int iconX = baseX + pos;
            int iconY = (int) d.getHeight() / 19 + height;

            Optional<RememberType> rememberType = RememberType.fromSymbol(thingsToRemember.toUpperCase().charAt(i));
            if(rememberType.isPresent()) {
                switch (rememberType.get()) {
                    case ASTERISK:
                        asteriskSprite.load("res/remember_A_asterisk.png", iconX, iconY);
                        break;
                    case BELL:
                        bellSprite.load("res/remember_B_bell.png", iconX, iconY);
                        break;
                    case CLOCK:
                        clockSprite.load("res/remember_C_clock.png", iconX, iconY);
                        break;
                    case DIAMOND:
                        diamondSprite.load("res/remember_D_diamond.png", iconX, iconY);
                        break;
                    case HASH:
                        hashTagSprite.load("res/remember_H_hash.png", iconX, iconY);
                        break;
                    case STAR:
                        starSprite.load("res/remember_S_star.png", iconX, iconY);
                        break;
                }
            }
        }

    }

    private void skiGame() {
        repaint();
        ImageIcon west = new ImageIcon("res/hugo_ski_L.gif");
        ImageIcon east = new ImageIcon("res/hugo_ski_R.gif");

        w_width = 110;
        west.setImage(west.getImage().getScaledInstance(w_width, w_height, Image.SCALE_DEFAULT));
        sprite_L2 = west.getImage();

        w_width = 90;
        west.setImage(west.getImage().getScaledInstance(w_width, w_height, Image.SCALE_DEFAULT));
        sprite_L = west.getImage();

        e_width = 90;
        east.setImage(east.getImage().getScaledInstance(e_width, e_height, Image.SCALE_DEFAULT));
        sprite_R = east.getImage();

        e_width = 110;
        east.setImage(east.getImage().getScaledInstance(e_width, e_height, Image.SCALE_DEFAULT));
        sprite_R2 = east.getImage();

        ImageIcon j = new ImageIcon("res/background1.gif");
        int wi = (int) d.getWidth() - 10;
        int he = (int) d.getHeight() - 35;
        j.setImage(j.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
        bg = j.getImage();

        setFocusable(true);

        x = 55;
        y = (int) ((int) d.getHeight() / 2.67);

        cloudSprite.load("res/cloud.png", (int) d.getWidth() / 3, (int) d.getHeight() / 25);
        tree1Sprite.load("res/trees2.png", (d.width / 5) - 40, (d.height / 17) + 20);
        tree2Sprite.load("res/trees0.png", (d.width / 5) - 40, (d.height / 17) + 20);
        tree3Sprite.load("res/trees1.png", (d.width / 5) - 40, (d.height / 17) + 20);
        tree4Sprite.load("res/trees2.png", (int) (d.getWidth() / 2) + 70, (int) (d.getHeight() / 4) - 90);
        tree5Sprite.load("res/trees0.png", ((int) d.getWidth() / 2) + 7, ((int) d.getHeight() / 4) - 90);
        tree6Sprite.load("res/trees1.png", ((int) d.getWidth() / 2) + 70, ((int) d.getHeight() / 4) - 90);
        tree7Sprite.load("res/trees2.png", ((int) d.getWidth() / 2) + 72, ((int) d.getHeight() / 4) - 92);
        tree8Sprite.load("res/trees1.png", (d.width / 5) - 42, (d.height / 17) + 22);

        hugolife1Sprite.load("res/hugo_life.png", ((int) d.getWidth() / 55), (int) ((int) d.getHeight() / 1.3));
        hugolife2Sprite.load("res/hugo_life.png", ((int) d.getWidth() / 55) + 80, (int) ((int) d.getHeight() / 1.3));
        hugolife3Sprite.load("res/hugo_life.png", ((int) d.getWidth() / 55) + 160, (int) ((int) d.getHeight() / 1.3));

        pauseSprite.load("res/pause.png", (int) d.getWidth() / 6, (int) d.getHeight() / 3);
        scoreBarSprite.load("res/score-life-bar.png", 0, (int) ((int) d.getHeight() / 1.35));
    }

    private void showingVideo() {
        setFocusable(true);
        if (!useMP4) {
            String pathSound = getResourceAudioVideo(video, ".aiff");
            File fi = new File(pathSound); // .aiff is a well-working sound format for the current video setup
            URL mediaURL = null;
            try {
                mediaURL = fi.toURL();
            } catch (MalformedURLException ex) {
                Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
            }

            try {
                mediaPlayer = Manager.createPlayer(mediaURL);

            } catch (IOException | NoPlayerException ex) {
                Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
            }

            mediaPlayer.start();
            System.out.println("Playing sound for a video");
        }
    }

    private void titleScreen(GameState gameState) {
        if (gameState != GameState.SKI_GAME && nextState != GameState.SKI_GAME) {
            gamePaused = true;
        }

        if (hugoSkiing.currentStateAtTheLevel >= 71 && !hugoSkiing.gameOver) {
            ImageIcon credits_icon = new ImageIcon("res/credits_screen.png");
            int wi = (int) d.getWidth() - 2;
            int he = (int) d.getHeight() - 37;
            credits_icon.setImage(credits_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            creditsScreen = credits_icon.getImage();

            setFocusable(true);
        } else {
            ImageIcon title_icon = new ImageIcon("res/title_screen.png");
            int wi = (int) d.getWidth() - 15;
            int he = (int) d.getHeight() - 20;
            title_icon.setImage(title_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            titleScreen = title_icon.getImage();

            setFocusable(true);
        }
    }

    private void preTitle() {
        ImageIcon theVeryFirst_icon = new ImageIcon("res/_the_very_1st_texts.png");
        int wi = (int) d.getWidth();
        int he = (int) d.getHeight() - 35;
        theVeryFirst_icon.setImage(theVeryFirst_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
        theVeryFirst = theVeryFirst_icon.getImage();

        setFocusable(true);
    }

    /**
     * The paint component method for graphics object(s).
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (gameState) {
            case PRE_TITLE -> paintPreTitle(g);
            case TITLE_SCREEN -> paintTitleScreen(g);
            case SHOWING_VIDEO -> paintShowingVideo(g);
            case SKI_GAME -> paintSkiGame(g);
            case REMEMBER_ITEMS -> paintRememberItems(g);
            case GAME_OVER -> paintGameOver(g);
            case VIDEO_TRANSITION -> paintVideoTransition();
        }
    }


    private void paintVideoTransition() {
        // when moving from state 2 straight back to state 2, a workaround, show 2 videos in a row both with sound
        nextState = GameState.SHOWING_VIDEO;
        System.out.println("------ State change from " + gameState + " to " + nextState);
        gameState = nextState;

        constructFrames(gameState);
        repaint();
    }

    private void paintGameOver(Graphics g) {
        super.paintComponent(g);

        cheatBackflip180 = false;
        key12 = false; // Even if something else was pressed when inputting the 12 numbers, it does not matter.
        key11 = false; // You may press, for example, "9700  2  4954 4744" where 2 is an unnecessary extra press.
        key10 = false;
        key9 = false;
        key8 = false;
        key7 = false;
        key6 = false;
        key5 = false;
        key4 = false;
        key3 = false;
        key2 = false;
        key1 = false;

        String onesToDraw_path = "res/numbers" + ones + ".png";
        String tensToDraw_path = "res/numbers" + tens + ".png";
        String hundredsToDraw_path = "res/numbers" + hundreds + ".png";
        String thousandsToDraw_path = "res/numbers" + thousands + ".png";
        String tenThousandsToDraw_path = "res/numbers" + tenThousands + ".png";
        String hundredThousandsToDraw_path = "res/numbers" + hundredThousands + ".png";

        int digitFromLeft1_x_position = ((int) d.getWidth() / 11);
        int digitFromLeft1_y_position = (int) ((int) d.getHeight() / 2.2);

        digitFromLeft1.load(hundredThousandsToDraw_path, digitFromLeft1_x_position, digitFromLeft1_y_position);
        digitFromLeft2.load(tenThousandsToDraw_path, digitFromLeft1_x_position+50, digitFromLeft1_y_position);
        digitFromLeft3.load(thousandsToDraw_path, digitFromLeft1_x_position+100, digitFromLeft1_y_position);
        digitFromLeft4.load(hundredsToDraw_path, digitFromLeft1_x_position+150, digitFromLeft1_y_position);
        digitFromLeft5.load(tensToDraw_path, digitFromLeft1_x_position+200, digitFromLeft1_y_position);
        digitFromLeft6.load(onesToDraw_path, digitFromLeft1_x_position+250, digitFromLeft1_y_position);

        g.drawImage(scoreBGSprite.getImage(), -10, -18, null);
        repaint();

        if (pulled_rope_1) {
            ImageIcon r1_icon = new ImageIcon("res/rope1good.png");
            int r1_iconw = r1_icon.getIconWidth();
            int r1_iconh = r1_icon.getIconHeight();
            r1_icon.setImage(r1_icon.getImage().getScaledInstance(r1_iconw - 30, r1_iconh - 30, Image.SCALE_DEFAULT));
            r1 = r1_icon.getImage();
            g.drawImage(r1, digitFromLeft1_x_position - 40, digitFromLeft1_y_position + 70, this);
        }
        if (pulled_rope_2 || (!pulled_rope_1 && !pulled_rope_2 && !pulled_rope_3)) {
            ImageIcon r2_icon = new ImageIcon("res/rope2bad.png");
            int r2_iconw = r2_icon.getIconWidth();
            int r2_iconh = r2_icon.getIconHeight();
            r2_icon.setImage(r2_icon.getImage().getScaledInstance(r2_iconw - 30, r2_iconh - 30, Image.SCALE_DEFAULT));
            r2 = r2_icon.getImage();
            g.drawImage(r2, digitFromLeft1_x_position - 40, digitFromLeft1_y_position + 70, this);
        }
        if (pulled_rope_3) {
            ImageIcon r3_icon = new ImageIcon("res/rope3best.png");
            int r3_iconw = r3_icon.getIconWidth();
            int r3_iconh = r3_icon.getIconHeight();
            r3_icon.setImage(r3_icon.getImage().getScaledInstance(r3_iconw - 30, r3_iconh - 30, Image.SCALE_DEFAULT));
            r3 = r3_icon.getImage();
            g.drawImage(r3, digitFromLeft1_x_position - 40, digitFromLeft1_y_position + 70, this);
        }

        if (hundredThousandsVisible)
            digitFromLeft1.drawImage(g,this);
        if (tenThousandsVisible)
            digitFromLeft2.drawImage(g,this);
        if (thousandsVisible)
            digitFromLeft3.drawImage(g,this);
        if (hundredsVisible)
            digitFromLeft4.drawImage(g,this);
        if (tensVisible)
            digitFromLeft5.drawImage(g,this);
        if (onesVisible)
            digitFromLeft6.drawImage(g,this);

        repaint();

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;
            constructFrames(gameState);
            repaint();
        }
    }

    private void paintRememberItems(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgCave, cave_x, cave_y, this); // cave image is based on the sledge Hugo game, a classic winter game

        asteriskSprite.drawImage(g, this);
        bellSprite.drawImage(g, this);
        clockSprite.drawImage(g, this);
        diamondSprite.drawImage(g, this);
        hashTagSprite.drawImage(g, this);
        starSprite.drawImage(g, this);

        if (currentlyAllCorrect) {
            u1bSprite.drawImage(g, this);
            u2bSprite.drawImage(g, this);
            u3bSprite.drawImage(g, this);
        }

        Set<Character> allowedChars = RememberType.getSymbols();
        if (secondPhase && currentlyAllCorrect) {
            if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(0))) {
                u1wSprite.drawImage(g, this);
            }
            if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(1))) {
                u2wSprite.drawImage(g, this);
            }
            if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(2))) {
                u3wSprite.drawImage(g, this);
            }

            d1bSprite.drawImage(g, this);
            d2bSprite.drawImage(g, this);
            d3bSprite.drawImage(g, this);

            if (allCorrectInTheEnd) {
                if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(3))) {
                    d1wSprite.drawImage(g, this);
                }
                if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(4))) {
                    d2wSprite.drawImage(g, this);
                }
                if (allowedChars.contains(thingsToRemember.toUpperCase().charAt(5))) {
                    d3wSprite.drawImage(g, this);
                }
                video = VideoType.TWO_CHOSEN_CORRECTLY;
                nextState = GameState.VIDEO_TRANSITION;
            }
        }
        if (!currentlyAllCorrect) {
            video = VideoType.WRONG_CHOICE;
            nextState = GameState.VIDEO_TRANSITION;
        }
        repaint();

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;
            constructFrames(gameState);
            repaint();
        }
    }

    private void paintSkiGame(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);

        Thread Cloud = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (System.currentTimeMillis() % 19 == 0) {
                if (leftWind) {
                    cloudSprite.x--;
                } else {
                    cloudSprite.x++;
                }
            }
            if (cloudSprite.getX() < -300 && leftWind) {
                cloudSprite.setX(d.width);
            }
            if (cloudSprite.getX() > 700 && !leftWind) {
                cloudSprite.setX(cloudSprite.getX() - 1000);
            }
        });
        if (!Cloud.isAlive()) {
            Cloud.start();
        }

        Thread Tr1 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree1Sprite.x -= 2;
                    tree1Sprite.y++;
                }
                if (tree1Sprite.x < -340) {
                    tree1Sprite.x = (d.width / 8) - 7;
                    tree1Sprite.y = (d.height / 12) + 20;
                }
            }
        });
        if (!Tr1.isAlive()) {
            Tr1.start();
        }

        Thread Tr2 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree2Sprite.x -= 2;
                    tree2Sprite.y++;
                }
                if (tree2Sprite.x < -400) {
                    tree2Sprite.x = (d.width / 4) - 4;
                    tree2Sprite.y = (d.height / 8) + 20;
                }
            }
        });
        if (!Tr2.isAlive()) {
            Tr2.start();
        }
        Thread Tr3 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree3Sprite.x -= 2;
                    tree3Sprite.y++;
                }
                if (tree3Sprite.x < -500) {
                    tree3Sprite.x = (d.width / 5) - 4;
                    tree3Sprite.y = (d.height / 17) + 20;
                }
            }
        });
        if (!Tr3.isAlive()) {
            Tr3.start();
        }
        Thread Tr4 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree4Sprite.x += 2;
                    tree4Sprite.y++;
                }
                if (tree4Sprite.x > 640) {
                    tree4Sprite.x = ((int) d.getWidth() / 2) + 40;
                    tree4Sprite.y = ((int) d.getHeight() / 3) - 100;
                }
            }
        });
        if (!Tr4.isAlive()) {
            Tr4.start();
        }
        Thread Tr5 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree5Sprite.x += 2;
                    tree5Sprite.y++;
                }
                if (tree5Sprite.x > 760) {
                    tree5Sprite.x = ((int) d.getWidth() / 2) + 40;
                    tree5Sprite.y = ((int) d.getHeight() / 3) - 80;
                }
            }
        });
        if (!Tr5.isAlive()) {
            Tr5.start();
        }
        Thread Tr6 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree6Sprite.x += 2;
                    tree6Sprite.y++;
                }
                if (tree6Sprite.x > 800) {
                    tree6Sprite.x = ((int) d.getWidth() / 2) + 40;
                    tree6Sprite.y = ((int) d.getHeight() / 3) - 80;
                }
            }
        });
        if (!Tr6.isAlive()) {
            Tr6.start();
        }
        Thread Tr7 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree7Sprite.x += 2;
                    tree7Sprite.y++;
                }
                if (tree7Sprite.x > 753) {
                    tree7Sprite.x = ((int) d.getWidth() / 2) + 42;
                    tree7Sprite.y = ((int) d.getHeight() / 3) - 82;
                }
            }
        });
        if (!Tr7.isAlive()) {
            Tr7.start();
        }
        Thread Tr8 = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if (!gamePaused) {
                if (System.currentTimeMillis() % 2 == 0) {
                    tree8Sprite.x -= 2;
                    tree8Sprite.y++;
                }
                if (tree8Sprite.x < -574) {
                    tree8Sprite.x = (d.width / 9);
                    tree8Sprite.y = (d.height / 7) + 32;
                }
            }
        });
        if (!Tr8.isAlive()) {
            Tr8.start();
        }

        // finally, drawing the graphical decorations:
        cloudSprite.drawImage(g, this);
        tree1Sprite.drawImage(g, this);
        tree2Sprite.drawImage(g, this);
        tree3Sprite.drawImage(g, this);
        tree4Sprite.drawImage(g, this);
        tree5Sprite.drawImage(g, this);
        tree6Sprite.drawImage(g, this);
        tree7Sprite.drawImage(g, this);
        tree8Sprite.drawImage(g, this);

        Set<String> ignored = Set.of("E", "S", "F");
        if (ignored.contains(currentHazardOrMoney1)) currentHazardOrMoney1_image = null;
        if (ignored.contains(currentHazardOrMoney2)) currentHazardOrMoney2_image = null;
        if (ignored.contains(currentHazardOrMoney3)) currentHazardOrMoney3_image = null;
        if (ignored.contains(currentHazardOrMoney4)) currentHazardOrMoney4_image = null;

        Map<String, String> hazardPaths = Map.of(
                "M", "res/money.png",
                "8", "res/enemy_snowman.png",
                "o", "res/enemy_snowball.png",
                "Q", "res/enemy_bomb.png",
                "B", "res/enemy_beaver_masi.png"
        );
        String path_of_hazard_1 = hazardPaths.getOrDefault(currentHazardOrMoney1, "");
        String path_of_hazard_2 = hazardPaths.getOrDefault(currentHazardOrMoney2, "");
        String path_of_hazard_3 = hazardPaths.getOrDefault(currentHazardOrMoney3, "");
        String path_of_hazard_4 = hazardPaths.getOrDefault(currentHazardOrMoney4, "");
        if (currentHazardOrMoney1.equals("1")) {
            path_of_hazard_2 = "";
            currentHazardOrMoney2_image = null;
            path_of_hazard_3 = "";
            currentHazardOrMoney3_image = null;
            path_of_hazard_4 = "";
            currentHazardOrMoney4_image = null;
            Map<Character, String> hazardMap = Map.of(
                    'A', "res/remember_A_asterisk.png",
                    'B', "res/remember_B_bell.png",
                    'C', "res/remember_C_clock.png",
                    'D', "res/remember_D_diamond.png",
                    'H', "res/remember_H_hash.png",
                    'S', "res/remember_S_star.png"
            );

            for (int i = 0; i < 3; i++) {
                char c = thingsToRemember.charAt(i);
                if (hazardMap.containsKey(c)) {
                    path_of_hazard_1 = hazardMap.get(c);
                }
            }
        }
        if (currentHazardOrMoney1.equals("2")) {
            path_of_hazard_2 = "";
            currentHazardOrMoney2_image = null;
            path_of_hazard_3 = "";
            currentHazardOrMoney3_image = null;
            path_of_hazard_4 = "";
            currentHazardOrMoney4_image = null;
            for (int i = 3; i < 6; i++) {
                Optional<RememberType> rememberType = RememberType.fromSymbol(thingsToRemember.charAt(i));
                if (rememberType.isPresent()) {
                    switch (rememberType.get()) {
                        case ASTERISK:
                            path_of_hazard_1 = "res/remember_A_asterisk.png";
                            break;
                        case BELL:
                            path_of_hazard_1 = "res/remember_B_bell.png";
                            break;
                        case CLOCK:
                            path_of_hazard_1 = "res/remember_C_clock.png";
                            break;
                        case DIAMOND:
                            path_of_hazard_1 = "res/remember_D_diamond.png";
                            break;
                        case HASH:
                            path_of_hazard_1 = "res/remember_H_hash.png";
                            break;
                        case STAR:
                            path_of_hazard_1 = "res/remember_S_star.png";
                            break;
                        default:
                            // Unknown character – you can handle it here if needed
                            break;
                    }
                }
            }

        }
        // file names should remain exactly original

        if (!"".equals(path_of_hazard_1)) {
            ImageIcon currentHazardOrMoney_1 = new ImageIcon(path_of_hazard_1);
            currentHazardOrMoney_1.setImage(currentHazardOrMoney_1.getImage()
                    .getScaledInstance(currentHazardOrMoney1w, currentHazardOrMoney1h, Image.SCALE_DEFAULT));
            currentHazardOrMoney1_image = currentHazardOrMoney_1.getImage();
        }
        if (!"".equals(path_of_hazard_2)) {
            ImageIcon currentHazardOrMoney_2 = new ImageIcon(path_of_hazard_2);
            currentHazardOrMoney_2.setImage(currentHazardOrMoney_2.getImage()
                    .getScaledInstance(currentHazardOrMoney2w, currentHazardOrMoney2h, Image.SCALE_DEFAULT));
            currentHazardOrMoney2_image = currentHazardOrMoney_2.getImage();
        }
        if (!"".equals(path_of_hazard_3)) {
            ImageIcon currentHazardOrMoney_3 = new ImageIcon(path_of_hazard_3);
            currentHazardOrMoney_3.setImage(currentHazardOrMoney_3.getImage()
                    .getScaledInstance(currentHazardOrMoney3w, currentHazardOrMoney3h, Image.SCALE_DEFAULT));
            currentHazardOrMoney3_image = currentHazardOrMoney_3.getImage();
        }
        if (!"".equals(path_of_hazard_4)) {
            ImageIcon currentHazardOrMoney_4 = new ImageIcon(path_of_hazard_4);
            currentHazardOrMoney_4.setImage(currentHazardOrMoney_4.getImage()
                    .getScaledInstance(currentHazardOrMoney4w, currentHazardOrMoney4h, Image.SCALE_DEFAULT));
            currentHazardOrMoney4_image = currentHazardOrMoney_4.getImage();
        }

        String onesToDraw_path = "res/numbers" + (ones) + ".png";
        String tensToDraw_path = "res/numbers" + (tens) + ".png";
        String hundredsToDraw_path = "res/numbers" + (hundreds) + ".png";
        String thousandsToDraw_path = "res/numbers" + (thousands) + ".png";
        String tenThousandsToDraw_path = "res/numbers" + (tenThousands) + ".png";
        String hundredThousandsToDraw_path = "res/numbers" + (hundredThousands) + ".png";

        int digitFromLeft1_x_position = ((int) d.getWidth() / 2);
        int digitFromLeft1_y_position = (int)((int) d.getHeight() / 1.27);

        digitFromLeft1.load(hundredThousandsToDraw_path, digitFromLeft1_x_position, digitFromLeft1_y_position);
        digitFromLeft2.load(tenThousandsToDraw_path, digitFromLeft1_x_position+45, digitFromLeft1_y_position);
        digitFromLeft3.load(thousandsToDraw_path, digitFromLeft1_x_position+90, digitFromLeft1_y_position);
        digitFromLeft4.load(hundredsToDraw_path, digitFromLeft1_x_position+135, digitFromLeft1_y_position);
        digitFromLeft5.load(tensToDraw_path, digitFromLeft1_x_position+180, digitFromLeft1_y_position);
        digitFromLeft6.load(onesToDraw_path, digitFromLeft1_x_position+225, digitFromLeft1_y_position);

        Thread HAZ1 = new HAZ1Thread(this);
        if (!HAZ1.isAlive()) {
            HAZ1.start();
        }
        if (hugoSkiing.currentStateAtTheLevel > -2) {
            g.drawImage(currentHazardOrMoney1_image, currentHazardOrMoney1_x_position, currentHazardOrMoney1_y_position, this);
        }

        Thread HAZ2 = new HAZ2Thread(this);
        if (!HAZ2.isAlive()) {
            HAZ2.start();
        }
        if (hugoSkiing.currentStateAtTheLevel > -2) {
            g.drawImage(currentHazardOrMoney2_image, currentHazardOrMoney2_x_position, currentHazardOrMoney2_y_position, this);
        }

        Thread HAZ3 = new HAZ3Thread(this);
        if (!HAZ3.isAlive()) {
            HAZ3.start();
        }
        if (hugoSkiing.currentStateAtTheLevel > -2) {
            g.drawImage(currentHazardOrMoney3_image, currentHazardOrMoney3_x_position, currentHazardOrMoney3_y_position, this);
        }

        Thread HAZ4 = new HAZ4Thread(this);
        if (!HAZ4.isAlive()) {
            HAZ4.start();
        }
        if (hugoSkiing.currentStateAtTheLevel > -2) {
            g.drawImage(currentHazardOrMoney4_image, currentHazardOrMoney4_x_position, currentHazardOrMoney4_y_position, this);
        }

        g.drawImage(scoreBarSprite.getImage(), scoreBarSprite.getX(), scoreBarSprite.getY(), this);

        if (hundredThousandsVisible)
            digitFromLeft1.drawImage(g, this);
        if (tenThousandsVisible)
            digitFromLeft2.drawImage(g, this);
        if (thousandsVisible)
            digitFromLeft3.drawImage(g, this);
        if (hundredsVisible)
            digitFromLeft4.drawImage(g, this);
        if (tensVisible)
            digitFromLeft5.drawImage(g, this);
        if (onesVisible)
            digitFromLeft6.drawImage(g, this);

        repaint();

        if (number_of_lives > 1.5) {
            hugolife1Sprite.drawImage(g, this);
        }
        if (number_of_lives > 2.5) {
            hugolife2Sprite.drawImage(g, this);
        }
        if (number_of_lives > 3.5) {
            hugolife3Sprite.drawImage(g, this);
        }

        if (currentGrid < 2) { // Hugo ski animation
            Thread GRIDS01 = new GridS01Thread(this);
            if (!GRIDS01.isAlive()) {
                GRIDS01.start();
            }
            if (currentGrid == 0) {
                g.drawImage(sprite_L2, x, y, this);
            } else {
                g.drawImage(sprite_L, x, y, this);
            }
        } else {
            Thread GRIDS23 = new GridS23Thread(this);
            if (!GRIDS23.isAlive()) {
                GRIDS23.start();
            }
            if (currentGrid == 3) {
                g.drawImage(sprite_R2, x, y, this);
            } else {
                g.drawImage(sprite_R, x, y, this);
            }
        }

        // Order matters,
        if (gamePaused) { // pause should be written last because it should always be on top of everything.
            if (pausedWithEnter) {
                g.drawImage(pauseSprite.getImage(), pauseSprite.getX(), pauseSprite.getY(), this);
            }
        }

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;
            constructFrames(gameState);
            repaint();
        }
    }

    private void paintShowingVideo(Graphics g) {
        super.paintComponent(g);

        if (useMP4) { // If you think that ".aiff + .gif" is not a good combination
            String pathMP4 = getResourceAudioVideo(video, ".mp4");

            File video_source = new File(pathMP4);
            try {
                Desktop.getDesktop().open(video_source); // opens Windows Media Player for instance
            } catch (IOException ex) {                   // not the best way to display mp4s
                Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            String pathGif = getResourceAudioVideo(video, "_s.gif");

            videoIMGicon = new ImageIcon(pathGif);
            super.paintComponent(g);
            videoimg = null; // .gif
            videoimg = videoIMGicon.getImage();
            int wi = (int) (d.getWidth());
            int he = (int) (d.getHeight() - 40);
            videoimg.setAccelerationPriority((float) 1.0); // from 0-> lowest to 1-> highest

            g.drawImage(videoimg, 0, 0, wi, he, null);
            for (int i = 0; i < 30000; i++) {
                repaint(); // Important repaint lines
            }
        }

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;

            constructFrames(gameState);
            repaint();
        }
    }

    private void paintTitleScreen(Graphics g) {
        super.paintComponent(g);
        if (hugoSkiing.currentStateAtTheLevel >= 71 && !hugoSkiing.gameOver) {
            g.drawImage(creditsScreen, 0, 0, this);
            repaint();
        } else {
            g.drawImage(titleScreen, 0, 0, this);
            repaint();
        }

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;
            constructFrames(gameState);
            repaint();

        }
    }

    private void paintPreTitle(Graphics g) {
        super.paintComponent(g);
        g.drawImage(theVeryFirst, 0, 0, this);
        repaint();

        if (nextState != gameState) {
            System.out.println("------ State change from " + gameState + " to " + nextState);
            gameState = nextState;
            constructFrames(gameState);
            repaint();
        }
    }

    public void gameReset() {
        currentGrid = 0;
        pulled_rope_1 = false;
        pulled_rope_2 = false;
        pulled_rope_3 = false;
        ones = 0;   // score digits (6)
        tens = 0;
        hundreds = 0;
        thousands = 0;
        tenThousands = 0;
        hundredThousands = 0;
        onesVisible = true;
        tensVisible = false;
        hundredsVisible = false;
        thousandsVisible = false;
        tenThousandsVisible = false;
        hundredThousandsVisible = false;
        number_of_lives = 4; // number of lives
        currentlyAllCorrect = true; // even though 0 guesses
        secondPhase = false;
        allCorrectInTheEnd = false;
    }

    public void setMaxScore() {
        setOnes(9);
        setTens(9);
        setHundreds(9);
        setThousands(9);
        setTenThousands(9);
        setHundredThousands(9);
    }

    private final String[] files = {"res/scylla_intro",
            "res/start_hoplaa",
            "res/scylla_button_press",
            "res/scylla0",
            "res/remember2forKey_intro",
            "res/remember2forKey_win",
            "res/remember2forKey_fail",
            "res/screentalk_finish_line",
            "res/screentalk_heraa_pahvi",
            "res/screentalk_viimeista_viedaan",
            "res/screentalk_game_over",
            "res/scylla1",
            "res/scylla2",
            "res/scylla3",
            "res/loselife_snowman",
            "res/loselife_snowball",
            "res/loselife_bomb",
            "res/loselife_beaver"};

    private String getResourceAudioVideo(VideoType videoIndex, String extension) {
        return files[videoIndex.getIndex()].concat(extension);
    }

    public void stopSound() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
        }
    }

    public void videoFlush() {
        if (videoimg != null) {  // videos should always start at the beginning
            videoimg.flush();
            videoimg = null;
        }
    }

    /**
     * Game reset call.
     */
    public void reset() {
        hugoSkiing.gameReset(GAMESPEED); // creates a new game
    }

    /**
     * Resets the positions of 4 ski track objects.
     */
    public void reset4positions() {
        if (!hugoSkiing.tic) return;

        setInitialPositions();

        // Override position for special states
        if ((hugoSkiing.currentStateAtTheLevel == 14 || hugoSkiing.currentStateAtTheLevel == 25) && !pausedWithEnter) {
            currentHazardOrMoney1_x_position = 20;
            currentHazardOrMoney1_y_position = 30;
        }

        setDimensionsToOne();

        hideIfBelowY(currentHazardOrMoney1_y_position, () -> {
            if (hugoSkiing.currentStateAtTheLevel != 14 && hugoSkiing.currentStateAtTheLevel != 25) {
                currentHazardOrMoney1_y_position += 1000;
                currentHazardOrMoney1_x_position += 1000;
            }
        });

        hideIfBelowY(currentHazardOrMoney2_y_position, () -> {
            currentHazardOrMoney2_y_position += 1000;
            currentHazardOrMoney2_x_position += 1000;
        });

        hideIfBelowY(currentHazardOrMoney3_y_position, () -> {
            currentHazardOrMoney3_y_position += 1000;
            currentHazardOrMoney3_x_position += 1000;
        });

        hideIfBelowY(currentHazardOrMoney4_y_position, () -> {
            currentHazardOrMoney4_y_position += 1000;
            currentHazardOrMoney4_x_position += 1000;
        });
    }

    private void setInitialPositions() {
        int baseX = d.width / 3;
        int baseY = d.height / 3;

        currentHazardOrMoney1_x_position = baseX + 35;
        currentHazardOrMoney1_y_position = baseY;

        int y31 = (int) (d.height / 3.1);
        currentHazardOrMoney2_x_position = baseX + 58;
        currentHazardOrMoney2_y_position = y31;

        currentHazardOrMoney3_x_position = baseX + 88;
        currentHazardOrMoney3_y_position = y31;

        currentHazardOrMoney4_x_position = baseX + 130;
        currentHazardOrMoney4_y_position = y31;
    }

    private void setDimensionsToOne() {
        currentHazardOrMoney1w = currentHazardOrMoney1h = 1;
        currentHazardOrMoney2w = currentHazardOrMoney2h = 1;
        currentHazardOrMoney3w = currentHazardOrMoney3h = 1;
        currentHazardOrMoney4w = currentHazardOrMoney4h = 1;
    }

    private void hideIfBelowY(int currentY, Runnable action) {
        if (currentY > y) {
            action.run();
        }
    }


    /**
     * Set lives, max is 4 in this version (1.1).
     *
     * @param newAmount
     */
    public void setLives(int newAmount) {
        if (newAmount < 5 && newAmount > -1) {
            number_of_lives = newAmount;
        }
    }

    /**
     * Set ones.
     *
     * @param newAmount
     */
    public void setOnes(int newAmount) {
        ones = newAmount;
    }

    /**
     * Set tens.
     *
     * @param newAmount
     */
    public void setTens(int newAmount) {
        tens = newAmount;
    }

    /**
     * Set hundreds.
     *
     * @param newAmount
     */
    public void setHundreds(int newAmount) {
        hundreds = newAmount;
    }

    /**
     * Set thousands.
     *
     * @param newAmount
     */
    public void setThousands(int newAmount) {
        thousands = newAmount;
    }

    /**
     * Set 10 000s.
     *
     * @param newAmount
     */
    public void setTenThousands(int newAmount) {
        tenThousands = newAmount;
    }

    /**
     * Set 100 000s.
     *
     * @param newAmount
     */
    public void setHundredThousands(int newAmount) {
        hundredThousands = newAmount;
    }

    /**
     * Set hazard 1.
     *
     * @param value
     */
    public void setcurrentHazardOrMoney1(String value) {
        currentHazardOrMoney1 = value;
    }

    /**
     * Set hazard 2.
     *
     * @param value
     */
    public void setcurrentHazardOrMoney2(String value) {
        currentHazardOrMoney2 = value;
    }

    /**
     * Set hazard 3.
     *
     * @param value
     */
    public void setcurrentHazardOrMoney3(String value) {
        currentHazardOrMoney3 = value;
    }

    /**
     * Set hazard 4.
     *
     * @param value
     */
    public void setcurrentHazardOrMoney4(String value) {
        currentHazardOrMoney4 = value;
    }

}