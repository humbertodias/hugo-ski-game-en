package hugohiihto.view;

import hugohiihto.model.MemoryPuzzle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * The six symbols and the 1-2-3 buttons at the cave entrance.
 */
public final class MemoryBoard {
    private static final int[] COLUMNS = {10, 130, 250};
    private static final int[] ROWS = {5, 150};

    private final Image[] symbols = new Image[6];
    private final int[] symbolX = new int[6];
    private final int[] symbolY = new int[6];
    private final Image[] idle = new Image[6];
    private final Image[] chosen = new Image[6];
    private final int[] buttonX = new int[6];
    private final int[] buttonY = new int[6];
    private Image background;

    public void load(Dimension window, String pattern) {
        int width = window.width - 10;
        int height = window.height - 35;
        background = Images.load("res/cave_entrance00.png", width, height);
        for (int i = 0; i < 6; i++) {
            int column = COLUMNS[i % 3];
            int row = ROWS[i / 3];
            int buttonFile = (i % 3) + 1;
            buttonX[i] = window.width / 6 + (column - 2);
            buttonY[i] = window.height / 19 + (row + 90);
            idle[i] = Images.load("res/num_select" + buttonFile + ".png");
            chosen[i] = Images.load("res/num_selected" + buttonFile + ".png");
            symbolX[i] = window.width / 6 + column;
            symbolY[i] = window.height / 19 + row;
            symbols[i] = Images.load(spriteFor(pattern.charAt(i)));
        }
    }

    public void draw(Graphics g, MemoryPuzzle puzzle, ImageObserver observer) {
        g.drawImage(background, 1, 1, observer);
        for (int i = 0; i < symbols.length; i++) {
            g.drawImage(symbols[i], symbolX[i], symbolY[i], observer);
        }
        if (!puzzle.isAllCorrect()) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            g.drawImage(idle[i], buttonX[i], buttonY[i], observer);
        }
        if (!puzzle.isSecondPhase()) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            if (puzzle.correctAt(i)) {
                g.drawImage(chosen[i], buttonX[i], buttonY[i], observer);
            }
        }
        for (int i = 3; i < 6; i++) {
            g.drawImage(idle[i], buttonX[i], buttonY[i], observer);
        }
        if (!puzzle.isSolved()) {
            return;
        }
        for (int i = 3; i < 6; i++) {
            if (puzzle.correctAt(i)) {
                g.drawImage(chosen[i], buttonX[i], buttonY[i], observer);
            }
        }
    }

    private static String spriteFor(char symbol) {
        return switch (Character.toUpperCase(symbol)) {
            case 'A' -> "res/remember_A_asterisk.png";
            case 'B' -> "res/remember_B_bell.png";
            case 'C' -> "res/remember_C_clock.png";
            case 'D' -> "res/remember_D_diamond.png";
            case 'H' -> "res/remember_H_hash.png";
            default -> "res/remember_S_star.png";
        };
    }
}
