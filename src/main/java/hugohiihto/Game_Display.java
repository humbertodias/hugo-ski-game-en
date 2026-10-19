package hugohiihto;

import hugohiihto.Resources;
import hugohiihto.audio.SoundBoard;
import hugohiihto.input.KeyboardController;
import hugohiihto.model.GameSession;
import hugohiihto.model.MemoryPuzzle;
import hugohiihto.model.Score;
import hugohiihto.view.Cutscene;
import hugohiihto.view.GameSurface;
import hugohiihto.view.Images;
import hugohiihto.view.LaneMotion;
import hugohiihto.view.MemoryBoard;
import hugohiihto.view.ScoreDigits;
import hugohiihto.view.SkierSprite;
import hugohiihto.view.SlopeScenery;
import hugohiihto.view.TrackSprites;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Player;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The window. It draws and listens; the run itself lives in {@link GameSession}.
 */
public final class Game_Display extends JPanel implements GameSurface {
    static final String VERSION = "1.1.3.ENG";
    static final Dimension WINDOW = new Dimension(630, 500);

    private final GameSession session = new GameSession();
    private final SoundBoard audio = new SoundBoard();
    private final SlopeScenery scenery = new SlopeScenery();
    private final MemoryBoard memoryBoard = new MemoryBoard();
    private final SkierSprite skier = new SkierSprite();

    private int screen;
    private int nextScreen;
    private int video;
    private boolean useMp4;

    private Image intro;
    private Image title;
    private Image credits;
    private Image slope;
    private Image scoreBar;
    private Image lifeIcon;
    private Image pauseImage;
    private Image resultBackground;
    private Image skierLeftWide;
    private Image skierLeft;
    private Image skierRight;
    private Image skierRightWide;
    private Image videoFrame;

    public Game_Display() {
        addKeyListener(new KeyboardController(this));
        setFocusable(true);
        constructFrames();
        flushVideoFrame();
    }

    @Override
    public GameSession session() {
        return session;
    }

    @Override
    public SoundBoard audio() {
        return audio;
    }

    @Override
    public int screen() {
        return screen;
    }

    @Override
    public int nextScreen() {
        return nextScreen;
    }

    @Override
    public void setNextScreen(int nextScreen) {
        this.nextScreen = nextScreen;
    }

    @Override
    public int video() {
        return video;
    }

    @Override
    public void setVideo(int video) {
        this.video = video;
    }

    @Override
    public void openCutscene(int video, int nextScreen) {
        this.video = video;
        this.nextScreen = nextScreen;
    }

    @Override
    public Player voice() {
        return audio.voice();
    }

    @Override
    public boolean paused() {
        return session.paused();
    }

    @Override
    public void setPaused(boolean paused) {
        session.setPaused(paused);
    }

    @Override
    public int lane() {
        return session.lane();
    }

    @Override
    public void setLane(int lane) {
        session.setLane(lane);
    }

    @Override
    public int skierX() {
        return skier.x();
    }

    @Override
    public int skierY() {
        return skier.y();
    }

    @Override
    public int screenWidth() {
        return WINDOW.width;
    }

    @Override
    public int screenHeight() {
        return WINDOW.height;
    }

    @Override
    public void exitGame() {
        System.exit(0);
    }

    @Override
    public void repaintSurface() {
        repaint();
    }

    @Override
    public void resetGame() {
        session.start(this, GameSession.SPEED_MS);
    }

    @Override
    public void preloadIntroFrames() {
        videoFrame = Images.load("res/scylla_intro_s.gif");
        videoFrame = Images.load("res/start_hoplaa_s.gif");
        if (videoFrame != null) {
            videoFrame.setAccelerationPriority(1f);
        }
    }

    @Override
    public void flushVideoFrame() {
        if (videoFrame != null) {
            videoFrame.flush();
            videoFrame = null;
        }
    }

    @Override
    public void constructFrames() {
        repaint();
        if (screen != nextScreen) {
            return;
        }
        if (screen == 0) {
            intro = Images.load("res/_the_very_1st_texts.png", WINDOW.width, WINDOW.height - 35);
        }
        if (screen == 1) {
            if (screen != 3 && nextScreen != 3) {
                setPaused(true);
            }
            if (session.progress() >= 71 && !session.isGameOver()) {
                credits = Images.load("res/credits_screen.png", WINDOW.width - 2, WINDOW.height - 37);
            } else {
                title = Images.load("res/title_screen.png", WINDOW.width - 15, WINDOW.height - 20);
            }
        }
        if (screen == 2 && !useMp4) {
            audio.playVoice(Resources.file(Cutscene.byId(video).audio()));
        }
        if (screen == 3) {
            loadSlope();
        }
        if (screen == 4) {
            memoryBoard.load(WINDOW, session.memory().pattern());
        }
        if (screen == 5) {
            resultBackground = Images.load("res/title_screen_nothing.png", WINDOW.width, WINDOW.height);
            flushVideoFrame();
        }
        flushVideoFrame();
        repaint();
    }

    private void loadSlope() {
        int skierHeight = WINDOW.height / 3;
        Image left = new ImageIcon(Resources.file("res/hugo_ski_l.gif").getPath()).getImage();
        skierLeftWide = left.getScaledInstance(110, skierHeight, Image.SCALE_DEFAULT);
        skierLeft = skierLeftWide.getScaledInstance(90, skierHeight, Image.SCALE_DEFAULT);
        Image right = new ImageIcon(Resources.file("res/hugo_ski_r.gif").getPath()).getImage();
        skierRight = right.getScaledInstance(90, skierHeight, Image.SCALE_DEFAULT);
        skierRightWide = skierRight.getScaledInstance(110, skierHeight, Image.SCALE_DEFAULT);
        slope = Images.load("res/background1.gif", WINDOW.width - 10, WINDOW.height - 35);
        skier.place(55, (int) ((int) WINDOW.getHeight() / 2.67));
        scenery.load(WINDOW);
        lifeIcon = Images.load("res/hugo_life.png");
        pauseImage = Images.load("res/pause.png");
        ImageIcon bar = new ImageIcon(Resources.file("res/score-life-bar.png").getPath());
        scoreBar = Images.load("res/score-life-bar.png", bar.getIconWidth() * 5, bar.getIconHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (screen == 0) {
            paintIntro(g);
        }
        if (screen == 1) {
            paintTitle(g);
        }
        if (screen == 2) {
            paintCutscene(g);
        }
        if (screen == 3) {
            paintSlope(g);
        }
        if (screen == 4) {
            paintMemory(g);
        }
        if (screen == 5) {
            paintResult(g);
        }
        if (screen >= 6) {
            nextScreen = 2;
            System.out.println("------ State change from " + screen + " to " + nextScreen);
            screen = nextScreen;
            constructFrames();
        }
    }

    private void paintIntro(Graphics g) {
        super.paintComponent(g);
        g.drawImage(intro, 0, 0, this);
        repaint();
        followScreenChange();
    }

    private void paintTitle(Graphics g) {
        super.paintComponent(g);
        if (session.progress() >= 71 && !session.isGameOver()) {
            g.drawImage(credits, 0, 0, this);
        } else {
            g.drawImage(title, 0, 0, this);
        }
        repaint();
        followScreenChange();
    }

    private void paintCutscene(Graphics g) {
        super.paintComponent(g);
        if (useMp4) {
            try {
                Desktop.getDesktop().open(Resources.file(Cutscene.byId(video).mp4()));
            } catch (IOException ex) {
                Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            videoFrame = new ImageIcon(Resources.file(Cutscene.byId(video).gif()).getPath()).getImage();
            super.paintComponent(g);
            videoFrame.setAccelerationPriority(1f);
            g.drawImage(videoFrame, 0, 0, WINDOW.width, WINDOW.height - 40, null);
            for (int i = 0; i < 30000; i++) {
                repaint();
            }
        }
        followScreenChange();
    }

    private void paintSlope(Graphics g) {
        super.paintComponent(g);
        g.drawImage(slope, 0, 0, null);
        scenery.draw(g, this, session.paused(), session.windToTheLeft(), WINDOW);

        Image[] digits = ScoreDigits.load(session.score());
        int digitX = WINDOW.width / 2;
        int digitY = (int) (WINDOW.height / 1.27);

        LaneMotion.kick(this);
        if (session.progress() > -2) {
            String lead = session.slope().lane(0).symbol();
            boolean scroll = "1".equals(lead) || "2".equals(lead);
            for (int i = 0; i < 4; i++) {
                var lane = session.slope().lane(i);
                Image sprite = TrackSprites.sprite(lane.symbol(), session.memory(), scroll && i > 0);
                if (sprite == null || lane.width() < 1 || lane.height() < 1) {
                    continue;
                }
                g.drawImage(sprite, lane.x(), lane.y(), lane.width(), lane.height(), this);
            }
        }
        g.drawImage(scoreBar, 0, (int) (WINDOW.height / 1.35), this);
        ScoreDigits.draw(g, session.score(), digits, digitX, digitY, 45, this);
        repaint();
        drawLives(g);
        drawSkier(g);
        if (session.paused() && session.pausedByPlayer()) {
            g.drawImage(pauseImage, WINDOW.width / 6, WINDOW.height / 3, this);
        }
        followScreenChange();
    }

    private void drawLives(Graphics g) {
        int y = (int) (WINDOW.height / 1.3);
        int x = WINDOW.width / 55;
        if (session.lives() > 1.5) {
            g.drawImage(lifeIcon, x, y, this);
        }
        if (session.lives() > 2.5) {
            g.drawImage(lifeIcon, x + 80, y, this);
        }
        if (session.lives() > 3.5) {
            g.drawImage(lifeIcon, x + 160, y, this);
        }
    }

    private void drawSkier(Graphics g) {
        skier.kick(this);
        Image frame = switch (session.lane()) {
            case 0 -> skierLeftWide;
            case 1 -> skierLeft;
            case 3 -> skierRightWide;
            default -> skierRight;
        };
        g.drawImage(frame, skier.x(), skier.y(), this);
    }

    private void paintMemory(Graphics g) {
        super.paintComponent(g);
        MemoryPuzzle puzzle = session.memory();
        memoryBoard.draw(g, puzzle, this);
        if (!puzzle.isAllCorrect()) {
            openCutscene(6, 6);
        }
        if (puzzle.isSolved()) {
            openCutscene(5, 6);
        }
        repaint();
        followScreenChange();
    }

    private void paintResult(Graphics g) {
        super.paintComponent(g);
        session.cheat().reset();
        Score score = session.score();
        Image[] digits = ScoreDigits.load(score);
        int originX = WINDOW.width / 11;
        int originY = (int) (WINDOW.height / 2.2);
        g.drawImage(resultBackground, -10, -18, null);
        repaint();
        if (session.ropeGood()) {
            g.drawImage(rope("res/rope1good.png"), originX - 40, originY + 70, this);
        }
        if (session.ropeBad() || (!session.ropeGood() && !session.ropeBad() && !session.ropeBest())) {
            g.drawImage(rope("res/rope2bad.png"), originX - 40, originY + 70, this);
        }
        if (session.ropeBest()) {
            g.drawImage(rope("res/rope3best.png"), originX - 40, originY + 70, this);
        }
        ScoreDigits.draw(g, score, digits, originX, originY, 50, this);
        repaint();
        followScreenChange();
    }

    private Image rope(String path) {
        ImageIcon icon = new ImageIcon(Resources.file(path).getPath());
        return Images.load(path, icon.getIconWidth() - 30, icon.getIconHeight() - 30);
    }

    private void followScreenChange() {
        if (nextScreen == screen) {
            return;
        }
        System.out.println("------ State change from " + screen + " to " + nextScreen);
        screen = nextScreen;
        constructFrames();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HUGO - SKIING");
            try {
                System.out.println("Hugo Skiing " + VERSION + ", GAME SPEED (ms): " + GameSession.SPEED_MS + ", Finnish voices");
                frame.setIconImage(new ImageIcon(Resources.file("res/favicon_corner.png").getPath()).getImage());
                frame.setSize(WINDOW);
                frame.setMaximumSize(WINDOW);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setContentPane(new Game_Display());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } catch (Exception e) {
                frame.setTitle("SOME FILES ARE PROBABLY MISSING OR THEY HAVE BEEN RENAMED OR EDITED");
                frame.setSize(WINDOW);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.out.println(e);
            }
        });
    }
}
