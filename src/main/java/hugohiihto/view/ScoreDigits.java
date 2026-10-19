package hugohiihto.view;

import hugohiihto.model.Score;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * Draws the six score digits from the left, skipping places that are still hidden.
 */
public final class ScoreDigits {
    private ScoreDigits() {
    }

    public static Image[] load(Score score) {
        Image[] digits = new Image[6];
        for (int fromLeft = 0; fromLeft < 6; fromLeft++) {
            int place = 5 - fromLeft;
            digits[fromLeft] = Images.load("res/numbers" + score.digit(place) + ".png");
        }
        return digits;
    }

    public static void draw(Graphics g, Score score, Image[] digits, int originX, int originY, int step,
            ImageObserver observer) {
        for (int fromLeft = 0; fromLeft < 6; fromLeft++) {
            int place = 5 - fromLeft;
            if (score.visible(place)) {
                g.drawImage(digits[fromLeft], originX + step * fromLeft, originY, observer);
            }
        }
    }
}
