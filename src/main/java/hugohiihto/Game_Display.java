package hugohiihto;

// import java.awt.*; // Commented out AWT
// import java.awt.event.*; // Commented out AWT event
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
// import javax.media.Manager; // FMJ Removed
// import javax.media.NoPlayerException; // FMJ Removed
// import javax.media.Player; // library FMJ - http://fmj-sf.net/ // FMJ Removed
// import javax.sound.sampled.AudioSystem; // Commented out - Replaced by JavaFX
// import javax.sound.sampled.Clip; // Commented out - Replaced by JavaFX
// import javax.sound.sampled.FloatControl; // Commented out - Replaced by JavaFX
// import javax.sound.sampled.LineUnavailableException; // Commented out - Replaced by JavaFX
// import javax.sound.sampled.UnsupportedAudioFileException; // Commented out - Replaced by JavaFX
// import javax.swing.*; // Commented out Swing
import javafx.scene.image.Image; // Added JavaFX Image
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaException;

/**
 * Hugo Ski Game v1.1 by Tuomas Hyvönen, 10/2024 
 *
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
 *
 * - There can still be bugs and other flaws, I am not a perfect programmer. 
 *   You should be happy if the application even starts up in the first place. 
 *   If a video cut scene does not start, please move the mouse cursor on the game window. 
 *
 * The game window cannot be resized currently, press the Windows key then +Plus/-Minus to zoom in/out. 
 * Windows Magnifier/zooming instructions: 
 * https://www.androidpolice.com/how-to-zoom-in-out-on-windows-10-11/
 * Something like this should be possible on Linux, MAC etc. too but without Windows button... 
 *
 * Thanks for trying out this game! I do not own the Hugo franchise. 
 * Sharing Java codes and random files on the Internet should not be a crime - as far as I do not cause harm to people or their money. 
 * The purpose is to share the fun with people and keep the Hugo franchise alive. 
 *
 * Java must be installed before playing the game: 
 * https://www.java.com/en/download/manual.jsp
 *
 *
 * "Work is not man's punishment. It is his reward and his strength and his pleasure." 
 * - George Sand, French novelist 
 *
 * "Love is a serious mental disease."      (especially for Hugo franchise) 
 * - Plato, Greek philosopher
 *
 * "We can easily forgive a child who is afraid of the dark; the real tragedy of life is when men are afraid of the light."
 * - Plato
 *
 * https://www.brainyquote.com 
 *
 * ----
 *
 * Please support the original and official Hugo releases! They have been the inspiration for this game. 
 * Consider this game to be treated the same way as "Mega Man Unlimited". 
 * https://megamanfanon.fandom.com/wiki/Mega_Man_Unlimited 
 *
 * Or if you're mean, treat this the same way as Metroid AM2R. 
 * https://en.wikipedia.org/wiki/AM2R 
 *
 * Or Commodore64 Super Mario Bros. that Nintendo also did not treat well. 
 * https://archive.org/details/Super_Mario_Bros_C64_Zeropaige 
 *
 *
 * Thanks to this discussion for huge progress in game development when getting started:
 * https://stackoverflow.com/questions/12082660/background-image-for-simple-game
 *
 * "The beginning is the most important part of the work."
 * - also from Plato
 *
 * ----
 *
 * Some Hugo creators not involved in this project, thanks for each one of them! 
 * Consider this as "the real credits screen": 
 *
 * https://screentroll.fandom.com/wiki/Hugo_(PlayStation) 
 *  Producers: Ivan Sølvason, Lars Rikart Jensen, Troels Gram, Jens C. Ringdal
 *  Design: Niels Krogh Mortensen, Troels Gram, Lars Rikart Jensen
 *  Programmers: Peter Marino, Poul-Jesper Olsen, Ole Thomas Jensen, Mario Gomes, Claes Hougaard, 
 *    Michael Barklund, Erik Schack Andersen, John Dideriksen, Troels Gram, Jørgen Lortentsen
 *  Graphics: Chadi Freigeh, Claus Friese, Thomas Skellund, Mark Gregory, Jakob Steffensen, Peter Eide Paulsen, 
 *    Jørgen Trolle Ørberg, John Madsen, Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, 
 *    Anders Morgenthaler, Torben Bakager Larsen, Jesper Laugesen 
 *  Music and sound: David A. Filskov, Christian Steen Jensen.
 *
 * https://screentroll.fandom.com/wiki/Hugo_2_(PlayStation) 
 *  Producer: Ivan Sølvason, Lars Rikart Jensen. Piet N. Kargaard
 *  Design: Mario Gomes, Peter Eide Paulsen, Poul Engelbrech Madsen, John Dideriksen
 *  Programming: Mario Gomes, Jesper Olsen, Anders Emil Hansen, Ole T. Jensen
 *  Graphics: Claus Friese, Chadi Freigeh, Peter Eide Paulsen, Piet N. Kargaard, John Madsen, Jørgen Trolle Ørberg
 *  Music and sound: David A. Filskov, Christian Steen Jensen, Klaus Mulvad Nielsen, Asbjørn Andersen
 *  Other: Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, Anders Morgenthaler, Torben Bakager Larsen, 
 *    Jesper Laugesen, Tom Westerman, Thomas Skellund, Espen Toft Jacobsen, Laust Palbo Nielsen.
 *
 *  https://screentroll.fandom.com/wiki/Hugo_5
 *  Production manager: Ivan Sølvason
 *  Programmers: Jakob Frandsen, Bo Krohn, Kim Frederiksen, Lasse S. Tassing, Jens Nordahl
 *  TV programmers: Kim Frederiksen, Stig Jørgensen
 *  Graphics: Lars Krogh Mortensen, Laust Palbo Nielsen, Tom Westermann, Esben Toft Jacobsen, 
 *    Jakob Steffensen, Jesper Eskildsen, Thomas Skellund
 *  Music and sound effects: Mads Kristensen, David Filskov.
 *
 *  https://screentroll.fandom.com/wiki/Hugo_(1996_video_game)
 *  Producer: Ivan Sølvason
 *  PC programming: Jakob Frandsen, Lasse Tassing, Kim Frederisken, Troels Gram
 *  TV programming: Stig Jørgansen, Kim Frederisken, Bo Krohn, Morten Hansen, Esben Krag Hansen
 *  Graphics: Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, Anders Morgenthaler, 
 *    Laust Palbo Nielsen, Thomas Skellund, Torben Bakager, Martin De Thurah
 *  Music and sound effects: Mads Kriestensen, Nicolai Thilo, Thomas Engell, Jørgen Traun. 
 *
 * https://fi.wikipedia.org/wiki/DJ_Hugo
 * https://fi.wikipedia.org/wiki/Hugo_(televisio-ohjelma)
 * Music: Slotmachine featuring Gemini 7, Kata Laurikainen, Anssi Ahonen, Jaana Rinne 
 * Other: Harri Hyttinen, Eija Ahvo, Jussi-Pekka Koskiranta, Pekka Kossila, Ari Meriläinen, Taru Valkeapää, Marika Saukkonen. 
 *
 * Guyus the Raptor and other uploaders at YouTube etc. 
 * Commodore 64 music by Jens-Christian Huus
 *
 * ----
 *
 *
 * The game display class. The other class is HugoHiihto.java. 
 * The main method is here, check the end of this file. 
 *
 * Tested with Microsoft Windows 11
 * Java developed by Oracle / Sun Microsystems. 
 * Created with Apache NetBeans 23 
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide 
 * @author Tuomas Hyvönen 
 * @version 1.1.ENG
 */
public final class Game_Display { // Removed "extends JPanel"
    final static String VERSION = "1.1.ENG";
    final static int GAMESPEED = 1700;      // in milliseconds
    // does not update graphics!
    int game_state = 0;      // 0 = Pre title no music,  1 = title screen and credits screen after beating the game,
    // 2 = showing a video,     3 = actual ski game,
    // 4 = remember two items,  5 = game over or beat the game + show score.
    // use state 6 or higher when moving from a video to another video
    static int nextState = 0;
    // boolean useMP4 = false; // FMJ related, removed. GIF+AudioClip is the way.
    // The original Hugo graphics and sounds have been edited.
    static int video = 0;
    javafx.scene.image.Image videoIMGicon; // Explicitly JavaFX Image
    javafx.scene.image.Image videoimg = null; // .gif expected + .aiff for sound // Explicitly JavaFX Image
    // 0 = Scylla intro,          1 = Hugo's first words hoplaa nyt hommiin,
    // 2 = Scylla button,         3 = three ropes intro,
    // 4 = Hugo asks for two,     5 = two chosen correctly,
    // 6 = made a wrong choice,   7 = (knock) Hugo finished the skiing,
    // 8 = [knock] wake up pahvi, 9 = (knock) now the last troll going,
    // 10 = (knock) game over,    11 = rope #1,
    // 12 = rope #2,              13 = rope #3,
    // 14 = snowman,              15 = snowball,
    // 16 = bomb,                 17 = beaver.
    static boolean cheatBackflip180 = false;
    static boolean key1 = false;
    static boolean key2 = false;
    static boolean key3 = false;
    static boolean key4 = false;
    static boolean key5 = false;
    static boolean key6 = false;
    static boolean key7 = false;
    static boolean key8 = false;
    static boolean key9 = false;
    static boolean key10 = false;
    static boolean key11 = false;
    static boolean key12 = false;
    // static Player mediaPlayer = null; // FMJ Player for video - REMOVED
    static AudioClip currentCutsceneAudio = null; // For managing cutscene sounds
    
    // JavaFX MediaPlayers for music/longer sounds - replacing former Clip objects clip0-clip4
    static MediaPlayer audioPlayerMenu = null;
    static MediaPlayer audioPlayerPopcorn = null;
    static MediaPlayer audioPlayerFinnishHugo = null;
    static MediaPlayer audioPlayerGameMusic2 = null; // Potentially unused, was Clip clip2
    static MediaPlayer audioPlayerCredits = null;
    static MediaPlayer audioPlayerSkateboard = null;
    
    // JavaFX AudioClips for short sound effects - replacing former Clip objects
    static MediaPlayer mediaPlayerSkiingLoop = null; // Renamed from soundSkiing, type changed to MediaPlayer
    static AudioClip soundMoney = null;
    static AudioClip soundScore = null;
    static AudioClip soundChangeGrid = null;
    static AudioClip soundButton4 = null;
    static AudioClip soundButton6 = null;
    static AudioClip soundCorrect = null;
    //MediaPlayer for skiing sound, as it needs looping
    static MediaPlayer mediaPlayerSkiingLoop = null;


    // Define paths for easy access and to avoid redundant string creation
    static final String PATH_MONEY_WAV = "/res/money.wav";
    static final String PATH_POINTS_SCORE_WAV = "/res/points_score.wav";
    static final String PATH_MUSIC_PS1HUGO2MENU_WAV = "/res/music-ps1hugo2menu.wav"; // For audioPlayerMenu
    static final String PATH_MUSIC_DJHUGOPOPCORN_WAV = "/res/music-djhugopopcorn.wav"; // For audioPlayerPopcorn
    static final String PATH_MUSIC_CREDITS_WAV = "/res/music_credits.wav"; // For audioPlayerCredits
    static final String PATH_SKI_TRACK_CHANGE_WAV = "/res/ski_track_change.wav"; // For soundChangeGrid
    static final String PATH_BUTTON4SOUND_WAV = "/res/button4sound.wav"; // For soundButton4
    static final String PATH_BUTTON6SOUND_WAV = "/res/button6sound.wav"; // For soundButton6
    static final String PATH_CORRECT_SELECTION_WAV = "/res/correct_selection.wav"; // For soundCorrect
    static final String PATH_HIHTOAANI_WAV = "/res/hiihtoaani.wav"; // For soundSkiing
    static final String PATH_MUSIC_FROM_CLASSIC_SKATEBOARD_WAV = "/res/music_from_classic_skateboard.wav"; // For audioPlayerSkateboard
    static final String PATH_MUSIC_FINNISHHUGO_WAV = "/res/music_FinnishHugo.wav"; // For audioPlayerFinnishHugo

    // Paths for cutscene .aiff sounds
    static final String PATH_SCYLLA_INTRO_AIFF = "/res/scylla_intro.aiff";
    static final String PATH_START_HOPLAA_AIFF = "/res/start_hoplaa.aiff";
    static final String PATH_SCYLLA_BUTTON_PRESS_AIFF = "/res/scylla_button_press.aiff";
    static final String PATH_SCYLLA0_AIFF = "/res/scylla0.aiff";
    static final String PATH_REMEMBER2FORKEY_INTRO_AIFF = "/res/remember2forKey_intro.aiff";
    static final String PATH_REMEMBER2FORKEY_WIN_AIFF = "/res/remember2forKey_win.aiff";
    static final String PATH_REMEMBER2FORKEY_FAIL_AIFF = "/res/remember2forKey_fail.aiff";
    static final String PATH_SCREENTALK_FINISH_LINE_AIFF = "/res/screentalk_finish_line.aiff";
    static final String PATH_SCREENTALK_HERAA_PAHVI_AIFF = "/res/screentalk_heraa_pahvi.aiff";
    static final String PATH_SCREENTALK_VIIMEISTA_VIEDAAN_AIFF = "/res/screentalk_viimeista_viedaan.aiff";
    static final String PATH_SCREENTALK_GAME_OVER_AIFF = "/res/screentalk_game_over.aiff";
    static final String PATH_SCYLLA1_AIFF = "/res/scylla1.aiff";
    static final String PATH_SCYLLA2_AIFF = "/res/scylla2.aiff";
    static final String PATH_SCYLLA3_AIFF = "/res/scylla3.aiff";
    static final String PATH_LOSELIFE_SNOWMAN_AIFF = "/res/loselife_snowman.aiff";
    static final String PATH_LOSELIFE_SNOWBALL_AIFF = "/res/loselife_snowball.aiff";
    static final String PATH_LOSELIFE_BOMB_AIFF = "/res/loselife_bomb.aiff";
    static final String PATH_LOSELIFE_BEAVER_AIFF = "/res/loselife_beaver.aiff";

    // AudioClip fields for cutscenes
    static AudioClip soundScyllaIntro;
    static AudioClip soundStartHoplaa;
    static AudioClip soundScyllaButtonPress;
    static AudioClip soundScylla0; // Rope intro
    static AudioClip soundRemember2forKeyIntro;
    static AudioClip soundRemember2forKeyWin;
    static AudioClip soundRemember2forKeyFail;
    static AudioClip soundScreenTalkFinishLine;
    static AudioClip soundScreenTalkHeraaPahvi;
    static AudioClip soundScreenTalkViimeistaViedaan;
    static AudioClip soundScreenTalkGameOver;
    static AudioClip soundScylla1; // Rope 1 chosen
    static AudioClip soundScylla2; // Rope 2 chosen
    static AudioClip soundScylla3; // Rope 3 chosen
    static AudioClip soundLoseLifeSnowman;
    static AudioClip soundLoseLifeSnowball;
    static AudioClip soundLoseLifeBomb;
    static AudioClip soundLoseLifeBeaver;


    static {
        try {
            // AudioClips for general sound effects
            soundMoney = new AudioClip(Game_Display.class.getResource(PATH_MONEY_WAV).toExternalForm());
            soundCorrect = new AudioClip(Game_Display.class.getResource(PATH_CORRECT_SELECTION_WAV).toExternalForm());
            soundChangeGrid = new AudioClip(Game_Display.class.getResource(PATH_SKI_TRACK_CHANGE_WAV).toExternalForm());
            soundButton4 = new AudioClip(Game_Display.class.getResource(PATH_BUTTON4SOUND_WAV).toExternalForm());
            soundButton6 = new AudioClip(Game_Display.class.getResource(PATH_BUTTON6SOUND_WAV).toExternalForm());
            soundScore = new AudioClip(Game_Display.class.getResource(PATH_POINTS_SCORE_WAV).toExternalForm());

            // AudioClips for cutscenes
            soundScyllaIntro = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA_INTRO_AIFF).toExternalForm());
            soundStartHoplaa = new AudioClip(Game_Display.class.getResource(PATH_START_HOPLAA_AIFF).toExternalForm());
            soundScyllaButtonPress = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA_BUTTON_PRESS_AIFF).toExternalForm());
            soundScylla0 = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA0_AIFF).toExternalForm());
            soundRemember2forKeyIntro = new AudioClip(Game_Display.class.getResource(PATH_REMEMBER2FORKEY_INTRO_AIFF).toExternalForm());
            soundRemember2forKeyWin = new AudioClip(Game_Display.class.getResource(PATH_REMEMBER2FORKEY_WIN_AIFF).toExternalForm());
            soundRemember2forKeyFail = new AudioClip(Game_Display.class.getResource(PATH_REMEMBER2FORKEY_FAIL_AIFF).toExternalForm());
            soundScreenTalkFinishLine = new AudioClip(Game_Display.class.getResource(PATH_SCREENTALK_FINISH_LINE_AIFF).toExternalForm());
            soundScreenTalkHeraaPahvi = new AudioClip(Game_Display.class.getResource(PATH_SCREENTALK_HERAA_PAHVI_AIFF).toExternalForm());
            soundScreenTalkViimeistaViedaan = new AudioClip(Game_Display.class.getResource(PATH_SCREENTALK_VIIMEISTA_VIEDAAN_AIFF).toExternalForm());
            soundScreenTalkGameOver = new AudioClip(Game_Display.class.getResource(PATH_SCREENTALK_GAME_OVER_AIFF).toExternalForm());
            soundScylla1 = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA1_AIFF).toExternalForm());
            soundScylla2 = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA2_AIFF).toExternalForm());
            soundScylla3 = new AudioClip(Game_Display.class.getResource(PATH_SCYLLA3_AIFF).toExternalForm());
            soundLoseLifeSnowman = new AudioClip(Game_Display.class.getResource(PATH_LOSELIFE_SNOWMAN_AIFF).toExternalForm());
            soundLoseLifeSnowball = new AudioClip(Game_Display.class.getResource(PATH_LOSELIFE_SNOWBALL_AIFF).toExternalForm());
            soundLoseLifeBomb = new AudioClip(Game_Display.class.getResource(PATH_LOSELIFE_BOMB_AIFF).toExternalForm());
            soundLoseLifeBeaver = new AudioClip(Game_Display.class.getResource(PATH_LOSELIFE_BEAVER_AIFF).toExternalForm());

            // MediaPlayers for music
            Media mediaMenu = new Media(Game_Display.class.getResource(PATH_MUSIC_PS1HUGO2MENU_WAV).toExternalForm());
            mediaPlayerMenu = new MediaPlayer(mediaMenu);
            mediaPlayerMenu.setVolume(1.0); 
            mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);

            Media mediaPopcorn = new Media(Game_Display.class.getResource(PATH_MUSIC_DJHUGOPOPCORN_WAV).toExternalForm());
            mediaPlayerPopcorn = new MediaPlayer(mediaPopcorn);
            mediaPlayerPopcorn.setVolume(0.17); 
            mediaPlayerPopcorn.setCycleCount(MediaPlayer.INDEFINITE);

            Media mediaCredits = new Media(Game_Display.class.getResource(PATH_MUSIC_CREDITS_WAV).toExternalForm());
            mediaPlayerCredits = new MediaPlayer(mediaCredits);
            mediaPlayerCredits.setVolume(1.0); 
            mediaPlayerCredits.setCycleCount(MediaPlayer.INDEFINITE);

            Media mediaSkateboard = new Media(Game_Display.class.getResource(PATH_MUSIC_FROM_CLASSIC_SKATEBOARD_WAV).toExternalForm());
            mediaPlayerSkateboard = new MediaPlayer(mediaSkateboard);
            mediaPlayerSkateboard.setVolume(0.63); 
            mediaPlayerSkateboard.setCycleCount(MediaPlayer.INDEFINITE);

            Media mediaFinnishHugo = new Media(Game_Display.class.getResource(PATH_MUSIC_FINNISHHUGO_WAV).toExternalForm());
            mediaPlayerFinnishHugo = new MediaPlayer(mediaFinnishHugo);
            mediaPlayerFinnishHugo.setVolume(1.0); 
            mediaPlayerFinnishHugo.setCycleCount(MediaPlayer.INDEFINITE);
            
            Media mediaSkiing = new Media(Game_Display.class.getResource(PATH_HIHTOAANI_WAV).toExternalForm());
            mediaPlayerSkiingLoop = new MediaPlayer(mediaSkiing);
            mediaPlayerSkiingLoop.setVolume(0.28); 
            mediaPlayerSkiingLoop.setCycleCount(MediaPlayer.INDEFINITE);
            
            audioPlayerGameMusic2 = null; // Explicitly set to null as it's not used / covered by others

        } catch (Exception e) {
            Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, "Error initializing AudioClips or MediaPlayers in static block", e);
        }
    }

    //  Hugo 2 PlayStation 1 title/menu
    //  Popcorn Slotmachine featuring Gemini 7 (1993 Finnish DJ Hugo charity CD tr 8)
    //  Hugo classic skateboard music Hugo 1 PlayStation 1
    //  Ending song Commodore64 Hugo rap
    //  Finnish Hugo TV show theme song (1993 Finnish DJ Hugo charity CD tr 1)
    // As the programmer I own nothing. The original creators listed above do not lose money when these tracks are included in this game.
    // Do not upload valuable video game and TV show musics to YouTube if they have strict copyrights!

    /*   * E- empty (even though E might not be needed to be read, it is meaningful to show positions)
     * M- money
     * 8- snowman
     * o- snowball (small o)
     * Q- bomb
     * B- the beaver
     * 1- thing to remember #1 (6 possible)
     * 2- thing to remember #2 (6 possible (actually 5 because never same again allowed))
     * S- Scylla button press with short horror music
     * F- goal, just end the skiing session
     *
     * A-  black asterisk* as +correct
     * a-  black asterisk* as incorrect-
     * B-  yellow bell as +correct
     * b-  yellow bell as incorrect-
     * C-  red clock as +correct
     * c-  red clock as incorrect-
     * D-  red diamond as +correct
     * d-  red diamond as incorrect-
     * H-  black hash(tag#) as +correct
     * h-  black hash(tag#) as incorrect-
     * S-  yellow star as +correct
     * s-  yellow star as incorrect-
     */

    // ezgif.com  &  redketchup.io/gif-resizer  were useful services for video gif polishing
    static boolean pulled_rope_1 = false; // good ending        1
    static boolean pulled_rope_2 = false; // bad ending         2
    static boolean pulled_rope_3 = false; // the best ending    3
    javafx.scene.image.Image r1; 
    javafx.scene.image.Image r2; 
    javafx.scene.image.Image r3; 

    static boolean gamePaused = false;
    static boolean pausedWithEnter = false; // 2 types of pausing: interruption before a video and pause on purpose by the player
    static String thingsToRemember = "dsHAcb";   // will be random later , end 0s and 1s are just extra if present
    static boolean currentlyAllCorrect = true;
    static boolean secondPhase = false; // these are in guessing 123 123 for the skull cave key
    static boolean allCorrectInTheEnd = false;      // (Scylla has weird locks and why does she even give the 2 scroll key clues to Hugo?)

    // static JFrame f = new JFrame(); // Already commented
    // static Dimension d = new Dimension(630, 500); // Already commented
    int maxW = 630 - 220;

    // Method to update currentHazardOrMoneyX_image fields based on current hazard strings
    // Called from HugoHiihto.GameLoop's "TAC" phase.
    public void updateHazardImages() {
        currentHazardOrMoney1_image = getHazardImageForString(currentHazardOrMoney1, 1);
        currentHazardOrMoney2_image = getHazardImageForString(currentHazardOrMoney2, 2);
        currentHazardOrMoney3_image = getHazardImageForString(currentHazardOrMoney3, 3);
        currentHazardOrMoney4_image = getHazardImageForString(currentHazardOrMoney4, 4);
    }

    // Helper to get a specific image based on the hazard character and update its dimensions
    private Image getHazardImageForString(String hazardChar, int hazardIndex) {
        String imagePath = "";
        int w = 60, h = 60; // Default dimensions

        switch (hazardChar) {
            case "M": imagePath = "/res/money.png"; w = 40; h = 40; break;
            case "8": imagePath = "/res/enemy_snowman.png"; break;
            case "o": imagePath = "/res/enemy_snowball.png"; w = 30; h = 30; break;
            case "Q": imagePath = "/res/enemy_bomb.png"; w = 50; h = 50; break;
            case "B": imagePath = "/res/enemy_beaver_masi.png"; break;
            case "1": // Thing to remember 1
                // The actual image path will be determined by the specific symbol to remember
                // which is derived from `thingsToRemember`.
                imagePath = getRememberSymbolImagePath(thingsToRemember.charAt(0)); // Example: get 1st symbol
                w = 120; h = 120; // Standard size for these items
                break;
            case "2": // Thing to remember 2
                imagePath = getRememberSymbolImagePath(thingsToRemember.charAt(3)); // Example: get 4th symbol
                w = 120; h = 120;
                break;
            case "E": // Empty
            case "S": // Scylla button (no specific image, it's a game state trigger)
            case "F": // Finish line (no specific image, it's a game state trigger)
            default:
                imagePath = ""; // No image or a placeholder transparent image could be used
                break;
        }
        
        // Store dimensions for HugoHiihtoFX to use for sizing ImageViews
        switch(hazardIndex) {
            case 1: currentHazardOrMoney1w = w; currentHazardOrMoney1h = h; break;
            case 2: currentHazardOrMoney2w = w; currentHazardOrMoney2h = h; break;
            case 3: currentHazardOrMoney3w = w; currentHazardOrMoney3h = h; break;
            case 4: currentHazardOrMoney4w = w; currentHazardOrMoney4h = h; break;
        }

        if (!imagePath.isEmpty()) {
            try {
                // Using imageCache from HugoHiihtoFX is not directly possible here.
                // Game_Display should manage its own images.
                return new Image(getClass().getResourceAsStream(imagePath));
            } catch (Exception e) {
                Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "Failed to load hazard image: " + imagePath, e);
                return null; // Return null if loading fails
            }
        }
        return null; // No image for this hazardChar or error
    }

    // Helper to get image path for "remember" symbols
    private String getRememberSymbolImagePath(char symbolChar) {
        switch (symbolChar) {
            case 'A': case 'a': return "/res/remember_A_asterisk.png";
            case 'B': case 'b': return "/res/remember_B_bell.png";
            case 'C': case 'c': return "/res/remember_C_clock.png";
            case 'D': case 'd': return "/res/remember_D_diamond.png";
            case 'H': case 'h': return "/res/remember_H_hash.png";
            case 'S': case 's': return "/res/remember_S_star.png";
            default: return ""; // Should not happen if thingsToRemember is valid
        }
    }
    
    // Helper for HugoHiihtoFX to get score digit image paths
    public String getScoreDigitPath(int digit) {
        if (digit < 0 || digit > 9) digit = 0; // Default to 0 for invalid digits
        return "/res/numbers" + digit + ".png";
    }


    private void stopAllMusicPlayers() {
        if (mediaPlayerMenu != null && mediaPlayerMenu.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerMenu.stop();
        if (mediaPlayerPopcorn != null && mediaPlayerPopcorn.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerPopcorn.stop();
        if (mediaPlayerCredits != null && mediaPlayerCredits.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerCredits.stop();
        if (mediaPlayerSkateboard != null && mediaPlayerSkateboard.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerSkateboard.stop();
        if (mediaPlayerFinnishHugo != null && mediaPlayerFinnishHugo.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerFinnishHugo.stop();
        if (mediaPlayerSkiingLoop != null && mediaPlayerSkiingLoop.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerSkiingLoop.stop();
        // audioPlayerGameMusic2 is null and not played, so no need to check/stop it.
    }
    
    int w_width = 630/7; 

    int w_width = 630/7; 
    int w_height = 500/3; 
    int e_width = 630/7; 
    int e_height = 500/3; 

    static int x, y;
    static int currentGrid = 0; // or line, should be 0, 1, 2 or 3, nothing else
    // Hugo will always go forward and <- & -> change the line on the current phase

    javafx.scene.image.Image theVeryFirst; 
    javafx.scene.image.Image titleScreen;  // instructions for how to play 
    javafx.scene.image.Image scoreBGR; 
    javafx.scene.image.Image creditsScreen; 

    javafx.scene.image.Image sprite_R; // Hugo showing up on 4 lines/grids/tracks 
    javafx.scene.image.Image sprite_L; 
    javafx.scene.image.Image sprite_R2; 
    javafx.scene.image.Image sprite_L2; 
    javafx.scene.image.Image bg; 

    int cave_x, cave_y;
    javafx.scene.image.Image bgCave; 

    javafx.scene.image.Image cloud; 
    int cloud_x_position;
    int cloud_y_position;
    static boolean leftWind = false;

    javafx.scene.image.Image possibleTree1; // may be changed to something else than trees - if wanted to edit so 
    int possibleTree1_x_position;
    int possibleTree1_y_position;
    javafx.scene.image.Image possibleTree2; 
    int possibleTree2_x_position;
    int possibleTree2_y_position;
    javafx.scene.image.Image possibleTree3; 
    int possibleTree3_x_position;
    int possibleTree3_y_position;
    javafx.scene.image.Image possibleTree4; 
    int possibleTree4_x_position;
    int possibleTree4_y_position;
    javafx.scene.image.Image possibleTree5; 
    int possibleTree5_x_position;
    int possibleTree5_y_position;
    javafx.scene.image.Image possibleTree6; 
    int possibleTree6_x_position;
    int possibleTree6_y_position;
    javafx.scene.image.Image possibleTree7; 
    int possibleTree7_x_position;
    int possibleTree7_y_position;
    javafx.scene.image.Image possibleTree8; 
    int possibleTree8_x_position;
    int possibleTree8_y_position;
    // int possibleTree1iconw; // Commenting out, direct scaling not used with JavaFX Image for now
    // int possibleTree1iconh; // Commenting out
    // int possibleTree2iconw; // Commenting out
    // int possibleTree2iconh; // Commenting out
    // int possibleTree3iconw; // Commenting out
    // int possibleTree3iconh; // Commenting out
    // int possibleTree4iconw; // Commenting out
    // int possibleTree4iconh; // Commenting out
    // int possibleTree5iconw; // Commenting out
    // int possibleTree5iconh; // Commenting out
    // int possibleTree6iconw; // Commenting out
    // int possibleTree6iconh; // Commenting out
    // int possibleTree7iconw; // Commenting out
    // int possibleTree7iconh; // Commenting out
    // int possibleTree8iconw; // Commenting out
    // int possibleTree8iconh; // Commenting out
    javafx.scene.image.Image possibleTree1icon; 
    javafx.scene.image.Image possibleTree2icon; 
    javafx.scene.image.Image possibleTree3icon; 
    javafx.scene.image.Image possibleTree4icon; 
    javafx.scene.image.Image possibleTree5icon; 
    javafx.scene.image.Image possibleTree6icon; 
    javafx.scene.image.Image possibleTree7icon; 
    javafx.scene.image.Image possibleTree8icon; 

    javafx.scene.image.Image scorebar; // blue score bar 
    int scorebar_x_position;
    int scorebar_y_position;

    javafx.scene.image.Image hugolife1; 
    int hugolife1_x_position;
    int hugolife1_y_position;
    javafx.scene.image.Image hugolife2; 
    int hugolife2_x_position;
    int hugolife2_y_position;
    javafx.scene.image.Image hugolife3; 
    int hugolife3_x_position;
    int hugolife3_y_position;
    javafx.scene.image.Image digitFromLeft1image; 
    int digitFromLeft1_x_position;
    int digitFromLeft1_y_position;
    javafx.scene.image.Image digitFromLeft2image; 
    int digitFromLeft2_x_position;
    int digitFromLeft2_y_position;
    javafx.scene.image.Image digitFromLeft3image; 
    int digitFromLeft3_x_position;
    int digitFromLeft3_y_position;
    javafx.scene.image.Image digitFromLeft4image; 
    int digitFromLeft4_x_position;
    int digitFromLeft4_y_position;
    javafx.scene.image.Image digitFromLeft5image; 
    int digitFromLeft5_x_position;
    int digitFromLeft5_y_position;
    javafx.scene.image.Image digitFromLeft6image; 
    int digitFromLeft6_x_position;
    int digitFromLeft6_y_position;
    javafx.scene.image.Image pause; 
    int pause_x_position;
    int pause_y_position;

    static boolean vanish4Faster = false;
    javafx.scene.image.Image currentHazardOrMoney1_image; 
    static int currentHazardOrMoney1_x_position;
    static int currentHazardOrMoney1_y_position;
    javafx.scene.image.Image currentHazardOrMoney2_image; 
    static int currentHazardOrMoney2_x_position;
    static int currentHazardOrMoney2_y_position;
    javafx.scene.image.Image currentHazardOrMoney3_image; 
    static int currentHazardOrMoney3_x_position;
    static int currentHazardOrMoney3_y_position;
    javafx.scene.image.Image currentHazardOrMoney4_image; 
    static int currentHazardOrMoney4_x_position;
    static int currentHazardOrMoney4_y_position;
    static String currentHazardOrMoney1 = "E";
    static String currentHazardOrMoney2 = "E";
    static String currentHazardOrMoney3 = "E";
    static String currentHazardOrMoney4 = "E";
    static int currentHazardOrMoney1w; // Potentially for scaling, keep for now
    static int currentHazardOrMoney1h; // Potentially for scaling, keep for now
    static int currentHazardOrMoney2w; // Potentially for scaling, keep for now
    static int currentHazardOrMoney2h; // Potentially for scaling, keep for now
    static int currentHazardOrMoney3w; // Potentially for scaling, keep for now
    static int currentHazardOrMoney3h; // Potentially for scaling, keep for now
    static int currentHazardOrMoney4w = 60; 
    static int currentHazardOrMoney4h = 60;

    int position1 = 10;
    int position2 = 130;
    int position3 = 250;
    int heightLevel1 = 5;
    int heightLevel2 = 150;
    javafx.scene.image.Image asterisk; 
    int asterisk_x_position;
    int asterisk_y_position;
    javafx.scene.image.Image bell; 
    int bell_x_position;
    int bell_y_position;
    javafx.scene.image.Image clock; 
    int clock_x_position;
    int clock_y_position;
    javafx.scene.image.Image diamond; 
    int diamond_x_position;
    int diamond_y_position;
    javafx.scene.image.Image hashtag; 
    int hashtag_x_position;
    int hashtag_y_position;
    javafx.scene.image.Image star; 
    int star_x_position;
    int star_y_position;

    javafx.scene.image.Image u1b; 
    int u1b_x_position;
    int u1b_y_position;
    javafx.scene.image.Image u1w; 
    javafx.scene.image.Image u2b; 
    int u2b_x_position;
    int u2b_y_position;
    javafx.scene.image.Image u2w; 
    javafx.scene.image.Image u3b; 
    int u3b_x_position;
    int u3b_y_position;
    javafx.scene.image.Image u3w; 
    javafx.scene.image.Image d1b; 
    int d1b_x_position;
    int d1b_y_position;
    javafx.scene.image.Image d1w; 
    javafx.scene.image.Image d2b; 
    int d2b_x_position;
    int d2b_y_position;
    javafx.scene.image.Image d2w; 
    javafx.scene.image.Image d3b; 
    int d3b_x_position;
    int d3b_y_position;
    javafx.scene.image.Image d3w; 

    // for score digit values:
    static int ones = 0;
    static int tens = 0;
    static int hundreds = 0;
    static int thousands = 0;
    static int tenThousands = 0;
    static int hundredThousands = 0;
    static boolean onesVisible = true;
    static boolean tensVisible = false;
    static boolean hundredsVisible = false;
    static boolean thousandsVisible = false;
    static boolean tenThousandsVisible = false;
    static boolean hundredThousandsVisible = false;

    static int number_of_lives = 4;
    // AudioClip fields already declared above
    // File object declarations for sounds are removed, paths are now constants.

    /**
     * Game reset call.
     */
    public void reset() {
        HugoHiihto.hugoHiihto = null;
        HugoHiihto.gameReset(GAMESPEED); // creates a new game
    }

    /**
     * Resets the positions of 4 ski track objects.
     */
    public static void reset4positions() {
        if(HugoHiihto.tic) {
            Game_Display.currentHazardOrMoney1_x_position = (630/3)+35; // Replaced d.width
            Game_Display.currentHazardOrMoney1_y_position = (int)(500/3); // Replaced d.height
            if(HugoHiihto.currentStateAtTheLevel == 14 || HugoHiihto.currentStateAtTheLevel == 25) {
                if(!pausedWithEnter) {
                    Game_Display.currentHazardOrMoney1_x_position = 20;
                    Game_Display.currentHazardOrMoney1_y_position = 30;
                }
            }
            Game_Display.currentHazardOrMoney2_x_position = (630/3)+58; // Replaced d.width
            Game_Display.currentHazardOrMoney2_y_position = (int)(500/3.1); // Replaced d.height
            Game_Display.currentHazardOrMoney3_x_position = (630/3)+88; // Replaced d.width
            Game_Display.currentHazardOrMoney3_y_position = (int)(500/3.1); // Replaced d.height
            Game_Display.currentHazardOrMoney4_x_position = (630/3)+130; // Replaced d.width
            Game_Display.currentHazardOrMoney4_y_position = (int)(500/3.1); // Replaced d.height
            Game_Display.currentHazardOrMoney1w = 1;
            Game_Display.currentHazardOrMoney1h = 1;
            Game_Display.currentHazardOrMoney2w = 1;
            Game_Display.currentHazardOrMoney2h = 1;
            Game_Display.currentHazardOrMoney3w = 1;
            Game_Display.currentHazardOrMoney3h = 1;
            Game_Display.currentHazardOrMoney4w = 1;
            Game_Display.currentHazardOrMoney4h = 1;
            if(currentHazardOrMoney1_y_position > y && (HugoHiihto.currentStateAtTheLevel != 14
                    && HugoHiihto.currentStateAtTheLevel != 25)) {
                currentHazardOrMoney1_y_position+=1000;
                currentHazardOrMoney1_x_position+=1000;
            }
            if(currentHazardOrMoney2_y_position > y) {
                currentHazardOrMoney2_y_position+=1000;
                currentHazardOrMoney2_x_position+=1000;
            }
            if(currentHazardOrMoney3_y_position > y) {
                currentHazardOrMoney3_y_position+=1000;
                currentHazardOrMoney3_x_position+=1000;
            }
            if(currentHazardOrMoney4_y_position > y) {
                currentHazardOrMoney4_y_position+=1000;
                currentHazardOrMoney4_x_position+=1000;
            }
        }
    }

    /**
     * Set lives, max is 4 in this version (1.1).
     * @param new_amount
     */
    public static void setLives(int new_amount) {
        if(new_amount < 5 && new_amount > -1) {
            number_of_lives = new_amount;
        }
    }
    /**
     * Set ones.
     * @param new_amount
     */
    public static void setOnes(int new_amount) {
        ones = new_amount;
    }
    /**
     * Set tens.
     * @param new_amount
     */
    public static void setTens(int new_amount) {
        tens = new_amount;
    }
    /**
     * Set hundreds.
     * @param new_amount
     */
    public static void setHundreds(int new_amount) {
        hundreds = new_amount;
    }
    /**
     * Set thousands.
     * @param new_amount
     */
    public static void setThousands(int new_amount) {
        thousands = new_amount;
    }
    /**
     * Set 10 000s.
     * @param new_amount
     */
    public static void setTenThousands(int new_amount) {
        tenThousands = new_amount;
    }
    /**
     * Set 100 000s.
     * @param new_amount
     */
    public static void setHundredThousands(int new_amount) {
        hundredThousands = new_amount;
    }
    /**
     * Set hazard 1.
     * @param value
     */
    public static void setcurrentHazardOrMoney1(String value) {
        currentHazardOrMoney1 = value;
    }
    /**
     * Set hazard 2.
     * @param value
     */
    public static void setcurrentHazardOrMoney2(String value) {
        currentHazardOrMoney2 = value;
    }
    /**
     * Set hazard 3.
     * @param value
     */
    public static void setcurrentHazardOrMoney3(String value) {
        currentHazardOrMoney3 = value;
    }
    /**
     * Set hazard 4.
     * @param value
     */
    public static void setcurrentHazardOrMoney4(String value) {
        currentHazardOrMoney4 = value;
    }

    /**
     * Key listeners when pressing buttons. Please call only once!
     * Else, input bugs will occur with multiple presses.
     */
    public class AL extends KeyAdapter { // Restored class definition
    
        /**
         * Key pressed event when player gives input.
         * @param e
         */
        @Override // Restored annotation
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
    
            if(keyCode == KeyEvent.VK_ESCAPE) {
    // 
    //             System.gc();    // run garbage collector
    //             System.exit(0); // and exit if ESC pressed
    //         }
    // 
    //         if(keyCode == KeyEvent.VK_ENTER) {
    // 
    //             if(video != 3) {
    //                 if(videoimg != null) {  // so videos will always start at the beginning
    //                     // videoimg.flush(); // Commented out flush
    //                     videoimg = null;
    //                 }
    //             }
    // 
    //             // Mutings for all game music MediaPlayers
                 stopAllMusicPlayers(); 
    //             // mediaPlayerSkiingLoop is included in stopAllMusicPlayers by the helper method
    //             
    //             // if(mediaPlayer != null && video != 2 && video != 3) { // FMJ Player for video // FMJ REMOVED
    //             //    mediaPlayer.stop();
    //             // }
    //             if (currentCutsceneAudio != null && video != 3) { // video 3 is rope selection screen, sound handled differently
    //                 currentCutsceneAudio.stop();
    //                 currentCutsceneAudio = null;
    //             }
    //         }
    // 
    // 
    //         if((double)game_state < 0.1) {
    //             if (keyCode == KeyEvent.VK_ENTER) {
    // 
    //                 constructFrames(game_state);
    // 
    //                 // if(mediaPlayerPopcorn != null && mediaPlayerPopcorn.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerPopcorn.stop(); // Handled by stopAllMusicPlayers
    //                 // if(mediaPlayerSkateboard != null && mediaPlayerSkateboard.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerSkateboard.stop(); // Handled by stopAllMusicPlayers
    // 
    //                 video = 0;
    //                 nextState = 6;
    //             }
    //         }
    //         else if((double)game_state > 0.9 && (double)game_state < 1.1) { // Title screen / Credits screen
    //             if (keyCode == KeyEvent.VK_ENTER) {
    // 
    //                 // videoimg = new Image(getClass().getResourceAsStream("/res/scylla_intro_s.gif")); // Updated image loading - These seem like placeholders, actual video loading is in constructFrames
    //                 // videoimg = new Image(getClass().getResourceAsStream("/res/start_hoplaa_s.gif")); // Updated image loading
    //                 // videoimg.setAccelerationPriority((float)1.0); // Commented out setAccelerationPriority
    // 
    // 
    //                 if(HugoHiihto.currentStateAtTheLevel >= 71 && (pulled_rope_3 || pulled_rope_1)) { // Beat the game, going to title
    //                     nextState = 0;
    //                     HugoHiihto.currentStateAtTheLevel = -5;
    //                     // Music for title screen will be handled by state 0 logic if ENTER is pressed again
    //                 }
    //                 else { // Starting a new game from title screen
    //                     if(HugoHiihto.gameOver) {
    //                         if(HugoHiihto.timerTask != null) {
    //                             HugoHiihto.timerTask.cancel();
    //                         }
    //                     }
    // 
    //                     reset(); // Resets game variables and starts GameLoop
    // 
    //                     video = 1; // Hugo's first words video
    //                     nextState = 6; // To show video
    //                     constructFrames(game_state); // Will load video 1 GIF and its .aiff sound via FMJ
    //                 }
    //             }
    //             if((keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9) && key5) {      key6 = true; }
    //             if(keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9) {                key1 = true; }
    //             if((keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7) && key9) {      key10 = true; }
    //             if((keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7) && key1) {      key2 = true; }
    //             if((keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0) && key3) {      key4 = true; }
    //             if((keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0) && key2) {      key3 = true; }
    //             if((keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5) && key6) {      key7 = true; }
    //             if((keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) && key11) {     key12 = true; }
    //             if((keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) && key10) {     key11 = true; }
    //             if((keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) && key8) {      key9 = true; }
    //             if((keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) && key7) {      key8 = true; }
    //             if((keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) && key4) {      key5 = true; }
    //             if(cheatBackflip180) {
    //                 // Original: Game_Display.clipMoney.setFramePosition(0); Game_Display.clipMoney.start();
    //                 if (soundMoney != null) {
    //                     soundMoney.setVolume(1.0); // Full volume
    //                     soundMoney.play();
    //                 } else {
    //                     Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "soundMoney AudioClip is null. Cannot play cheat sound.");
    //                 }
    //             }
    //             if(key12) { // Activating the cheat mode!
    //                 cheatBackflip180 = true;
    //             }
    //         }
    //         else if((double)game_state > 1.9 && (double)game_state < 2.1) {
    //             // 0 = Scylla intro,          1 = Hugo's first words hoplaa nyt hommiin,
    //             // 2 = Scylla button,         3 = three ropes intro,
    //             // 4 = Hugo asks for two,     5 = two chosen correctly,
    //             // 6 = made a wrong choice,   7 = (knock) Hugo finished the skiing,
    //             // 8 = [knock] wake up pahvi, 9 = (knock) now the last troll going,
    //             // 10 = (knock) game over,    11 = rope #1,
    //             // 12 = rope #2,              13 = rope #3,
    //             // 14 = snowman,              15 = snowball,
    //             // 16 = bomb,                 17 = beaver.
    // 
    //             if(video == 0) { // Scylla Intro Video
    //                 if(keyCode == KeyEvent.VK_ENTER) { // After Scylla intro video, go to title/credits screen
    // 
    //                     if(HugoHiihto.currentStateAtTheLevel >= 71 && HugoHiihto.gameOver == false) { // Game beaten, show credits
    //                         if (mediaPlayerCredits != null) {
    //                             // mediaPlayerCredits.setCycleCount(MediaPlayer.INDEFINITE); // Already set in static initializer
    //                             mediaPlayerCredits.play();
    //                         } else {
    //                             Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "mediaPlayerCredits is null. Cannot play credits music.");
    //                         }
    //                     }
    //                     else { // Game not beaten or game over, show title screen
    //                         if (mediaPlayerMenu != null) {
    //                             // mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE); // Already set in static initializer
    //                             mediaPlayerMenu.play();
    //                         } else {
    //                             Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "mediaPlayerMenu is null. Cannot play menu music.");
    //                         }
    //                     }
    // 
    //                     gamePaused = true; // Game is paused as we are on a static screen (title/credits)
    //                     nextState = 1; // Go to title/credits screen state
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 1) {
    //                 if(keyCode == KeyEvent.VK_ENTER) { // After "Hoplaa nyt hommiin" video
    //                     nextState = 3; // to the actual game state
    //                     gamePaused = false; // Unpause to start gameplay
    //                     if (mediaPlayerSkiingLoop != null) mediaPlayerSkiingLoop.play(); // Start skiing music
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 2) {
    //                 if(keyCode == KeyEvent.VK_ENTER) { // After Scylla button press video
    //                     nextState = 3; // to the actual game state
    //                     gamePaused = false; // Unpause
    //                     // Music (Popcorn/Skateboard/FinnishHugo) was already started by GameLoop's 'S' condition, skiing music should resume if it was playing.
    //                     // GameLoop's 'S' condition in HugoHiihto.java already handles starting one of these:
    //                     // mediaPlayerPopcorn, mediaPlayerSkateboard, or mediaPlayerFinnishHugo.
    //                     // It also stops mediaPlayerSkiingLoop. We might want to restart skiing loop here if no other music is playing,
    //                     // but typically Scylla button leads to a different music theme.
    //                     // For now, let's assume the music started by 'S' continues.
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 3) { // Rope selection intro video ("Scylla0.gif" + "Scylla0.aiff")
    //                 // This video is where Hugo stands before the three ropes.
    //                 // Music like Popcorn, Skateboard, or FinnishHugo might be playing from the 'S' trigger in GameLoop.
    //                 // Stop them before proceeding to actual rope choice.
    //                 // if(mediaPlayerPopcorn != null && mediaPlayerPopcorn.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerPopcorn.stop(); // Handled by stopAllMusicPlayers
    //                 // if(mediaPlayerSkateboard != null && mediaPlayerSkateboard.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerSkateboard.stop(); // Handled by stopAllMusicPlayers
    //                 // if(mediaPlayerFinnishHugo != null && mediaPlayerFinnishHugo.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerFinnishHugo.stop(); // Handled by stopAllMusicPlayers
    //                 // Note: stopAllMusicPlayers() was called on ANY Enter key press at the beginning of keyPressed.
    //                 // So, these specific musics should already be stopped if user pressed Enter to get here.
    // 
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //                 if(keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
    //                     // if(mediaPlayer != null) { // FMJ player for video sound // FMJ REMOVED
    //                     //     mediaPlayer.stop();
    //                     // }
    //                     if (currentCutsceneAudio != null) { currentCutsceneAudio.stop(); currentCutsceneAudio = null;}
    //                     pulled_rope_1 = true;
    //                     pulled_rope_2 = false;
    //                     pulled_rope_3 = false;
    //                     System.out.println("1 chosen!");
    // 
    //                     // Score logic remains
    //                     HugoHiihto.increaseScoreThousands(thousands);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    // 
    //                     video = 11; // Video for rope 1 outcome
    //                     nextState = 6; 
    //                 }
    //                 if(keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
    //                     // if(mediaPlayer != null) { // FMJ REMOVED
    //                     //     mediaPlayer.stop();
    //                     // }
    //                     if (currentCutsceneAudio != null) { currentCutsceneAudio.stop(); currentCutsceneAudio = null;}
    //                     pulled_rope_2 = true;
    //                     pulled_rope_1 = false;
    //                     pulled_rope_3 = false;
    //                     System.out.println("2 chosen!");
    //                     HugoHiihto.currentStateAtTheLevel = -5; // Game over logic
    //                     video = 12; // Video for rope 2 outcome
    //                     nextState = 6;
    //                 }
    //                 if(keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
    //                     // if(mediaPlayer != null) { // FMJ REMOVED
    //                     //     mediaPlayer.stop();
    //                     // }
    //                     if (currentCutsceneAudio != null) { currentCutsceneAudio.stop(); currentCutsceneAudio = null;}
    //                     pulled_rope_3 = true;
    //                     pulled_rope_1 = false;
    //                     pulled_rope_2 = false;
    //                     System.out.println("3 chosen!");
    // 
    //                     // Score logic remains
    //                     HugoHiihto.increaseScoreThousands(thousands);
    //                     HugoHiihto.increaseScoreThousands(thousands);
    //                     HugoHiihto.increaseScoreTens(tens);
    //                     HugoHiihto.increaseScoreTens(tens);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    //                     HugoHiihto.increaseScoreOnes(ones);
    // 
    //                     video = 13; // Video for rope 3 outcome
    //                     nextState = 6;
    //                 }
    //             }
    //             if(video == 4) {    // Hugo asks for two items video ("remember2forKey_intro.gif")
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     nextState = 4; // Go to item guessing state
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 5) {    // Two items chosen correctly video ("remember2forKey_win.gif")
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     // stopAllMusicPlayers(); // Called at the start of Enter press
    //                     // if(mediaPlayerPopcorn != null && mediaPlayerPopcorn.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerPopcorn.stop(); // Handled by stopAllMusicPlayers
    //                     // if(mediaPlayerSkateboard != null && mediaPlayerSkateboard.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerSkateboard.stop(); // Handled by stopAllMusicPlayers
    //                     // if(mediaPlayerFinnishHugo != null && mediaPlayerFinnishHugo.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerFinnishHugo.stop(); // Handled by stopAllMusicPlayers
    // 
    //                     video = 3; // Go to three ropes intro video
    //                     nextState = 6; // To show video
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 6) {    // Wrong item choice video ("remember2forKey_fail.gif")
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     HugoHiihto.gameOver = true;
    //                     nextState = 5; // Go to game over / score screen state
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 7) {
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     Game_Display.video = 4;
    //                     Game_Display.nextState = 6;
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 8) {
    //                 if(keyCode == KeyEvent.VK_ENTER) { // After "heraa pahvi" video
    //                     nextState = 3; // to the actual game
    //                     gamePaused = false;
    //                     if (mediaPlayerSkiingLoop != null) mediaPlayerSkiingLoop.play(); // Resume skiing music
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 9) {
    //                 if(keyCode == KeyEvent.VK_ENTER) { // After "viimeista viedaan" video
    //                     nextState = 3; // to the actual game
    //                     gamePaused = false;
    //                     if (mediaPlayerSkiingLoop != null) mediaPlayerSkiingLoop.play(); // Resume skiing music
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 10) {
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     // cancel all timertasks!
    //                     HugoHiihto.gameOver = true;
    //                     nextState = 5;
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 11) {
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     nextState = 5;
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 12) {
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     nextState = 5;
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 13) {
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     nextState = 5;
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 14) {
    //                 currentGrid = 0;
    //                 currentHazardOrMoney1_y_position += 800;
    //                 currentHazardOrMoney2_y_position += 800;
    //                 currentHazardOrMoney3_y_position += 800;
    //                 currentHazardOrMoney4_y_position += 800;
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     if(number_of_lives >= 2) {
    //                         video = 8;
    //                         nextState = 6;
    //                     }
    //                     else {
    //                         if(number_of_lives >= 1) {
    //                             video = 9;
    //                             nextState = 6;
    //                         }
    //                         else {
    //                             video = 10;
    //                             nextState = 6;
    //                         }
    //                     }
    // 
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 15) {
    //                 currentGrid = 0;
    //                 currentHazardOrMoney1_y_position += 800;
    //                 currentHazardOrMoney2_y_position += 800;
    //                 currentHazardOrMoney3_y_position += 800;
    //                 currentHazardOrMoney4_y_position += 800;
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     if(number_of_lives >= 2) {
    //                         video = 8;
    //                         nextState = 6;
    //                     }
    //                     else {
    //                         if(number_of_lives >= 1) {
    //                             video = 9;
    //                             nextState = 6;
    //                         }
    //                         else {
    //                             video = 10;
    //                             nextState = 6;
    //                         }
    //                     }
    // 
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 16) {
    //                 currentGrid = 0;
    //                 currentHazardOrMoney1_y_position += 800;
    //                 currentHazardOrMoney2_y_position += 800;
    //                 currentHazardOrMoney3_y_position += 800;
    //                 currentHazardOrMoney4_y_position += 800;
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     if(number_of_lives >= 2) {
    //                         video = 8;
    //                         nextState = 6;
    //                     }
    //                     else {
    //                         if(number_of_lives >= 1) {
    //                             video = 9;
    //                             nextState = 6;
    //                         }
    //                         else {
    //                             video = 10;
    //                             nextState = 6;
    //                         }
    //                     }
    // 
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //             if(video == 17) {
    //                 currentGrid = 0;
    //                 currentHazardOrMoney1_y_position += 800;
    //                 currentHazardOrMoney2_y_position += 800;
    //                 currentHazardOrMoney3_y_position += 800;
    //                 currentHazardOrMoney4_y_position += 800;
    //                 if(keyCode == KeyEvent.VK_ENTER) {
    //                     if(number_of_lives >= 2) {
    //                         video = 8;
    //                         nextState = 6;
    //                     }
    //                     else {
    //                         if(number_of_lives >= 1) {
    //                             video = 9;
    //                             nextState = 6;
    //                         }
    //                         else {
    //                             video = 10;
    //                             nextState = 6;
    //                         }
    //                     }
    // 
    //                 }
    //                 if(keyCode == KeyEvent.VK_ESCAPE) {
    //                     System.gc();
    //                     System.exit(0);
    //                 }
    //             }
    //         }
    //         else if((double)game_state > 2.9 && (double)game_state < 3.1) {
    // 
    //             maxW = 630 - 220; // Replaced d.width
    // 
    //             if ((keyCode == KeyEvent.VK_LEFT && keyCode != KeyEvent.VK_RIGHT) ||
    //                     (keyCode == KeyEvent.VK_4 && !(keyCode == KeyEvent.VK_6)) ||
    //                     (keyCode == KeyEvent.VK_NUMPAD4 && !(keyCode == KeyEvent.VK_NUMPAD6))) {
    //                 if(!gamePaused) {
    //                     if (x <= -25) {
    //                     }
    //                     else {
    //                         if(currentGrid >= 1){
    //                             currentGrid--;
    //                         }
    //                     }
    //                 }
    //             }
    //             else if ((keyCode == KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) ||
    //                     (keyCode == KeyEvent.VK_6 && !(keyCode == KeyEvent.VK_4)) ||
    //                     (keyCode == KeyEvent.VK_NUMPAD6 && !(keyCode == KeyEvent.VK_NUMPAD4))) {
    //                 if(!gamePaused) {
    //                     if (x >= maxW) {
    //                     }
    //                     else {
    //                         if(currentGrid <= 2){
    //                             currentGrid++;
    //                         }
    //                     }
    //                 }
    //             }
    // 
    // 
    //             if(keyCode == KeyEvent.VK_ENTER) {
    //                 pausedWithEnter = true;
    //                 if(!gamePaused) {
    //                     gamePaused = true;
    //                 }
    //                 else {
    //                     pausedWithEnter = false;
    //                     gamePaused = false;
    //                 }
    //             }
    //         }
    //         else if((double)game_state > 3.9 && (double)game_state < 4.1) {
    //             //keyCode = e.getKeyCode();
    //             // currentlyAllCorrect = true;
    //             if(keyCode == KeyEvent.VK_NUMPAD1 || keyCode == KeyEvent.VK_1) { // 1
    //                 if(!secondPhase) {
    //                     if( thingsToRemember.charAt(0) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(0) == 'B' ||
    //                             thingsToRemember.charAt(0) == 'C' ||
    //                             thingsToRemember.charAt(0) == 'D' ||
    //                             thingsToRemember.charAt(0) == 'H' ||
    //                             thingsToRemember.charAt(0) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     secondPhase = true;
    //                 }
    //                 else {
    //                     if( thingsToRemember.charAt(3) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(3) == 'B' ||
    //                             thingsToRemember.charAt(3) == 'C' ||
    //                             thingsToRemember.charAt(3) == 'D' ||
    //                             thingsToRemember.charAt(3) == 'H' ||
    //                             thingsToRemember.charAt(3) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     //secondPhase = false;
    //                     if(currentlyAllCorrect) {
    //                         allCorrectInTheEnd = true;
    //                         System.out.println("Both correct!");
    //                     }
    //                 }
    //             }
    //             if(keyCode == KeyEvent.VK_NUMPAD2 || keyCode == KeyEvent.VK_2) { // 2
    //                 if(!secondPhase) {
    //                     if( thingsToRemember.charAt(1) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(1) == 'B' ||
    //                             thingsToRemember.charAt(1) == 'C' ||
    //                             thingsToRemember.charAt(1) == 'D' ||
    //                             thingsToRemember.charAt(1) == 'H' ||
    //                             thingsToRemember.charAt(1) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     secondPhase = true;
    //                 }
    //                 else {
    //                     if( thingsToRemember.charAt(4) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(4) == 'B' ||
    //                             thingsToRemember.charAt(4) == 'C' ||
    //                             thingsToRemember.charAt(4) == 'D' ||
    //                             thingsToRemember.charAt(4) == 'H' ||
    //                             thingsToRemember.charAt(4) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     //secondPhase = false;
    //                     if(currentlyAllCorrect) {
    //                         allCorrectInTheEnd = true;
    //                         System.out.println("Both correct!");
    //                     }
    //                 }
    //             }
    //             if(keyCode == KeyEvent.VK_NUMPAD3 || keyCode == KeyEvent.VK_3) { // 3
    //                 if(!secondPhase) {
    //                     if( thingsToRemember.charAt(2) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(2) == 'B' ||
    //                             thingsToRemember.charAt(2) == 'C' ||
    //                             thingsToRemember.charAt(2) == 'D' ||
    //                             thingsToRemember.charAt(2) == 'H' ||
    //                             thingsToRemember.charAt(2) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     secondPhase = true;
    //                 }
    //                 else {
    //                     if( thingsToRemember.charAt(5) == 'A' || // if caps, then correct
    //                             thingsToRemember.charAt(5) == 'B' ||
    //                             thingsToRemember.charAt(5) == 'C' ||
    //                             thingsToRemember.charAt(5) == 'D' ||
    //                             thingsToRemember.charAt(5) == 'H' ||
    //                             thingsToRemember.charAt(5) == 'S') {
    //                         //currentlyAllCorrect = true;
    //                     }
    //                     else {
    //                         currentlyAllCorrect = false;
    //                     }
    //                     //secondPhase = false;
    //                     if(currentlyAllCorrect) {
    //                         allCorrectInTheEnd = true;
    //                         System.out.println("Both correct!");
    //                     }
    //                 }
    //             }
    // 
    //             if ((currentlyAllCorrect && (
    //                     keyCode == KeyEvent.VK_1 ||
    //                             keyCode == KeyEvent.VK_2 ||
    //                             keyCode == KeyEvent.VK_3 ||
    //                             keyCode == KeyEvent.VK_NUMPAD1 ||
    //                             keyCode == KeyEvent.VK_NUMPAD2 ||
    //                             keyCode == KeyEvent.VK_NUMPAD3))) {
    //                 // Original: Game_Display.clipCorrect.setFramePosition(0); Game_Display.clipCorrect.start();
    //                 if (soundCorrect != null) {
    //                     soundCorrect.setVolume(1.0); // Full volume
    //                     soundCorrect.play();
    //                 } else {
    //                     Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "soundCorrect AudioClip is null. Cannot play correct selection sound.");
    //                 }
    //             }
    //             if(!currentlyAllCorrect) {
    //                 if(!allCorrectInTheEnd) {
    //                     System.out.println("Wrong guess, not proceeding to ropes!");
    // 
    //                     nextState = 5;
    //                 }
    //             }
    //             if(allCorrectInTheEnd) {
    //                 System.out.println("Proceeding to ropes!");
    //                 video = 3;
    //                 nextState = 2;
    //             }
    //         }
    //         else if((double)game_state > 4.9 && (double)game_state < 5.1) {
    //             if(keyCode == KeyEvent.VK_ENTER) {
    //                 if(pulled_rope_1 || pulled_rope_3) {
    //                     HugoHiihto.currentStateAtTheLevel = 71;
    //                     nextState = 1;
    // 
    //                     if(HugoHiihto.currentStateAtTheLevel >= 71) {
    //                         try {
    //                             if (audioPlayerCredits == null) {
    //                                 Media media = new Media(getClass().getResource(PATH_MUSIC_CREDITS_WAV).toExternalForm());
    //                                 // audioPlayerCredits.setCycleCount(MediaPlayer.INDEFINITE); // Already set
    //                             }
    //                             // audioPlayerCredits.setVolume(1.0); // Already set
    //                             audioPlayerCredits.play();
    //                         } catch (Exception ex) { 
    //                             Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, "Error playing credits music", ex);
    //                         }
    //                     }
    //                     else { // Go to title screen, play menu music
    //                         try {
    //                             if (audioPlayerMenu == null) { // Should be initialized in static block
    //                                 Media media = new Media(getClass().getResource(PATH_MUSIC_PS1HUGO2MENU_WAV).toExternalForm());
    //                                 audioPlayerMenu = new MediaPlayer(media);
    //                                 audioPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
    //                                 audioPlayerMenu.setVolume(1.0);
    //                             }
    //                             audioPlayerMenu.play();
    //                         } catch (Exception ex) { 
    //                             Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, "Error playing menu music", ex);
    //                         }
    //                     }
    //                 }
    //                 else { // Game over, go to very first screen (state 0)
    //                     nextState = 0;
    //                 }
    //             }
    //         }
    //         else if((double)game_state >= 5.1) { // Should not happen if state transitions are correct
    //             nextState = 2; // Default to video state
    //             System.out.println(" --- keyPressed --- unexpected game_state " + game_state + ", transitioning to state " + nextState);
    //         }
    //     }
    // 
    //     /**
    //      * When releasing the left/right button in state 3. Plays a sound effect.
    //      *
    //      * @param e
    //      */
        @Override // Restored annotation
        public void keyReleased(KeyEvent e) { // This logic seems mostly fine as AudioClips are used.
    
            if(!gamePaused && game_state == 3) { // During active gameplay
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // Play ski track change sound
                    if (soundChangeGrid != null) {
                        // soundChangeGrid.setVolume(0.07); // Volume already set in static initializer if needed
                        soundChangeGrid.play();
                    } else {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "soundChangeGrid AudioClip is null.");
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                    // Play button 4 sound
                    if (soundButton4 != null) {
                        soundButton4.play();
                    } else {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "soundButton4 AudioClip is null.");
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    // Play button 6 sound
                    if (soundButton6 != null) {
                        soundButton6.play();
                    } else {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.WARNING, "soundButton6 AudioClip is null.");
                    }
                }
            }
        }
    } // End of AL class

    /**
     * The constructor.
     *
     * @throws Exception
     */
    public Game_Display() throws Exception {
        // addKeyListener(new AL()); // Commented out Swing specific call
        constructFrames(game_state);
        if(videoimg != null) {  // videos should always start at the beginning
            // videoimg.flush(); // Commented out flush
            videoimg = null;
        }
    }

    /**
     * Called by the constructor at first, then others can call when needed.
     * Creates the base of the game display, makes a screen update.
     *
     * @param gameState
     */
    public void constructFrames(int gameState) {
        // repaint(); // Commented out Swing specific call
        if(gameState != nextState) {
            //System.out.println("gameState != nextState");
            return;
        }
        if((double)gameState < 0.1) {
            theVeryFirst = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/_the_very_1st_texts.png"));
            // setFocusable(true); // Commented out Swing specific call
        }
        if((double)gameState > 0.9 && (double)gameState < 1.1) {
            if(gameState != 3 && nextState != 3) {
                gamePaused = true;
            }

            if(HugoHiihto.currentStateAtTheLevel >= 71 && HugoHiihto.gameOver == false) {
                creditsScreen = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/credits_screen.png"));
                // setFocusable(true); // Commented out Swing specific call
            }
            else {
                titleScreen = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/title_screen.png"));
                // setFocusable(true); // Commented out Swing specific call
            }
        }
        if((double)gameState > 1.9 && (double)gameState < 2.1) { // Video display state
            // setFocusable(true); // Commented out Swing specific call
            // The 'useMP4' boolean and its 'true' path (Desktop.getDesktop().open()) are removed.
            // The primary path is now GIF + AudioClip.
            
            if (currentCutsceneAudio != null) {
                currentCutsceneAudio.stop(); // Stop any previously playing cutscene audio
            }

            switch(video) {
                case 0: currentCutsceneAudio = soundScyllaIntro; break;
                case 1: currentCutsceneAudio = soundStartHoplaa; break;
                case 2: currentCutsceneAudio = soundScyllaButtonPress; break;
                case 3: currentCutsceneAudio = soundScylla0; break;
                case 4: currentCutsceneAudio = soundRemember2forKeyIntro; break;
                case 5: currentCutsceneAudio = soundRemember2forKeyWin; break;
                case 6: currentCutsceneAudio = soundRemember2forKeyFail; break;
                case 7: currentCutsceneAudio = soundScreenTalkFinishLine; break;
                case 8: currentCutsceneAudio = soundScreenTalkHeraaPahvi; break;
                case 9: currentCutsceneAudio = soundScreenTalkViimeistaViedaan; break;
                case 10: currentCutsceneAudio = soundScreenTalkGameOver; break;
                case 11: currentCutsceneAudio = soundScylla1; break;
                case 12: currentCutsceneAudio = soundScylla2; break;
                case 13: currentCutsceneAudio = soundScylla3; break;
                case 14: currentCutsceneAudio = soundLoseLifeSnowman; break;
                case 15: currentCutsceneAudio = soundLoseLifeSnowball; break;
                case 16: currentCutsceneAudio = soundLoseLifeBomb; break;
                case 17: currentCutsceneAudio = soundLoseLifeBeaver; break;
                default: currentCutsceneAudio = null; // Should not happen
            }

            if (currentCutsceneAudio != null) {
                currentCutsceneAudio.play();
                System.out.println("Playing cutscene sound for video ID: " + video);
            } else {
                System.out.println("No specific AudioClip found for video ID: " + video);
            }
        }
        if((double)gameState > 2.9 && (double)gameState < 3.1) {
            // repaint(); // Commented out Swing specific call
            sprite_L2 = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_ski_L.gif"));
            sprite_L = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_ski_L.gif"));
            sprite_R = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_ski_R.gif"));
            sprite_R2 = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_ski_R.gif"));
            bg = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/background1.gif"));
            // setFocusable(true); // Commented out Swing specific call
            x = 55;
            y = (int) ((int) 500/2.67); // Replaced d.getHeight()
            cloud = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/cloud.png"));
            possibleTree1icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees2.png"));
            possibleTree1 = possibleTree1icon;
            possibleTree2icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees0.png"));
            possibleTree2 = possibleTree2icon;
            possibleTree3icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees1.png"));
            possibleTree3 = possibleTree3icon;
            possibleTree4icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees2.png"));
            possibleTree4 = possibleTree4icon;
            possibleTree5icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees0.png"));
            possibleTree5 = possibleTree5icon;
            possibleTree6icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees1.png"));
            possibleTree6 = possibleTree6icon;
            possibleTree7icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees2.png"));
            possibleTree7 = possibleTree7icon;
            possibleTree8icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/trees1.png"));
            possibleTree8 = possibleTree8icon;
            hugolife1 = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_life.png"));
            hugolife2 = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_life.png"));
            hugolife3 = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/hugo_life.png"));
            pause = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/pause.png"));
            scorebar = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/score-life-bar.png"));
        }
        if((double)gameState > 3.9 && (double)gameState < 4.1) {
            bgCave = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/cave_entrance00.png"));
            // setFocusable(true); // Commented out Swing specific call
            cave_x = 1;
            cave_y = 1;
            for(int i = 0; i < 6; i++) {
                int pos = 0;
                int hei = 0;
                if(i == 0) {
                    pos = position1;
                    hei = heightLevel1;
                    u1b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select1.png"));
                    u1w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected1.png"));
                }
                if(i == 1) {
                    pos = position2;
                    hei = heightLevel1;
                    u2b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select2.png"));
                    u2w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected2.png"));
                }
                if(i == 2) {
                    pos = position3;
                    hei = heightLevel1;
                    u3b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select3.png"));
                    u3w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected3.png"));
                }
                if(i == 3) {
                    pos = position1;
                    hei = heightLevel2;
                    d1b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select1.png"));
                    d1w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected1.png"));
                }
                if(i == 4) {
                    pos = position2;
                    hei = heightLevel2;
                    d2b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select2.png"));
                    d2w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected2.png"));
                }
                if(i == 5) {
                    pos = position3;
                    hei = heightLevel2;
                    d3b = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_select3.png"));
                    d3w = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/num_selected3.png"));
                }

                if(thingsToRemember.charAt(i) == 'a' || thingsToRemember.charAt(i) == 'A') {
                    asterisk_x_position = (int) ((int) 630/6)+(pos); 
                    asterisk_y_position = (int) ((int) 500/19)+(hei); 
                    asterisk = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_A_asterisk.png")); 
                }
                if(thingsToRemember.charAt(i) == 'b' || thingsToRemember.charAt(i) == 'B') {
                    bell_x_position = (int) ((int) 630/6)+(pos); 
                    bell_y_position = (int) ((int) 500/19)+(hei); 
                    bell = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_B_bell.png")); 
                }
                if(thingsToRemember.charAt(i) == 'c' || thingsToRemember.charAt(i) == 'C') {
                    clock_x_position = (int) ((int) 630/6)+(pos); 
                    clock_y_position = (int) ((int) 500/19)+(hei); 
                    clock = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_C_clock.png")); 
                }
                if(thingsToRemember.charAt(i) == 'd' || thingsToRemember.charAt(i) == 'D') {
                    diamond_x_position = (int) ((int) 630/6)+(pos); 
                    diamond_y_position = (int) ((int) 500/19)+(hei); 
                    diamond = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_D_diamond.png")); 
                }
                if(thingsToRemember.charAt(i) == 'h' || thingsToRemember.charAt(i) == 'H') {
                    hashtag_x_position = (int) ((int) 630/6)+(pos); 
                    hashtag_y_position = (int) ((int) 500/19)+(hei); 
                    hashtag = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_H_hash.png")); 
                }
                if(thingsToRemember.charAt(i) == 's' || thingsToRemember.charAt(i) == 'S') {
                    star_x_position = (int) ((int) 630/6)+(pos); 
                    star_y_position = (int) ((int) 500/19)+(hei); 
                    star = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_S_star.png")); 
                }
            }
        }
        if((double)gameState > 4.9 && (double)gameState < 5.1) {
            // setFocusable(true); // Commented out Swing specific call
            scoreBGR = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/title_screen_nothing.png"));
            star = new javafx.scene.image.Image(getClass().getResourceAsStream("/res/remember_S_star.png"));
            if(videoimg != null) {
                // videoimg.flush(); // Commented out flush
                videoimg = null;
            }
        }

        if(videoimg != null) {  // videos should always start at the beginning
            // videoimg.flush(); // Commented out flush
            videoimg = null;
        }
        // repaint(); // Commented out Swing specific call
    }

    /**
     * The paint component method for graphics object(s).
     * @param g
     */
    /* // Commented out paintComponent method
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if((double)game_state < 0.1) {
            super.paintComponent(g);
            g.drawImage(theVeryFirst, 0, 0, this);
            repaint();

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();

            }
        }

        if((double)game_state > 0.9 && (double)game_state < 1.1) {
            super.paintComponent(g);
            if(HugoHiihto.currentStateAtTheLevel >= 71 && HugoHiihto.gameOver == false) {
                //super.paintComponent(g);
                g.drawImage(creditsScreen, 0, 0, this);
                repaint();
            }
            else {
                //super.paintComponent(g);
                g.drawImage(titleScreen, 0, 0, this);
                repaint();
            }

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();

            }
        }

        if((double)game_state > 1.9 && (double)game_state < 2.1) {
            super.paintComponent(g);

            if(useMP4) { // If you think that ".aiff + .gif" is not a good combination
                String pathMP4 = "";
                switch(video) {
                    case 0 ->  {
                        pathMP4 = "res/scylla_intro.mp4";
                        //pathSound = "res/scylla_intro.aiff";
                    }
                    case 1 ->  {
                        pathMP4 = "res/start_hoplaa.mp4";
                        //pathSound = "res/start_hoplaa.aiff";
                    }
                    case 2 ->  {
                        pathMP4 = "res/scylla_button_press.mp4";
                        //pathSound = "res/scylla_button_press.aiff";
                    }
                    case 3 ->  {
                        pathMP4 = "res/scylla0.mp4";
                        //pathSound = "res/scylla0.aiff";
                    }
                    case 4 ->  {
                        pathMP4 = "res/remember2forKey_intro.mp4";
                        //pathSound = "res/remember2forKey_intro.aiff";
                    }
                    case 5 ->  {
                        pathMP4 = "res/remember2forKey_win.mp4";
                        //pathSound = "res/remember2forKey_win.aiff";
                    }
                    case 6 ->  {
                        pathMP4 = "res/remember2forKey_fail.mp4";
                        //pathSound = "res/remember2forKey_fail.aiff";
                    }
                    case 7 ->  {
                        pathMP4 = "res/screentalk_finish_line.mp4";
                        //pathSound = "res/screentalk_finish_line.aiff";
                    }
                    case 8 ->  {
                        pathMP4 = "res/screentalk_heraa_pahvi.mp4";
                        //pathSound = "res/screentalk_heraa_pahvi.aiff";
                    }
                    case 9 ->  {
                        pathMP4 = "res/screentalk_viimeista_viedaan.mp4";
                        //pathSound = "res/screentalk_viimeista_viedaan.aiff";
                    }
                    case 10 ->  {
                        pathMP4 = "res/screentalk_game_over.mp4";
                        //pathSound = "res/screentalk_game_over.aiff";
                    }
                    case 11 ->  {
                        pathMP4 = "res/scylla1.mp4";
                        //pathSound = "res/scylla1.aiff";
                    }
                    case 12 ->  {
                        pathMP4 = "res/scylla2.mp4";
                        //pathSound = "res/scylla2.aiff";
                    }
                    case 13 ->  {
                        pathMP4 = "res/scylla3.mp4";
                        //pathSound = "res/scylla3.aiff";
                    }
                    case 14 ->  {
                        pathMP4 = "res/loselife_snowman.mp4";
                        //pathSound = "res/loselife_snowman.aiff";
                    }
                    case 15 ->  {
                        pathMP4 = "res/loselife_snowball.mp4";
                        //pathSound = "res/loselife_snowball.aiff";
                    }
                    case 16 ->  {
                        pathMP4 = "res/loselife_bomb.mp4";
                        //pathSound = "res/loselife_bomb.aiff";
                    }
                    case 17 ->  {
                        pathMP4 = "res/loselife_beaver.mp4";
                        //pathSound = "res/loselife_beaver.aiff";
                    }
                }

                File video_source = new File(pathMP4);
                try {
                    Desktop.getDesktop().open(video_source); // opens Windows Media Player for instance
                }
                catch (IOException ex) {                   // not the best way to display mp4s
                    Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            else {
                String pathGif = "";
                //String pathSound = "";
                switch(video) {
                    case 0 ->  {
                        pathGif = "res/scylla_intro_s.gif";
                        //pathSound = "res/scylla_intro.aiff";
                    }
                    case 1 ->  {
                        pathGif = "res/start_hoplaa_s.gif";
                        //pathSound = "res/start_hoplaa.aiff";
                    }
                    case 2 ->  {
                        pathGif = "res/scylla_button_press_s.gif";
                        //pathSound = "res/scylla_button_press.aiff";
                    }
                    case 3 ->  {
                        pathGif = "res/scylla0_s.gif";
                        //pathSound = "res/scylla0.aiff";
                    }
                    case 4 ->  {
                        pathGif = "res/remember2forKey_intro_s.gif";
                        //pathSound = "res/remember2forKey_intro.aiff";
                    }
                    case 5 ->  {
                        pathGif = "res/remember2forKey_win_s.gif";
                        //pathSound = "res/remember2forKey_win.aiff";
                    }
                    case 6 ->  {
                        pathGif = "res/remember2forKey_fail_s.gif";
                        //pathSound = "res/remember2forKey_fail.aiff";
                    }
                    case 7 ->  {
                        pathGif = "res/screentalk_finish_line_s.gif";
                        //pathSound = "res/screentalk_finish_line.aiff";
                    }
                    case 8 ->  {
                        pathGif = "res/screentalk_heraa_pahvi_s.gif";
                        //pathSound = "res/screentalk_heraa_pahvi.aiff";
                    }
                    case 9 ->  {
                        pathGif = "res/screentalk_viimeista_viedaan_s.gif";
                        //pathSound = "res/screentalk_viimeista_viedaan.aiff";
                    }
                    case 10 ->  {
                        pathGif = "res/screentalk_game_over_s.gif";
                        //pathSound = "res/screentalk_game_over.aiff";
                    }
                    case 11 ->  {
                        pathGif = "res/scylla1_s.gif";
                        //pathSound = "res/scylla1.aiff";
                    }
                    case 12 ->  {
                        pathGif = "res/scylla2_s.gif";
                        //pathSound = "res/scylla2.aiff";
                    }
                    case 13 ->  {
                        pathGif = "res/scylla3_s.gif";
                        //pathSound = "res/scylla3.aiff";
                    }
                    case 14 ->  {
                        pathGif = "res/loselife_snowman_s.gif";
                        //pathSound = "res/loselife_snowman.aiff";
                    }
                    case 15 ->  {
                        pathGif = "res/loselife_snowball_s.gif";
                        //pathSound = "res/loselife_snowball.aiff";
                    }
                    case 16 ->  {
                        pathGif = "res/loselife_bomb_s.gif";
                        //pathSound = "res/loselife_bomb.aiff";
                    }
                    case 17 ->  {
                        pathGif = "res/loselife_beaver_s.gif";
                        //pathSound = "res/loselife_beaver.aiff";
                    }
                }   // Important! Do not change the file names.

                videoIMGicon = new ImageIcon(pathGif);
                super.paintComponent(g);
                videoimg = null; // .gif
                videoimg = videoIMGicon.getImage();
                int wi = (int) (d.getWidth());
                int he = (int) (d.getHeight()-40);
                videoimg.setAccelerationPriority((float)1.0); // from 0-> lowest to 1-> highest

                g.drawImage(videoimg, 0, 0, wi, he, null);
                for(int i = 0; i < 30000; i++) {
                    repaint(); // Important repaint lines
                }
            }

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();

            }
        }
        if((double)game_state > 2.9 && (double)game_state < 3.1) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, null);


            Thread Cloud = new Thread() {
                @Override
                /**
                 * Run (cloud) object 0 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(System.currentTimeMillis()%19 == 0) {
                        if(leftWind) {
                            cloud_x_position--;
                        }
                        else {
                            cloud_x_position++;
                        }
                    }
                    if(cloud_x_position < -300 && leftWind) {
                        cloud_x_position = d.width;
                    }
                    if(cloud_x_position > 700 && !leftWind) {
                        cloud_x_position -=1000;
                    }
                }
            };
            if(!Cloud.isAlive()) {
                Cloud.start();
            }

            Thread Tr1 = new Thread() {
                @Override
                /**
                 * Run object 1 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree1_x_position-=2;
                            possibleTree1_y_position++;
                        }
                        if(possibleTree1_x_position < -340) {
                            possibleTree1_x_position = (d.width/8)-7;
                            possibleTree1_y_position = (d.height/12)+20;
                        }
                    }
                }
            };
            if(!Tr1.isAlive()) {
                Tr1.start();
            }
            Thread Tr2 = new Thread() {
                @Override
                /**
                 * Run object 2 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree2_x_position-=2;
                            possibleTree2_y_position++;
                        }
                        if(possibleTree2_x_position < -400) {
                            possibleTree2_x_position = (d.width/4)-4;
                            possibleTree2_y_position = (d.height/8)+20;
                        }
                    }
                }
            };
            if(!Tr2.isAlive()) {
                Tr2.start();
            }
            Thread Tr3 = new Thread() {
                @Override
                /**
                 * Run object 3 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree3_x_position-=2;
                            possibleTree3_y_position++;
                        }
                        if(possibleTree3_x_position < -500) {
                            possibleTree3_x_position = (d.width/5)-4;
                            possibleTree3_y_position = (d.height/17)+20;
                        }
                    }
                }
            };
            if(!Tr3.isAlive()) {
                Tr3.start();
            }
            Thread Tr4 = new Thread() {
                @Override
                /**
                 * Run object 4 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree4_x_position+=2;
                            possibleTree4_y_position++;
                        }
                        if(possibleTree4_x_position > 640) {
                            possibleTree4_x_position = (int) ((int) d.getWidth()/2)+40;
                            possibleTree4_y_position = (int) ((int) d.getHeight()/3)-100;
                        }
                    }
                }
            };
            if(!Tr4.isAlive()) {
                Tr4.start();
            }
            Thread Tr5 = new Thread() {
                @Override
                /**
                 * Run object 5 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree5_x_position+=2;
                            possibleTree5_y_position++;
                        }
                        if(possibleTree5_x_position > 760) {
                            possibleTree5_x_position = (int) ((int) d.getWidth()/2)+40;
                            possibleTree5_y_position = (int) ((int) d.getHeight()/3)-80;
                        }
                    }
                }
            };
            if(!Tr5.isAlive()) {
                Tr5.start();
            }
            Thread Tr6 = new Thread() {
                @Override
                /**
                 * Run object 6 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree6_x_position+=2;
                            possibleTree6_y_position++;
                        }
                        if(possibleTree6_x_position > 800) {
                            possibleTree6_x_position = (int) ((int) d.getWidth()/2)+40;
                            possibleTree6_y_position = (int) ((int) d.getHeight()/3)-80;
                        }
                    }
                }
            };
            if(!Tr6.isAlive()) {
                Tr6.start();
            }
            Thread Tr7 = new Thread() {
                @Override
                /**
                 * Run object 7 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree7_x_position+=2;
                            possibleTree7_y_position++;
                        }
                        if(possibleTree7_x_position > 753) {
                            possibleTree7_x_position = (int) ((int) d.getWidth()/2)+42;
                            possibleTree7_y_position = (int) ((int) d.getHeight()/3)-82;
                        }
                    }
                }
            };
            if(!Tr7.isAlive()) {
                Tr7.start();
            }
            Thread Tr8 = new Thread() {
                @Override
                /**
                 * Run object 8 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%2 == 0) {
                            possibleTree8_x_position-=2;
                            possibleTree8_y_position++;
                        }
                        if(possibleTree8_x_position < -574) {
                            possibleTree8_x_position = (d.width/9);
                            possibleTree8_y_position = (d.height/7)+32;
                        }
                    }
                }
            };
            if(!Tr8.isAlive()) {
                Tr8.start();
            }

            // finally drawing the graphical decorations:
            g.drawImage(cloud, cloud_x_position, cloud_y_position, this);
            g.drawImage(possibleTree1, possibleTree1_x_position, possibleTree1_y_position, this);
            g.drawImage(possibleTree2, possibleTree2_x_position, possibleTree2_y_position, this);
            g.drawImage(possibleTree3, possibleTree3_x_position, possibleTree3_y_position, this);
            g.drawImage(possibleTree4, possibleTree4_x_position, possibleTree4_y_position, this);
            g.drawImage(possibleTree5, possibleTree5_x_position, possibleTree5_y_position, this);
            g.drawImage(possibleTree6, possibleTree6_x_position, possibleTree6_y_position, this);
            g.drawImage(possibleTree7, possibleTree7_x_position, possibleTree7_y_position, this);
            g.drawImage(possibleTree8, possibleTree8_x_position, possibleTree8_y_position, this);

            String path_of_hazard_1 = "";
            String path_of_hazard_2 = "";
            String path_of_hazard_3 = "";
            String path_of_hazard_4 = "";
            if(currentHazardOrMoney1.equals("E") || currentHazardOrMoney1.equals("S") || currentHazardOrMoney1.equals("F")) {
                currentHazardOrMoney1_image = null;
            }
            if(currentHazardOrMoney2.equals("E") || currentHazardOrMoney2.equals("S") || currentHazardOrMoney2.equals("F")) {
                currentHazardOrMoney2_image = null;
            }
            if(currentHazardOrMoney3.equals("E") || currentHazardOrMoney3.equals("S") || currentHazardOrMoney3.equals("F")) {
                currentHazardOrMoney3_image = null;
            }
            if(currentHazardOrMoney4.equals("E") || currentHazardOrMoney4.equals("S") || currentHazardOrMoney4.equals("F")) {
                currentHazardOrMoney4_image = null;
            }

            if(currentHazardOrMoney1.equals("M")) {
                path_of_hazard_1 = "res/money.png";
            }
            if(currentHazardOrMoney1.equals("8")) {
                path_of_hazard_1 = "res/enemy_snowman.png";
            }
            if(currentHazardOrMoney1.equals("o")) {
                path_of_hazard_1 = "res/enemy_snowball.png";
            }
            if(currentHazardOrMoney1.equals("Q")) {
                path_of_hazard_1 = "res/enemy_bomb.png";
            }
            if(currentHazardOrMoney1.equals("B")) {
                path_of_hazard_1 = "res/enemy_beaver_masi.png";
            }
            if(currentHazardOrMoney2.equals("M")) {
                path_of_hazard_2 = "res/money.png";
            }
            if(currentHazardOrMoney2.equals("8")) {
                path_of_hazard_2 = "res/enemy_snowman.png";
            }
            if(currentHazardOrMoney2.equals("o")) {
                path_of_hazard_2 = "res/enemy_snowball.png";
            }
            if(currentHazardOrMoney2.equals("Q")) {
                path_of_hazard_2 = "res/enemy_bomb.png";
            }
            if(currentHazardOrMoney2.equals("B")) {
                path_of_hazard_2 = "res/enemy_beaver_masi.png";
            }
            if(currentHazardOrMoney3.equals("M")) {
                path_of_hazard_3 = "res/money.png";
            }
            if(currentHazardOrMoney3.equals("8")) {
                path_of_hazard_3 = "res/enemy_snowman.png";
            }
            if(currentHazardOrMoney3.equals("o")) {
                path_of_hazard_3 = "res/enemy_snowball.png";
            }
            if(currentHazardOrMoney3.equals("Q")) {
                path_of_hazard_3 = "res/enemy_bomb.png";
            }
            if(currentHazardOrMoney3.equals("B")) {
                path_of_hazard_3 = "res/enemy_beaver_masi.png";
            }
            if(currentHazardOrMoney4.equals("M")) {
                path_of_hazard_4 = "res/money.png";
            }
            if(currentHazardOrMoney4.equals("8")) {
                path_of_hazard_4 = "res/enemy_snowman.png";
            }
            if(currentHazardOrMoney4.equals("o")) {
                path_of_hazard_4 = "res/enemy_snowball.png";
            }
            if(currentHazardOrMoney4.equals("Q")) {
                path_of_hazard_4 = "res/enemy_bomb.png";
            }
            if(currentHazardOrMoney4.equals("B")) {
                path_of_hazard_4 = "res/enemy_beaver_masi.png";
            }
            if(currentHazardOrMoney1.equals("1")) {
                path_of_hazard_2 = ""; currentHazardOrMoney2_image = null;
                path_of_hazard_3 = ""; currentHazardOrMoney3_image = null;
                path_of_hazard_4 = ""; currentHazardOrMoney4_image = null;
                for(int i = 0; i < 3; i++) {
                    if(thingsToRemember.charAt(i) == 'A') {
                        path_of_hazard_1 = "res/remember_A_asterisk.png";
                    }
                    if(thingsToRemember.charAt(i) == 'B') {
                        path_of_hazard_1 = "res/remember_B_bell.png";
                    }
                    if(thingsToRemember.charAt(i) == 'C') {
                        path_of_hazard_1 = "res/remember_C_clock.png";
                    }
                    if(thingsToRemember.charAt(i) == 'D') {
                        path_of_hazard_1 = "res/remember_D_diamond.png";
                    }
                    if(thingsToRemember.charAt(i) == 'H') {
                        path_of_hazard_1 = "res/remember_H_hash.png";
                    }
                    if(thingsToRemember.charAt(i) == 'S') {
                        path_of_hazard_1 = "res/remember_S_star.png";
                    }
                }
            }
            if(currentHazardOrMoney1.equals("2")) {
                path_of_hazard_2 = ""; currentHazardOrMoney2_image = null;
                path_of_hazard_3 = ""; currentHazardOrMoney3_image = null;
                path_of_hazard_4 = ""; currentHazardOrMoney4_image = null;
                for(int i = 3; i < 6; i++) {
                    if(thingsToRemember.charAt(i) == 'A') {
                        path_of_hazard_1 = "res/remember_A_asterisk.png";
                    }
                    if(thingsToRemember.charAt(i) == 'B') {
                        path_of_hazard_1 = "res/remember_B_bell.png";
                    }
                    if(thingsToRemember.charAt(i) == 'C') {
                        path_of_hazard_1 = "res/remember_C_clock.png";
                    }
                    if(thingsToRemember.charAt(i) == 'D') {
                        path_of_hazard_1 = "res/remember_D_diamond.png";
                    }
                    if(thingsToRemember.charAt(i) == 'H') {
                        path_of_hazard_1 = "res/remember_H_hash.png";
                    }
                    if(thingsToRemember.charAt(i) == 'S') {
                        path_of_hazard_1 = "res/remember_S_star.png";
                    }
                }
            }
            // file names should remain exactly original

            if(!"".equals(path_of_hazard_1)) {
                //currentHazardOrMoney1_x_position = (int) ((int) d.getWidth()/15);
                //currentHazardOrMoney1_y_position = (int) ((int) d.getHeight()/3);
                ImageIcon currentHazardOrMoney_1 = new ImageIcon(path_of_hazard_1);
                //currentHazardOrMoney1w = currentHazardOrMoney1.getIconWidth();
                //currentHazardOrMoney1h = currentHazardOrMoney1.getIconHeight();
                currentHazardOrMoney_1.setImage(currentHazardOrMoney_1.getImage()
                        .getScaledInstance(currentHazardOrMoney1w, currentHazardOrMoney1h, Image.SCALE_DEFAULT));
                currentHazardOrMoney1_image = currentHazardOrMoney_1.getImage();
            }
            if(!"".equals(path_of_hazard_2)) {
                //currentHazardOrMoney2_x_position = (int) ((int) d.getWidth()/15)+130;
                //currentHazardOrMoney2_y_position = (int) ((int) d.getHeight()/3);
                ImageIcon currentHazardOrMoney_2 = new ImageIcon(path_of_hazard_2);
                //currentHazardOrMoney2w = currentHazardOrMoney2.getIconWidth();
                //currentHazardOrMoney2h = currentHazardOrMoney2.getIconHeight();
                currentHazardOrMoney_2.setImage(currentHazardOrMoney_2.getImage()
                        .getScaledInstance(currentHazardOrMoney2w, currentHazardOrMoney2h, Image.SCALE_DEFAULT));
                currentHazardOrMoney2_image = currentHazardOrMoney_2.getImage();
            }
            if(!"".equals(path_of_hazard_3)) {
                //currentHazardOrMoney3_x_position = (int) ((int) d.getWidth()/15)+250;
                //currentHazardOrMoney3_y_position = (int) ((int) d.getHeight()/3);
                ImageIcon currentHazardOrMoney_3 = new ImageIcon(path_of_hazard_3);
                //currentHazardOrMoney3w = currentHazardOrMoney3.getIconWidth();
                //currentHazardOrMoney3h = currentHazardOrMoney3.getIconHeight();
                currentHazardOrMoney_3.setImage(currentHazardOrMoney_3.getImage()
                        .getScaledInstance(currentHazardOrMoney3w, currentHazardOrMoney3h, Image.SCALE_DEFAULT));
                currentHazardOrMoney3_image = currentHazardOrMoney_3.getImage();
            }
            if(!"".equals(path_of_hazard_4)) {
                //currentHazardOrMoney4_x_position = (int) ((int) d.getWidth()/15)+395;
                //currentHazardOrMoney4_y_position = (int) ((int) d.getHeight()/3);
                ImageIcon currentHazardOrMoney_4 = new ImageIcon(path_of_hazard_4);
                //currentHazardOrMoney4w = currentHazardOrMoney4.getIconWidth();
                //currentHazardOrMoney4h = currentHazardOrMoney4.getIconHeight();
                currentHazardOrMoney_4.setImage(currentHazardOrMoney_4.getImage()
                        .getScaledInstance(currentHazardOrMoney4w, currentHazardOrMoney4h, Image.SCALE_DEFAULT));
                currentHazardOrMoney4_image = currentHazardOrMoney_4.getImage();
            }

            String onesToDraw_path = "res/numbers" + String.valueOf(ones) + ".png";
            String tensToDraw_path = "res/numbers" + String.valueOf(tens) + ".png";
            String hundredsToDraw_path = "res/numbers" + String.valueOf(hundreds) + ".png";
            String thousandsToDraw_path = "res/numbers" + String.valueOf(thousands) + ".png";
            String tenThousandsToDraw_path = "res/numbers" + String.valueOf(tenThousands) + ".png";
            String hundredThousandsToDraw_path = "res/numbers" + String.valueOf(hundredThousands) + ".png";

            digitFromLeft1_x_position = (int) ((int) d.getWidth()/2);
            digitFromLeft1_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft1 = new ImageIcon(hundredThousandsToDraw_path);
            int digitFromLeft1w = digitFromLeft1.getIconWidth();
            int digitFromLeft1h = digitFromLeft1.getIconHeight();
            digitFromLeft1.setImage(digitFromLeft1.getImage().getScaledInstance(digitFromLeft1w, digitFromLeft1h, Image.SCALE_DEFAULT));
            digitFromLeft1image = digitFromLeft1.getImage();

            digitFromLeft2_x_position = (int) ((int) d.getWidth()/2) +45;
            digitFromLeft2_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft2 = new ImageIcon(tenThousandsToDraw_path);
            int digitFromLeft2w = digitFromLeft2.getIconWidth();
            int digitFromLeft2h = digitFromLeft2.getIconHeight();
            digitFromLeft2.setImage(digitFromLeft2.getImage().getScaledInstance(digitFromLeft2w, digitFromLeft2h, Image.SCALE_DEFAULT));
            digitFromLeft2image = digitFromLeft2.getImage();

            digitFromLeft3_x_position = (int) ((int) d.getWidth()/2) +90;
            digitFromLeft3_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft3 = new ImageIcon(thousandsToDraw_path);
            int digitFromLeft3w = digitFromLeft3.getIconWidth();
            int digitFromLeft3h = digitFromLeft3.getIconHeight();
            digitFromLeft3.setImage(digitFromLeft3.getImage().getScaledInstance(digitFromLeft3w, digitFromLeft3h, Image.SCALE_DEFAULT));
            digitFromLeft3image = digitFromLeft3.getImage();

            digitFromLeft4_x_position = (int) ((int) d.getWidth()/2) +135;
            digitFromLeft4_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft4 = new ImageIcon(hundredsToDraw_path);
            int digitFromLeft4w = digitFromLeft4.getIconWidth();
            int digitFromLeft4h = digitFromLeft4.getIconHeight();
            digitFromLeft4.setImage(digitFromLeft4.getImage().getScaledInstance(digitFromLeft4w, digitFromLeft4h, Image.SCALE_DEFAULT));
            digitFromLeft4image = digitFromLeft4.getImage();

            digitFromLeft5_x_position = (int) ((int) d.getWidth()/2) +180;
            digitFromLeft5_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft5 = new ImageIcon(tensToDraw_path);
            int digitFromLeft5w = digitFromLeft5.getIconWidth();
            int digitFromLeft5h = digitFromLeft5.getIconHeight();
            digitFromLeft5.setImage(digitFromLeft5.getImage().getScaledInstance(digitFromLeft5w, digitFromLeft5h, Image.SCALE_DEFAULT));
            digitFromLeft5image = digitFromLeft5.getImage();

            digitFromLeft6_x_position = (int) ((int) d.getWidth()/2) +225;
            digitFromLeft6_y_position = (int) ((int) d.getHeight()/1.27);
            ImageIcon digitFromLeft6 = new ImageIcon(onesToDraw_path);
            int digitFromLeft6w = digitFromLeft6.getIconWidth();
            int digitFromLeft6h = digitFromLeft6.getIconHeight();
            digitFromLeft6.setImage(digitFromLeft6.getImage().getScaledInstance(digitFromLeft6w, digitFromLeft6h, Image.SCALE_DEFAULT));
            digitFromLeft6image = digitFromLeft6.getImage();

            Thread HAZ1 = new Thread() {
                @Override
                /**
                 * Run ski track 1 thread.
                 */
                public void run() {
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    if(!gamePaused) {
                        if(HugoHiihto.currentStateAtTheLevel == 15 || HugoHiihto.currentStateAtTheLevel == 26) {
                            currentHazardOrMoney1w = 1;
                            currentHazardOrMoney1h = 1;
                            currentHazardOrMoney1_y_position = 8000;
                            currentHazardOrMoney1_x_position = 8000;
                        }
                        if(vanish4Faster && (HugoHiihto.currentStateAtTheLevel == 14 || HugoHiihto.currentStateAtTheLevel == 25)) {
                            currentHazardOrMoney1_y_position+=4;
                        }
                        if(HugoHiihto.currentStateAtTheLevel == 14 || HugoHiihto.currentStateAtTheLevel == 25) {
                            if(currentHazardOrMoney1_x_position < 310) {
                                currentHazardOrMoney1_x_position++;
                            }
                            else {
                                currentHazardOrMoney1_y_position+=3;
                            }
                            currentHazardOrMoney1w = 120;
                            currentHazardOrMoney1h = 120;
                            if(currentHazardOrMoney1_x_position < 180 || vanish4Faster) {
                                currentHazardOrMoney1_x_position++;
                            }
                        }
                        else {
                            if(System.currentTimeMillis()%3 == 0) {
                                currentHazardOrMoney1_x_position-=2;
                                currentHazardOrMoney1_y_position-=2;
                                currentHazardOrMoney1w++;
                                currentHazardOrMoney1h+=2;
                            }
                            //
                            if(currentHazardOrMoney1_y_position < 185) {
                                currentHazardOrMoney1_y_position+=2;
                            }
                            else if(currentHazardOrMoney1_y_position > y -25 && System.currentTimeMillis()%5 == 0) {
                                currentHazardOrMoney1_x_position--;
                                currentHazardOrMoney1_y_position++;
                            }
                            if(currentHazardOrMoney1_y_position > d.getHeight()-230) {
                                currentHazardOrMoney1_x_position = (int) (d.getWidth() + 1000);
                                currentHazardOrMoney1w = 1;
                                currentHazardOrMoney1h = 1;
                            }
                            if(currentHazardOrMoney1_y_position > d.getHeight()-230 ||
                                    currentHazardOrMoney1_x_position < 50 ||
                                    !HugoHiihto.tic) {
                                currentHazardOrMoney1_y_position = 8000;
                                //System.out.println("Off screen 1");
                                currentHazardOrMoney1w = 1;
                                currentHazardOrMoney1h = 1;
                            }
                        }
                        if(vanish4Faster) {
                            currentHazardOrMoney1_y_position+=3;
                            currentHazardOrMoney1_x_position-=5;
                            currentHazardOrMoney1w+=3;
                            currentHazardOrMoney1h+=3;
                        }
                    }
                }
            };
            if(!HAZ1.isAlive()) {
                HAZ1.start();
            }
            if(HugoHiihto.currentStateAtTheLevel > -2) {
                g.drawImage(currentHazardOrMoney1_image, currentHazardOrMoney1_x_position, currentHazardOrMoney1_y_position, this);
            }

            Thread HAZ2 = new Thread() {
                @Override
                /**
                 * Run ski track 2 thread.
                 */
                public void run() {
                    repaint();
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%3 == 0) {
                            currentHazardOrMoney2_y_position++;
                            currentHazardOrMoney2w++;
                            currentHazardOrMoney2h+=2;
                        }
                        //
                        if(currentHazardOrMoney2_y_position < 175) {
                            currentHazardOrMoney2_y_position++;
                        }
                        if(System.currentTimeMillis()%3 == 0 && currentHazardOrMoney2_x_position > 192) {
                            currentHazardOrMoney2_x_position--;
                        }
                        if(currentHazardOrMoney2_y_position > d.getHeight()-230) {
                            currentHazardOrMoney2_x_position = (int) (d.getWidth() + 1000);
                            currentHazardOrMoney2w = 1;
                            currentHazardOrMoney2h = 1;
                        }
                        if(currentHazardOrMoney2_y_position > d.getHeight()-230 ||
                                !HugoHiihto.tic) {
                            //System.out.println("Off screen 2");
                            currentHazardOrMoney2_y_position = 8000;
                            currentHazardOrMoney2w = 1;
                            currentHazardOrMoney2h = 1;
                            repaint();
                        }
                        if(currentHazardOrMoney2_y_position > y +30 && System.currentTimeMillis()%5 == 0) {
                            currentHazardOrMoney2_y_position++;
                        }
                        if(vanish4Faster) {
                            currentHazardOrMoney2_y_position+=3;
                            currentHazardOrMoney2_x_position--;
                            currentHazardOrMoney2w+=3;
                            currentHazardOrMoney2h+=3;
                        }
                    }
                }
            };
            if(!HAZ2.isAlive()) {
                HAZ2.start();
            }
            if(HugoHiihto.currentStateAtTheLevel > -2) {
                g.drawImage(currentHazardOrMoney2_image, currentHazardOrMoney2_x_position, currentHazardOrMoney2_y_position, this);
            }

            Thread HAZ3 = new Thread() {
                @Override
                /**
                 * Run ski track 3 thread.
                 */
                public void run() {
                    repaint();
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%3 == 0) {
                            currentHazardOrMoney3_y_position++;
                            currentHazardOrMoney3w++;
                            currentHazardOrMoney3h+=2;
                        }
                        //
                        if(currentHazardOrMoney3_y_position < 175) {
                            currentHazardOrMoney3_y_position++;
                        }
                        if(System.currentTimeMillis()%3 == 0  && currentHazardOrMoney3_x_position < 340) {
                            currentHazardOrMoney3_x_position++;
                        }
                        if(currentHazardOrMoney3_y_position > d.getHeight()-230) {
                            currentHazardOrMoney3_x_position = (int) (d.getWidth() + 1000);
                            currentHazardOrMoney3w = 1;
                            currentHazardOrMoney3h = 1;
                        }
                        if(currentHazardOrMoney3_y_position > d.getHeight()-230 ||
                                !HugoHiihto.tic) {
                            //System.out.println("Off screen 3");
                            currentHazardOrMoney3_y_position = 8000;
                            currentHazardOrMoney3w = 1;
                            currentHazardOrMoney3h = 1;
                            repaint();
                        }
                        if(currentHazardOrMoney3_y_position > y +30  && System.currentTimeMillis()%5 == 0) {
                            currentHazardOrMoney3_y_position++;
                        }
                        if(vanish4Faster) {
                            currentHazardOrMoney3_y_position+=3;
                            currentHazardOrMoney3w+=3;
                            currentHazardOrMoney3h+=3;
                        }
                    }
                }
            };
            if(!HAZ3.isAlive()) {
                HAZ3.start();
            }
            if(HugoHiihto.currentStateAtTheLevel > -2) {
                g.drawImage(currentHazardOrMoney3_image, currentHazardOrMoney3_x_position, currentHazardOrMoney3_y_position, this);
            }

            Thread HAZ4 = new Thread() {
                @Override
                /**
                 * Run ski track 4 thread.
                 */
                public void run() {
                    repaint();
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    if(!gamePaused) {
                        if(System.currentTimeMillis()%3 == 0) {
                            currentHazardOrMoney4_x_position+=2;
                            currentHazardOrMoney4_y_position++;
                            currentHazardOrMoney4w++;
                            currentHazardOrMoney4h+=2;
                        }
                        //
                        if(currentHazardOrMoney4_y_position < 165) {
                            currentHazardOrMoney4_y_position++;
                        }
                        if(currentHazardOrMoney4_y_position > d.getHeight()-230) {
                            currentHazardOrMoney4_x_position = (int) (d.getWidth() + 1000);
                            currentHazardOrMoney4w = 1;
                            currentHazardOrMoney4h = 1;
                        }
                        if(currentHazardOrMoney4_y_position > d.getHeight()-230 ||
                                !HugoHiihto.tic) {
                            //System.out.println("Off screen 4");
                            currentHazardOrMoney4_y_position = 8000;
                            currentHazardOrMoney4w = 1;
                            currentHazardOrMoney4h = 1;
                            repaint();
                        }
                        if(currentHazardOrMoney4_y_position > y +30  && System.currentTimeMillis()%5 == 0) {
                            currentHazardOrMoney4_y_position++;
                        }
                        if(vanish4Faster) {
                            currentHazardOrMoney4_y_position+=3;
                            currentHazardOrMoney4_x_position+=3;
                            currentHazardOrMoney4w+=3;
                            currentHazardOrMoney4h+=3;
                        }
                    }
                }
            };
            if(!HAZ4.isAlive()) {
                HAZ4.start();
            }
            if(HugoHiihto.currentStateAtTheLevel > -2) {
                g.drawImage(currentHazardOrMoney4_image, currentHazardOrMoney4_x_position, currentHazardOrMoney4_y_position, this);
            }

            g.drawImage(scorebar, scorebar_x_position, scorebar_y_position, this);

            if(hundredThousandsVisible)
                g.drawImage(digitFromLeft1image, digitFromLeft1_x_position, digitFromLeft1_y_position, this);
            if(tenThousandsVisible)
                g.drawImage(digitFromLeft2image, digitFromLeft2_x_position, digitFromLeft2_y_position, this);
            if(thousandsVisible)
                g.drawImage(digitFromLeft3image, digitFromLeft3_x_position, digitFromLeft3_y_position, this);
            if(hundredsVisible)
                g.drawImage(digitFromLeft4image, digitFromLeft4_x_position, digitFromLeft4_y_position, this);
            if(tensVisible)
                g.drawImage(digitFromLeft5image, digitFromLeft5_x_position, digitFromLeft5_y_position, this);
            if(onesVisible)
                g.drawImage(digitFromLeft6image, digitFromLeft6_x_position, digitFromLeft6_y_position, this);

            repaint();

            if((double)number_of_lives > 1.5) {
                g.drawImage(hugolife1, hugolife1_x_position, hugolife1_y_position, this);
            }
            if((double)number_of_lives > 2.5) {
                g.drawImage(hugolife2, hugolife2_x_position, hugolife2_y_position, this);
            }
            if((double)number_of_lives > 3.5) {
                g.drawImage(hugolife3, hugolife3_x_position, hugolife3_y_position, this);
            }

            if(currentGrid < 2) { // Hugo ski animation
                Thread GRIDS01 = new Thread() {
                    @Override
                    /**
                     * Run Hugo L thread.
                     */
                    public void run() {
                        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                        int target = 55;
                        if(currentGrid == 0) {
                            target = 55;
                        }
                        if(currentGrid == 1) {
                            target = 55 + (maxW/3);
                        }
                        if(x > target) {
                            x-=6;
                            repaint();
                        }
                        if(x < target) {
                            x+=6;
                            repaint();
                        }
                    }
                };
                if(!GRIDS01.isAlive()) {
                    GRIDS01.start();
                }
                if(currentGrid == 0) {
                    g.drawImage(sprite_L2, x, y, this);
                }
                else {
                    g.drawImage(sprite_L, x, y, this);
                }
            }
            else {
                Thread GRIDS23 = new Thread() {
                    @Override
                    /**
                     * Run Hugo R thread.
                     */
                    public void run() {
                        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                        int target = 55;
                        if(currentGrid == 2) {
                            target = 55 + (maxW/3)*2;
                        }
                        if(currentGrid == 3) {
                            target = 55 + (maxW/3)*3;
                        }
                        if(x > target) {
                            x-=6;
                            repaint();
                        }
                        if(x < target) {
                            x+=6; // 6 is the speed of changing grid
                            repaint();
                        }
                    }
                };
                if(!GRIDS23.isAlive()) {
                    GRIDS23.start();
                }
                if(currentGrid == 3) {
                    g.drawImage(sprite_R2, x, y, this);
                }
                else {
                    g.drawImage(sprite_R, x, y, this);
                }
            }

            // Order matters,
            if(gamePaused) { // pause should be written last because it should always be on top of everything.
                if(pausedWithEnter) {
                    g.drawImage(pause, pause_x_position, pause_y_position, this);
                }
            }

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();

            }
        }
        if((double)game_state > 3.9 && (double)game_state < 4.1) {
            super.paintComponent(g);

            g.drawImage(bgCave, cave_x, cave_y, this); // cave image is based on the sledge Hugo game, a classic winter game

            g.drawImage(asterisk, asterisk_x_position, asterisk_y_position, this);
            g.drawImage(bell, bell_x_position, bell_y_position, this);
            g.drawImage(clock, clock_x_position, clock_y_position, this);
            g.drawImage(diamond, diamond_x_position, diamond_y_position, this);
            g.drawImage(hashtag, hashtag_x_position, hashtag_y_position, this);
            g.drawImage(star, star_x_position, star_y_position, this);

            if(currentlyAllCorrect) {
                g.drawImage(u1b, u1b_x_position, u1b_y_position, this);
                g.drawImage(u2b, u2b_x_position, u2b_y_position, this);
                g.drawImage(u3b, u3b_x_position, u3b_y_position, this);
            }

            if(secondPhase && currentlyAllCorrect) {
                if( thingsToRemember.charAt(0) == 'A' || // if caps, correct
                        thingsToRemember.charAt(0) == 'B' ||
                        thingsToRemember.charAt(0) == 'C' ||
                        thingsToRemember.charAt(0) == 'D' ||
                        thingsToRemember.charAt(0) == 'H' ||
                        thingsToRemember.charAt(0) == 'S') {
                    g.drawImage(u1w, u1b_x_position, u1b_y_position, this);
                }
                if( thingsToRemember.charAt(1) == 'A' ||
                        thingsToRemember.charAt(1) == 'B' ||
                        thingsToRemember.charAt(1) == 'C' ||
                        thingsToRemember.charAt(1) == 'D' ||
                        thingsToRemember.charAt(1) == 'H' ||
                        thingsToRemember.charAt(1) == 'S') {
                    g.drawImage(u2w, u2b_x_position, u2b_y_position, this);
                }
                if( thingsToRemember.charAt(2) == 'A' ||
                        thingsToRemember.charAt(2) == 'B' ||
                        thingsToRemember.charAt(2) == 'C' ||
                        thingsToRemember.charAt(2) == 'D' ||
                        thingsToRemember.charAt(2) == 'H' ||
                        thingsToRemember.charAt(2) == 'S') {
                    g.drawImage(u3w, u3b_x_position, u3b_y_position, this);
                }

                g.drawImage(d1b, d1b_x_position, d1b_y_position, this);
                g.drawImage(d2b, d2b_x_position, d2b_y_position, this);
                g.drawImage(d3b, d3b_x_position, d3b_y_position, this);

                if(allCorrectInTheEnd) {
                    if( thingsToRemember.charAt(3) == 'A' || // if caps, correct
                            thingsToRemember.charAt(3) == 'B' ||
                            thingsToRemember.charAt(3) == 'C' ||
                            thingsToRemember.charAt(3) == 'D' ||
                            thingsToRemember.charAt(3) == 'H' ||
                            thingsToRemember.charAt(3) == 'S') {
                        g.drawImage(d1w, d1b_x_position, d1b_y_position, this);
                    }
                    if( thingsToRemember.charAt(4) == 'A' ||
                            thingsToRemember.charAt(4) == 'B' ||
                            thingsToRemember.charAt(4) == 'C' ||
                            thingsToRemember.charAt(4) == 'D' ||
                            thingsToRemember.charAt(4) == 'H' ||
                            thingsToRemember.charAt(4) == 'S') {
                        g.drawImage(d2w, d2b_x_position, d2b_y_position, this);
                    }
                    if( thingsToRemember.charAt(5) == 'A' ||
                            thingsToRemember.charAt(5) == 'B' ||
                            thingsToRemember.charAt(5) == 'C' ||
                            thingsToRemember.charAt(5) == 'D' ||
                            thingsToRemember.charAt(5) == 'H' ||
                            thingsToRemember.charAt(5) == 'S') {
                        g.drawImage(d3w, d3b_x_position, d3b_y_position, this);
                    }
                    video = 5;
                    nextState = 6;
                }
            }
            if(!currentlyAllCorrect) {
                video = 6;
                nextState = 6;
            }
            repaint();

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();
            }
        }

        if((double)game_state > 4.9 && (double)game_state < 5.1) {
            super.paintComponent(g);

            cheatBackflip180 = false;
            key12 = false; // Even if something else was pressed when inputting the 12 numbers, it does not matter.
            key11 = false; // You may press for example "9700  2  4954 4744" where 2 is an unnecessary extra press.
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

            String onesToDraw_path = "res/numbers" + String.valueOf(ones) + ".png";
            String tensToDraw_path = "res/numbers" + String.valueOf(tens) + ".png";
            String hundredsToDraw_path = "res/numbers" + String.valueOf(hundreds) + ".png";
            String thousandsToDraw_path = "res/numbers" + String.valueOf(thousands) + ".png";
            String tenThousandsToDraw_path = "res/numbers" + String.valueOf(tenThousands) + ".png";
            String hundredThousandsToDraw_path = "res/numbers" + String.valueOf(hundredThousands) + ".png";

            digitFromLeft1_x_position = (int) ((int) d.getWidth()/11);
            digitFromLeft1_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft1 = new ImageIcon(hundredThousandsToDraw_path);
            int digitFromLeft1w = digitFromLeft1.getIconWidth();
            int digitFromLeft1h = digitFromLeft1.getIconHeight();
            digitFromLeft1.setImage(digitFromLeft1.getImage().getScaledInstance(
                    digitFromLeft1w, digitFromLeft1h, Image.SCALE_DEFAULT));
            digitFromLeft1image = digitFromLeft1.getImage();

            digitFromLeft2_x_position = (int) ((int) d.getWidth()/11) +50;
            digitFromLeft2_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft2 = new ImageIcon(tenThousandsToDraw_path);
            int digitFromLeft2w = digitFromLeft2.getIconWidth();
            int digitFromLeft2h = digitFromLeft2.getIconHeight();
            digitFromLeft2.setImage(digitFromLeft2.getImage().getScaledInstance(
                    digitFromLeft2w, digitFromLeft2h, Image.SCALE_DEFAULT));
            digitFromLeft2image = digitFromLeft2.getImage();

            digitFromLeft3_x_position = (int) ((int) d.getWidth()/11) +100;
            digitFromLeft3_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft3 = new ImageIcon(thousandsToDraw_path);
            int digitFromLeft3w = digitFromLeft3.getIconWidth();
            int digitFromLeft3h = digitFromLeft3.getIconHeight();
            digitFromLeft3.setImage(digitFromLeft3.getImage().getScaledInstance(
                    digitFromLeft3w, digitFromLeft3h, Image.SCALE_DEFAULT));
            digitFromLeft3image = digitFromLeft3.getImage();

            digitFromLeft4_x_position = (int) ((int) d.getWidth()/11) +150;
            digitFromLeft4_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft4 = new ImageIcon(hundredsToDraw_path);
            int digitFromLeft4w = digitFromLeft4.getIconWidth();
            int digitFromLeft4h = digitFromLeft4.getIconHeight();
            digitFromLeft4.setImage(digitFromLeft4.getImage().getScaledInstance(
                    digitFromLeft4w, digitFromLeft4h, Image.SCALE_DEFAULT));
            digitFromLeft4image = digitFromLeft4.getImage();

            digitFromLeft5_x_position = (int) ((int) d.getWidth()/11) +200;
            digitFromLeft5_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft5 = new ImageIcon(tensToDraw_path);
            int digitFromLeft5w = digitFromLeft5.getIconWidth();
            int digitFromLeft5h = digitFromLeft5.getIconHeight();
            digitFromLeft5.setImage(digitFromLeft5.getImage().getScaledInstance(
                    digitFromLeft5w, digitFromLeft5h, Image.SCALE_DEFAULT));
            digitFromLeft5image = digitFromLeft5.getImage();

            digitFromLeft6_x_position = (int) ((int) d.getWidth()/11) +250;
            digitFromLeft6_y_position = (int) ((int) d.getHeight()/2.2);
            ImageIcon digitFromLeft6 = new ImageIcon(onesToDraw_path);
            int digitFromLeft6w = digitFromLeft6.getIconWidth();
            int digitFromLeft6h = digitFromLeft6.getIconHeight();
            digitFromLeft6.setImage(digitFromLeft6.getImage().getScaledInstance(
                    digitFromLeft6w, digitFromLeft6h, Image.SCALE_DEFAULT));
            digitFromLeft6image = digitFromLeft6.getImage();

            g.drawImage(scoreBGR, -10, -18, null);
            repaint();

            if(pulled_rope_1) {
                ImageIcon r1_icon = new ImageIcon("res/rope1good.png");
                int r1_iconw = r1_icon.getIconWidth();
                int r1_iconh = r1_icon.getIconHeight();
                r1_icon.setImage(r1_icon.getImage().getScaledInstance(r1_iconw-30, r1_iconh-30, Image.SCALE_DEFAULT));
                r1 = r1_icon.getImage();
                g.drawImage(r1, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
            }
            if(pulled_rope_2 == true || (pulled_rope_1 == false && pulled_rope_2 == false && pulled_rope_3 == false)) {
                ImageIcon r2_icon = new ImageIcon("res/rope2bad.png");
                int r2_iconw = r2_icon.getIconWidth();
                int r2_iconh = r2_icon.getIconHeight();
                r2_icon.setImage(r2_icon.getImage().getScaledInstance(r2_iconw-30, r2_iconh-30, Image.SCALE_DEFAULT));
                r2 = r2_icon.getImage();
                g.drawImage(r2, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
            }
            if(pulled_rope_3) {
                ImageIcon r3_icon = new ImageIcon("res/rope3best.png");
                int r3_iconw = r3_icon.getIconWidth();
                int r3_iconh = r3_icon.getIconHeight();
                r3_icon.setImage(r3_icon.getImage().getScaledInstance(r3_iconw-30, r3_iconh-30, Image.SCALE_DEFAULT));
                r3 = r3_icon.getImage();
                g.drawImage(r3, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
            }

            if(hundredThousandsVisible)
                g.drawImage(digitFromLeft1image, digitFromLeft1_x_position, digitFromLeft1_y_position, this);
            if(tenThousandsVisible)
                g.drawImage(digitFromLeft2image, digitFromLeft2_x_position, digitFromLeft2_y_position, this);
            if(thousandsVisible)
                g.drawImage(digitFromLeft3image, digitFromLeft3_x_position, digitFromLeft3_y_position, this);
            if(hundredsVisible)
                g.drawImage(digitFromLeft4image, digitFromLeft4_x_position, digitFromLeft4_y_position, this);
            if(tensVisible)
                g.drawImage(digitFromLeft5image, digitFromLeft5_x_position, digitFromLeft5_y_position, this);
            if(onesVisible)
                g.drawImage(digitFromLeft6image, digitFromLeft6_x_position, digitFromLeft6_y_position, this);

            repaint();

            if(nextState != game_state) {
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                constructFrames(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                repaint();
            }
        }
        if((double)game_state >= 5.1) {
            // when moving from state 2 straight back to state 2, a workaround, show 2 videos in a row both with sound
            nextState = 2;
            System.out.println("------ State change from " + game_state + " to " + nextState);
            game_state = nextState;
            //super.paintComponent(g);
            constructFrames(game_state);
        }
    }
    */ // End of commented out paintComponent method


    /**
     * The main method. The program execution starts here.
     * Hugo Ski Game for Java, PC video game, designed for laptops and desktops.
     *
     * Tested with Microsoft Windows 11, 64 bit
     * Java developed by Oracle / Sun Microsystems
     * Recommended Java JDK version: 23 (or 17)
     *
     * Tuomas T. Hyvönen, 2024, Finland
     * Apache NetBeans 23, older versions used too when developing
     *
     * @param args
     */
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // lambda expression, https://www.w3schools.com/java/java_lambda.asp
            try {
                System.out.println("Hugo Skiing " + VERSION + ", GAME SPEED (ms): " + GAMESPEED + ", Finnish voices");
                // f = new JFrame("HUGO - SKIING"); // Commented out JFrame
                // f.setIconImage(new ImageIcon("res/favicon_corner.png").getImage()); // Commented out JFrame related
                // f.setSize(d); // Commented out JFrame related
                // f.setMaximumSize(d); // changing the dimension affects how the graphics will show up, do not edit 
                // f.setResizable(false); // Commented out JFrame related
                // f.setLocationRelativeTo(null); // Commented out JFrame related
                // f.setVisible(true); // Commented out JFrame related
                // f.setContentPane(new Game_Display()); // Commented out JFrame related
                // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Commented out JFrame related
            }
            catch (Exception e) {
                // f = new JFrame("SOME FILES ARE PROBABLY MISSING OR THEY HAVE BEEN RENAMED OR EDITED"); // Commented out JFrame
                // f.setSize(d); // Commented out JFrame related
                // f.setLocationRelativeTo(null); // Commented out JFrame related
                // f.setVisible(true); // Commented out JFrame related
                // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Commented out JFrame related
                System.out.println(e);
            }
        }/**
         * Running the JFrame. 
         */ /*);
    }
    */
} 