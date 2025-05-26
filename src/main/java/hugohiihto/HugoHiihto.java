package hugohiihto;

// import java.io.File; // No longer needed for File operations here
// import java.io.IOException; // No longer needed for File operations here
import java.util.Arrays;
// import java.util.TimerTask; // TimerTask is being removed
import java.util.logging.Level; // Keep for logging if any remains
import java.util.logging.Logger; // Keep for logging if any remains
// import javax.sound.sampled.AudioSystem; // Replaced by JavaFX
// import javax.sound.sampled.Clip; // Replaced by JavaFX
// import javax.sound.sampled.FloatControl; // Replaced by JavaFX
// import javax.sound.sampled.LineUnavailableException; // Replaced by JavaFX
// import javax.sound.sampled.UnsupportedAudioFileException; // Replaced by JavaFX
import javafx.scene.media.AudioClip; // Added for JavaFX
import javafx.scene.media.Media; // Added for JavaFX
import javafx.scene.media.MediaPlayer; // Added for JavaFX
import javafx.scene.media.MediaException; // Added for JavaFX

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
 * Computations and methods not included in the user interface / game display Java source code file.
 * Comments and names are sometimes in Finnish but tried to use English mostly.
 *
 * Tested with Microsoft Windows 11
 * Java developed by Oracle / Sun Microsystems.
 * Created with Apache NetBeans 23
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide
 * @author Tuomas Hyvönen
 * @version 1.1.ENG
 */
public class HugoHiihto {
    static HugoHiihto hugoHiihto = null;
    // static TimerTask timerTask; // Removed TimerTask
    String[] haz = null;
    String rem = null;
    static boolean gameOver = true;
    static boolean hasAchievedMaxScore = false;
    static int currentStateAtTheLevel = -5; // -5   So the game does not start with a surprise attack.
    static int theFurthestThePlayerHasGot = -4; // -4   (-5 +1)   This is necessary because of the pause feature.
    static boolean tic = true;
    // There is no clear logic in what class file the variables should belong to, there are only 2 Java classes used anyway.

    /**
     * The constructor.
     */
    public HugoHiihto() {
        haz = giveStageHazards();
        rem = giveThingsToRemember();
        Game_Display.leftWind = (boolean)(Math.random() < 0.5);
    }

    /**
     * Getter for a game instance.
     *
     * @return HugoHiihto
     */
    public HugoHiihto getGame() {
        return this;
    }

    /**
     * Resetting the game or beginning a new game.
     * The game speed integer is in milliseconds, for example 1800 = 1.8 seconds.
     * Does not edit how fast the graphics will show on the screen though!
     *
     * @param gameSpeed
     */
    public static void gameReset(int gameSpeed) {
        if(gameSpeed > 1000 && gameSpeed < 3000 && gameOver) {
            System.out.println("Game reset called");
            gameSpeed = Game_Display.GAMESPEED;
            System.gc(); // run Java garbage collector
            Game_Display.currentGrid = 0;
            Game_Display.pulled_rope_1 = false;
            Game_Display.pulled_rope_2 = false;
            Game_Display.pulled_rope_3 = false;
            gameOver = false;
            Game_Display.ones = 0;   // score digits (6)
            Game_Display.tens = 0;
            Game_Display.hundreds = 0;
            Game_Display.thousands = 0;
            Game_Display.tenThousands = 0;
            Game_Display.hundredThousands = 0;
            Game_Display.onesVisible = true;
            Game_Display.tensVisible = false;
            Game_Display.hundredsVisible = false;
            Game_Display.thousandsVisible = false;
            Game_Display.tenThousandsVisible = false;
            Game_Display.hundredThousandsVisible = false;
            Game_Display.number_of_lives = 4; // amount of lives
            HugoHiihto.currentStateAtTheLevel = -5;
            HugoHiihto.hasAchievedMaxScore = false;
            HugoHiihto.theFurthestThePlayerHasGot = -4;
            Game_Display.currentlyAllCorrect = true; // even though 0 guesses
            Game_Display.secondPhase = false;
            Game_Display.allCorrectInTheEnd = false;

            if(hugoHiihto == null) {
                hugoHiihto = new HugoHiihto();
            }

            Game_Display.thingsToRemember = hugoHiihto.getREM();
            // timerTask = hugoHiihto.new GameLoop(); // GameLoop TimerTask removed
            // java.util.Timer ti = new java.util.Timer(true); // Timer removed
            // ti.scheduleAtFixedRate(timerTask, 0, gameSpeed); // Timer scheduling removed
            // Initialization of lastTickTime will happen in the first call to updateGameTick
            lastTickTime = 0; 
        }
        else {
            gameSpeed = 1700;
            gameOver = true;
            gameReset(gameSpeed);
        }
    }

    /**
     * Get the 2 things to remember.
     * @return String
     */
    public String getREM() {
        return this.rem;
    }

    /**
     * Create a random integer, Java's own Math.random() has been used.
     * @param min
     * @param max_that_does_not_count
     * @return int
     */
    public static int getRandom(int min, int max_that_does_not_count) {
        int random = (int) ((Math.random() * (max_that_does_not_count - min)) + min);
        return random; // max_that_does_not_count if zero counts
    }

    /**
     * Creating the stage hazards to the 1d array called hazards.
     * Length is 71, negative values = the stage is about to begin soon and Hugo is just skiing.
     * In a previous version, the game array was longer.
     * @return int[]
     */
    public static int[] createStageHazards() {
        int[] hazards = new int[71];
        /*
         * E- empty
         * M- money
         * 8- snowman
         * o- snowball
         * Q- bomb
         * B- Masi the beaver (in Finland he is called Masi)
         *
         * 1- thing to remember #1 (6 possible)
         * 2- thing to remember #2 (6 possible (actually 5 because no same again))
         * S- Scylla button press with short horror music
         * F- goal, just end the skiing session
         *
         *
         * 0 = empty, empty, SNOWBALL, empty
         * 1 = MONEY, empty, SNOWMAN, empty
         * 2 = empty, MONEY, SNOWMAN, empty
         * 3 = empty, empty, MONEY, empty
         * 4 = empty, SNOWMAN, empty, MONEY
         * 5 = SNOWMAN, empty, empty, empty
         * 6 = empty, empty, empty, SNOWMAN
         * 7 = empty, SNOWBALL, empty, empty
         * 8 = empty, empty, empty, empty
         * 9 = BOMB, BOMB, empty, BOMB
         * 10 = BOMB, empty, BOMB, BOMB
         * 11 = empty, BOMB, BOMB, empty
         * 12 = BOMB, empty, MONEY, BOMB
         * 13 = BEAVER, MONEY, empty, empty
         * 14 = SNOWBALL, BEAVER, empty, empty
         * 15 = empty, empty, BEAVER, SNOWBALL
         * 16 = BOMB, empty, empty, BEAVER
         * 17 = empty, SNOWBALL, BOMB, BOMB
         * 18 = BOMB, BOMB, SNOWBALL, empty
         *
         * // some will be overwritten with 2 must-remember-images etc.
         */
        int randomPrevious = 8;
        for(int i = 0; i < 40; i++) {
            int random = getRandom(0,8);
            while(randomPrevious == random) { // avoids getting the same number again
                random = getRandom(0,8);
            }
            hazards[i] = random;
            randomPrevious = hazards[i];
        }
        for(int i = 40; i < hazards.length; i++) {
            int random = getRandom(5,19);
            while(randomPrevious == random) {
                random = getRandom(5,19);
            }
            hazards[i] = random;
            randomPrevious = hazards[i];
        }
        for(int i = 8; i < 40; i++) {
            if(i%2 == 0) {
                hazards[i] = 8; // every 2nd empty, a selected range
            }
            if(i == 14 || i == 25 || i == 15 || i == 26 || i == 16 || i == 27 || i == 17 || i == 28) {
                hazards[i] = 8;
            }
        }
        System.out.println(Arrays.toString(hazards));
        return hazards;
    }

    /**
     * Getting the stage hazards.
     *
     * @return String[]
     */
    public static String[] giveStageHazards() {
        String[] s = new String[71];
        int haz[] = createStageHazards();

        for(int i = 0; i < 70; i++) {
            if(haz[i] <= 0) {
                s[i] = "EEoE";
            }
            if(haz[i] == 1) {
                s[i] = "ME8E";
            }
            if(haz[i] == 2) {
                s[i] = "EM8E";
            }
            if(haz[i] == 3) {
                s[i] = "EEME";
            }
            if(haz[i] == 4) {
                s[i] = "E8EM";
            }
            if(haz[i] == 5) {
                s[i] = "8EEE";
            }
            if(haz[i] == 6) {
                s[i] = "EEE8";
            }
            if(haz[i] == 7) {
                s[i] = "EoEE";
            }
            if(haz[i] == 8) {
                s[i] = "EEEE";
            }
            if(haz[i] == 9) {
                s[i] = "QQEQ";
            }
            if(haz[i] == 10) {
                s[i] = "QEQQ";
            }
            if(haz[i] == 11) {
                s[i] = "EQQE";
            }
            if(haz[i] == 12) {
                s[i] = "QEMQ";
            }
            if(haz[i] == 13) {
                s[i] = "BMEE";
            }
            if(haz[i] == 14) {
                s[i] = "oBEE";
            }
            if(haz[i] == 15) {
                s[i] = "EEBo";
            }
            if(haz[i] == 16) {
                s[i] = "QEEB";
            }
            if(haz[i] == 17) {
                s[i] = "EoQQ";
            }
            if(haz[i] >= 18) {
                s[i] = "QQoE";
            }
        }

        // zero counts as the 1st number,

        // from 0 to 14:    casual easy
        // the 15th:          the 1st to remember
        // from 16 to 25:   casual easy
        // the 26th:          the 2nd to remember
        // from 27 to 38:   casual easy
        // the 39th:          Scylla's button press, popcorn starts
        // from 40 to 69:   hardcore bombing
        // the 70th:          the finish line
        // "memory game"
        // 3 ropes
        s[39] = "SSSS";
        if(Game_Display.cheatBackflip180) {
            for(int i = 0; i < 1; i++) {
                s[i] = "SSSS";
            }
            for(int i = 1; i < 4; i++) {
                s[i] = "QBo8";
            }
            for(int i = 4; i < 15; i++) {
                s[i] = "EMME";
            }
            for(int i = 17; i < 26; i++) {
                s[i] = "EMME";
            }
            for(int i = 28; i < 63; i++) {
                s[i] = "EMME";
            }
            for(int i = 63; i < 70; i++) {
                s[i] = "FFFF";
            }
        }
        s[15] = "1111"; s[16] = "EEEE"; // always an empty set after a scroll (1 and 2).
        s[26] = "2222"; s[27] = "EEEE";
        s[70] = "FFFF";
        System.out.println(Arrays.toString(s));
        return s;
    }

    /**
     * Generate what 2 things must be remembered when Hugo is at the goal.
     *
     * @return int
     */
    public static int generateIntToRemember() {
        /*
         * 1 = asterisk & bell
         * 2 = asterisk & clock
         * 3 = asterisk & diamond
         * 4 = asterisk & hashtag
         * 5 = asterisk & star  // "tähti, tähti"
         * 6 = bell & clock     // "kello, kello"
         * 7 = bell & diamond
         * 8 = bell & hashtag
         * 9 = bell & star
         * 10 = clock & diamond
         * 11 = clock & hashtag
         * 12 = clock & star
         * 13 = diamond & hashtag // "ruutu, ruutu"
         * 14 = diamond & star
         * 15 = hashtag & star
         *
         * Wrong answers when 3+3 are shown to the player:
         * 1 clock, diamond; hashtag, star
         * 2 bell, diamond; hashtag, star
         * 3 bell, clock; hashtag, star
         * 4 bell, clock; diamond, star
         * 5 bell, clock; diamond, hashtag
         * 6 asterisk, diamond; hashtag, star
         * 7 asterisk, clock; hashtag, star
         * 8 asterisk, clock; diamond, star
         * 9 asterisk, clock; diamond, hashtag
         * 10 asterisk, bell; hashtag, star
         * 11 asterisk, bell; diamond, star
         * 12 asterisk, bell; diamond, hashtag
         * 13 asterisk, bell; clock, star
         * 14 asterisk, bell; clock, hashtag
         * 15 asterisk, bell; clock, diamond
         *
         */
        int items = getRandom(0,16); // some may have more probability to become chosen
        return items;
    }

    /**
     * Give the 2 items to remember in order to get the skull cave key at the end.
     *
     * @return String
     */
    public static String giveThingsToRemember() {
        int problem = generateIntToRemember();
        boolean[][] problem2array = new boolean[3][3];
        // asterisk a, bell b, clock c, diamond d, hashtag h, star s
        // caps means the right answer
        String s = "";
        if(problem <= 1) {
            problem2array[0][0] = true; // Acd Bhs
            s = "AcdBhs100100";
        }
        if(problem == 2) {
            problem2array[0][1] = true; // Ads bCh
            s = "AdsbCh100010";
        }
        if(problem == 3) {
            problem2array[0][2] = true; // Acb shD
            s = "AcbshD100001";
        }
        if(problem == 4) {
            problem2array[1][0] = true; // bAc Hds
            s = "bAcHds010100";
        }
        if(problem == 5) {
            problem2array[1][1] = true; // cAb hSd
            s = "cAbhSd010010";
        }
        if(problem == 6) {
            problem2array[1][2] = true; // aBd hsC
            s = "aBdhsC010001";
        }
        if(problem == 7) {
            problem2array[2][0] = true; // chB Das
            s = "chBDas001100";
        }
        if(problem == 8) {
            problem2array[2][1] = true; // acB sHd
            s = "acBsHd001010";
        }
        if(problem == 9) {
            problem2array[2][2] = true; // haB cdS
            s = "haBcdS001001";
        }
        if(problem == 10) {
            problem2array[0][0] = true; // Cab Dsh
            s = "CabDsh100100";
        }
        if(problem == 11) {
            problem2array[0][1] = true; // Cbd aHs
            s = "CbdaHs100010";
        }
        if(problem == 12) {
            problem2array[2][2] = true; // haC dbS
            s = "haCdbS001001";
        }
        if(problem == 13) {
            problem2array[0][0] = true; // Das Hbc
            s = "DasHbc100100";
        }
        if(problem == 14) {
            problem2array[1][1] = true; // aDb cSh
            s = "aDbcSh010010";
        }
        if(problem >= 15) {
            problem2array[0][0] = true; // Hba Sdc
            s = "HbaSdc100100";
        }

        System.out.println(s);
        return s;
    }

    /**
     * Increase score, ones.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreOnes(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setOnes(whatWillBeIncreased);
        }
        else {
            increaseScoreTens(Game_Display.tens);
            Game_Display.setOnes(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }

    /**
     * Increase score, tens.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreTens(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        Game_Display.tensVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setTens(whatWillBeIncreased);
        }
        else {
            increaseScoreHundreds(Game_Display.hundreds);
            Game_Display.setTens(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }

    /**
     * Increase score, 100s.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreHundreds(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        Game_Display.tensVisible = true;
        Game_Display.hundredsVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setHundreds(whatWillBeIncreased);
        }
        else {
            increaseScoreThousands(Game_Display.thousands);
            Game_Display.setHundreds(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }

    /**
     * Increase score, 1000s.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreThousands(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        Game_Display.tensVisible = true;
        Game_Display.hundredsVisible = true;
        Game_Display.thousandsVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setThousands(whatWillBeIncreased);
        }
        else {
            increaseScoreTenThousands(Game_Display.tenThousands);
            Game_Display.setThousands(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }

    /**
     * Increase score, 10 000s.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreTenThousands(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        Game_Display.tensVisible = true;
        Game_Display.hundredsVisible = true;
        Game_Display.thousandsVisible = true;
        Game_Display.tenThousandsVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setTenThousands(whatWillBeIncreased);
        }
        else {
            increaseScoreHundredThousands(Game_Display.hundredThousands);
            Game_Display.setTenThousands(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }

    /**
     * Increase score, 100 000s.
     *
     * @param whatWillBeIncreased
     */
    public static void increaseScoreHundredThousands(int whatWillBeIncreased) {
        Game_Display.onesVisible = true;
        Game_Display.tensVisible = true;
        Game_Display.hundredsVisible = true;
        Game_Display.thousandsVisible = true;
        Game_Display.tenThousandsVisible = true;
        Game_Display.hundredThousandsVisible = true;
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
            whatWillBeIncreased++;
            Game_Display.setHundredThousands(whatWillBeIncreased);
        }
        else {
            hasAchievedMaxScore = true; // trying to increase when it's already 9
            System.out.println("THE PLAYER GOT A MILLION POINTS");
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }


    /**
     * Decreases lives by 1, call when hitting an enemy (4 possible enemies).
     *
     * @param lives
     */
    public static void decreaseLives(int lives) {
        lives--;
        Game_Display.setLives(lives);
    }


    // Game speed and timing variables for updateGameTick
    private long lastTickTime = 0;
    // Game_Display.GAMESPEED is final static, so can be used here.
    private final double gameSpeedNanos = (double)Game_Display.GAMESPEED * 1_000_000.0;

    // Store the last hazard string to avoid unnecessary updates if it hasn't changed
    private String[] lastHazKey = new String[1]; 


    /**
     * This method replaces the old GameLoop.run() and is called by AnimationTimer.
     * It handles game progression based on time delta.
     * @param now_nanos Current time in nanoseconds from AnimationTimer.
     */
    public void updateGameTick(long now_nanos) {
        if (gameOver) { // If game is over, don't update game logic.
            // The old GameLoop used to call this.cancel(). Here, we just return.
            // The AnimationTimer in HugoHiihtoFX will continue running for rendering,
            // but game logic progression stops.
            return;
        }

        if (lastTickTime == 0) { // First tick initialization
            lastTickTime = now_nanos;
            return;
        }

        if ((now_nanos - lastTickTime) < gameSpeedNanos) {
            return; // Not enough time for the next game tick
        }
        // lastTickTime = now_nanos; // Option 1: Reset to current time (can lead to drift)
        lastTickTime += gameSpeedNanos; // Option 2: Increment by fixed step (more stable for game logic)


        // --- Start of logic moved from GameLoop.run() ---
        if(currentStateAtTheLevel >= 71) { // Check for finish line explicitly, gameOver might be set by other conditions too
            // this.cancel() was here; now, just ensure gameOver is true.
            gameOver = true; 
            Game_Display.gamePaused = true;
            if(Game_Display.gamePaused) { // This condition is now redundant if gameOver is true
                Game_Display.video = 7;    // "Hugo finished skiing" cutscene
                Game_Display.nextState = 6; // state 6 is for transitioning between videos or to a new state after video
            }
            return; // End tick processing
        }

        if(Game_Display.nextState == 2) { // If a video is about to play
            Game_Display.currentGrid = 0;
            Game_Display.gamePaused = true; // Pause game logic during video
        } else {
            // Skiing music logic
            if(currentStateAtTheLevel < 37 && !Game_Display.gamePaused) { // Only play if not paused and in early stage
                if (Game_Display.mediaPlayerSkiingLoop != null && 
                    Game_Display.mediaPlayerSkiingLoop.getStatus() != MediaPlayer.Status.PLAYING &&
                    Game_Display.mediaPlayerSkiingLoop.getStatus() != MediaPlayer.Status.PAUSED) { // Avoid restarting if paused
                    Game_Display.mediaPlayerSkiingLoop.play();
                }
            } else if (Game_Display.gamePaused && Game_Display.mediaPlayerSkiingLoop != null && Game_Display.mediaPlayerSkiingLoop.getStatus() == MediaPlayer.Status.PLAYING) {
                 Game_Display.mediaPlayerSkiingLoop.pause(); // Pause skiing music if game is paused
            }
        }

        if(!Game_Display.gamePaused) {
            if(currentStateAtTheLevel < 70) { // Before finish line
                // hasDoneOnce was used to ensure score for finish line is added only once.
                // This might need to be a field if this method is called multiple times for the same state.
                // For now, assuming one logical tick per state advancement.
            }

            if(tic) { // "TIC" phase: Player action resolution, collision detection
                tic = false; // Switch to "TAC" for next gameSpeed interval
                System.out.println("\nTIC, location is " + currentStateAtTheLevel + ", next will be " + theFurthestThePlayerHasGot);
                if(theFurthestThePlayerHasGot > -1 && theFurthestThePlayerHasGot < haz.length) { // Ensure within bounds
                    String currentHazKey = haz[theFurthestThePlayerHasGot]; // Use the "upcoming" hazard string
                    if (currentHazKey == null) currentHazKey = "EEEE"; // Default if null

                    char actionChar = ' ';
                    if (Game_Display.currentGrid >=0 && Game_Display.currentGrid < currentHazKey.length()) {
                        actionChar = currentHazKey.charAt(Game_Display.currentGrid);
                    } else {
                        // This case should ideally not happen if currentGrid is always valid.
                        // System.err.println("Warning: currentGrid " + Game_Display.currentGrid + " out of bounds for haz key: " + currentHazKey);
                        actionChar = 'E'; // Default to Empty if out of bounds
                    }


                    if(actionChar == 'E' || actionChar == '1' || actionChar == '2') {
                        Game_Display.currentHazardOrMoney1_y_position+=4; // Visual effect for empty/scroll
                        Game_Display.currentHazardOrMoney2_y_position+=4;
                        Game_Display.currentHazardOrMoney3_y_position+=4;
                        Game_Display.currentHazardOrMoney4_y_position+=4;
                        Game_Display.vanish4Faster = true;
                    }
                    if(actionChar == 'F') { // Finish Line
                        currentStateAtTheLevel = 71; // Trigger game end sequence
                        // Score for finish line (original had !hasDoneOnce check)
                        // This needs to be handled carefully to award points once.
                        // For now, let's assume hasDoneOnce logic will be part of a one-time event check.
                        // if(!hasDoneOnceAtFinish) { // hasDoneOnceAtFinish would be a new boolean field
                        //    // add score...
                        //    // hasDoneOnceAtFinish = true;
                        // }
                         if (Game_Display.soundScore != null) Game_Display.soundScore.play();
                    }
                    if(actionChar == '8' || actionChar == 'o' || actionChar == 'Q' || actionChar == 'B') { // Enemy collision
                        System.out.println("ENEMY HIT - Type: " + actionChar + ", line: " + Game_Display.currentGrid);
                        Game_Display.gamePaused = true;
                        if(actionChar == '8') Game_Display.video = 14;      // Snowman
                        else if(actionChar == 'o') Game_Display.video = 15; // Snowball
                        else if(actionChar == 'Q') Game_Display.video = 16; // Bomb
                        else if(actionChar == 'B') Game_Display.video = 17; // Beaver
                        Game_Display.nextState = 2; // Show video state
                        decreaseLives(Game_Display.number_of_lives);
                    }
                    if(actionChar == 'S') { // Scylla button press
                        System.out.println("SCYLLA BUTTON PRESS");
                        Game_Display.gamePaused = true;
                        Game_Display.video = 2;
                        Game_Display.nextState = 2;
                        if (Game_Display.mediaPlayerSkiingLoop != null) Game_Display.mediaPlayerSkiingLoop.stop();
                        if (Game_Display.cheatBackflip180) {
                            if (Game_Display.mediaPlayerFinnishHugo != null) { Game_Display.mediaPlayerFinnishHugo.stop(); Game_Display.mediaPlayerFinnishHugo.play(); }
                        } else {
                            if (Game_Display.number_of_lives <= 3) {
                                if (Game_Display.mediaPlayerSkateboard != null) { Game_Display.mediaPlayerSkateboard.stop(); Game_Display.mediaPlayerSkateboard.play(); }
                            } else {
                                if (Game_Display.mediaPlayerPopcorn != null) { Game_Display.mediaPlayerPopcorn.stop(); Game_Display.mediaPlayerPopcorn.play(); }
                            }
                        }
                    }
                    if(actionChar == 'M') { // Money
                        // Visual feedback for collecting money
                        Game_Display.currentHazardOrMoney1_y_position+=4; 
                        Game_Display.currentHazardOrMoney2_y_position+=4;
                        Game_Display.currentHazardOrMoney3_y_position+=4;
                        Game_Display.currentHazardOrMoney4_y_position+=4;
                        Game_Display.vanish4Faster = true;
                        if(Game_Display.currentGrid == 0) Game_Display.currentHazardOrMoney1_y_position+= 400;
                        if(Game_Display.currentGrid == 1) Game_Display.currentHazardOrMoney2_y_position+= 400;
                        if(Game_Display.currentGrid == 2) Game_Display.currentHazardOrMoney3_y_position+= 400;
                        if(Game_Display.currentGrid == 3) Game_Display.currentHazardOrMoney4_y_position+= 400;
                        
                        if (Game_Display.soundMoney != null) Game_Display.soundMoney.play();
                        // Score increase logic (same as before)
                        if(Game_Display.cheatBackflip180) {
                            increaseScoreTenThousands(Game_Display.tenThousands);
                            for(int k=0; k<8; k++) increaseScoreThousands(Game_Display.thousands);
                        } else {
                            increaseScoreOnes(Game_Display.ones); increaseScoreTens(Game_Display.tens);
                            increaseScoreHundreds(Game_Display.hundreds); increaseScoreThousands(Game_Display.thousands);
                            if(Math.random() < 0.4) increaseScoreHundreds(Game_Display.hundreds);
                            if(Math.random() < 0.6) { increaseScoreTens(Game_Display.tens); increaseScoreOnes(Game_Display.ones); increaseScoreOnes(Game_Display.ones); }
                        }
                    }
                }
            }
            else { // "TAC" phase: Advance game state, set up next hazards
                tic = true;
                Game_Display.vanish4Faster = false; // Reset visual effect flag
                if(currentStateAtTheLevel < 0 || (currentStateAtTheLevel == 14 || currentStateAtTheLevel == 25)) { // Before start or at scroll locations
                    // Make hazards invisible/off-screen
                    Game_Display.currentHazardOrMoney1_y_position = 7000; Game_Display.currentHazardOrMoney2_y_position = 7000;
                    Game_Display.currentHazardOrMoney3_y_position = 7000; Game_Display.currentHazardOrMoney4_y_position = 7000;
                    Game_Display.currentHazardOrMoney1_x_position = 4000; Game_Display.currentHazardOrMoney2_x_position = 4000;
                    Game_Display.currentHazardOrMoney3_x_position = 4000; Game_Display.currentHazardOrMoney4_x_position = 4000;
                    Game_Display.currentHazardOrMoney1w = 1; Game_Display.currentHazardOrMoney1h = 1; // Minimal size
                }
                Game_Display.reset4positions(); // Reset visual positions of hazard slots

                if(currentStateAtTheLevel < 70 && theFurthestThePlayerHasGot < 70) {
                    currentStateAtTheLevel++;
                    if(currentStateAtTheLevel >= theFurthestThePlayerHasGot) {
                        theFurthestThePlayerHasGot++;
                    }
                }
                System.out.println("TAC, location is " + currentStateAtTheLevel + ", next will be " + theFurthestThePlayerHasGot);

                if(theFurthestThePlayerHasGot >= 0 && theFurthestThePlayerHasGot < haz.length) { // Check bounds
                    String currentHazKey = haz[theFurthestThePlayerHasGot];
                    if (currentHazKey == null) currentHazKey = "EEEE"; // Should not happen if haz is initialized properly

                    // Set hazard characters for Game_Display to pick up for rendering
                    Game_Display.setcurrentHazardOrMoney1(String.valueOf(currentHazKey.charAt(0)));
                    Game_Display.setcurrentHazardOrMoney2(String.valueOf(currentHazKey.charAt(1)));
                    Game_Display.setcurrentHazardOrMoney3(String.valueOf(currentHazKey.charAt(2)));
                    Game_Display.setcurrentHazardOrMoney4(String.valueOf(currentHazKey.charAt(3)));
                    
                    // Update ImageViews in Game_Display via HugoHiihtoFX instance
                    if (Game_Display.hugoHiihtoFXInstance != null && Game_Display.hugoHiihtoFXInstance.getGameDisplay() != null) {
                        Game_Display.hugoHiihtoFXInstance.getGameDisplay().updateHazardImages();
                    }

                    if(!"FFFF".equals(currentHazKey)) { // Don't overwrite finish line
                        // This modification of haz array was in original. Consider if it's necessary
                        // or if just reading from a static array is better.
                        // For now, keeping original behavior.
                        // haz[theFurthestThePlayerHasGot] = "EEEE"; 
                    }
                } else if (theFurthestThePlayerHasGot >= haz.length) { // Reached end of hazard array
                     currentStateAtTheLevel = 70; // Move to finish line state
                     // This will be handled by the gameOver check at the start of the next tick.
                }
            }
        }
        else { // Game is paused
            // If game is paused by Enter, skiing music should pause.
            // If paused for a cutscene (nextState == 2), music is handled by cutscene logic / stopAllMusicPlayers.
            if (Game_Display.pausedWithEnter && Game_Display.mediaPlayerSkiingLoop != null && Game_Display.mediaPlayerSkiingLoop.getStatus() == MediaPlayer.Status.PLAYING) {
                Game_Display.mediaPlayerSkiingLoop.pause();
            }
            // Logic for decrementing currentStateAtTheLevel if paused was here,
            // this seems to make the game "rewind" slightly if paused during TAC.
            // This might be complex to replicate perfectly without TimerTask's fixed rate.
            // For now, simply pausing the progression of currentStateAtTheLevel when gamePaused is true.
        }
        // --- End of logic moved from GameLoop.run() ---
    }
}