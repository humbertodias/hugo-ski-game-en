package hugohiihto.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * Cloud and trees. Each paint starts a one-shot thread, which is how the original moved them.
 */
public final class SlopeScenery {
    private final Drifter cloud = new Drifter();
    private final Drifter[] trees = new Drifter[8];

    public void load(Dimension window) {
        cloud.image = Images.load("res/cloud.png");
        cloud.x = window.width / 3;
        cloud.y = window.height / 25;
        trees[0] = tree("res/trees2.png", window.width / 5 - 40, window.height / 17 + 20);
        trees[1] = tree("res/trees0.png", window.width / 5 - 40, window.height / 17 + 20);
        trees[2] = tree("res/trees1.png", window.width / 5 - 40, window.height / 17 + 20);
        trees[3] = tree("res/trees2.png", window.width / 2 + 70, window.height / 4 - 90);
        trees[4] = tree("res/trees0.png", window.width / 2 + 70, window.height / 4 - 90);
        trees[5] = tree("res/trees1.png", window.width / 2 + 70, window.height / 4 - 90);
        trees[6] = tree("res/trees2.png", window.width / 2 + 72, window.height / 4 - 92);
        trees[7] = tree("res/trees1.png", window.width / 5 - 42, window.height / 17 + 22);
    }

    public void draw(Graphics g, ImageObserver observer, boolean paused, boolean windToTheLeft, Dimension window) {
        cloud.kick(() -> cloud.drift(windToTheLeft, window));
        g.drawImage(cloud.image, cloud.x, cloud.y, observer);
        int[] limits = {-340, -400, -500, 640, 760, 800, 753, -574};
        int[] dx = {-2, -2, -2, 2, 2, 2, 2, -2};
        for (int i = 0; i < trees.length; i++) {
            Drifter tree = trees[i];
            int index = i;
            tree.kick(() -> {
                if (paused) {
                    return;
                }
                if (System.currentTimeMillis() % 2 == 0) {
                    tree.x += dx[index];
                    tree.y++;
                }
                if (dx[index] < 0 && tree.x < limits[index]) {
                    tree.x = resetX(index, window);
                    tree.y = resetY(index, window);
                }
                if (dx[index] > 0 && tree.x > limits[index]) {
                    tree.x = resetX(index, window);
                    tree.y = resetY(index, window);
                }
            });
            g.drawImage(tree.image, tree.x, tree.y, observer);
        }
    }

    private static int resetX(int index, Dimension window) {
        return switch (index) {
            case 0 -> window.width / 8 - 7;
            case 1 -> window.width / 4 - 4;
            case 2 -> window.width / 5 - 4;
            case 3, 4, 5 -> window.width / 2 + 40;
            case 6 -> window.width / 2 + 42;
            default -> window.width / 9;
        };
    }

    private static int resetY(int index, Dimension window) {
        return switch (index) {
            case 0 -> window.height / 12 + 20;
            case 1 -> window.height / 8 + 20;
            case 2 -> window.height / 17 + 20;
            case 3 -> window.height / 3 - 100;
            case 4, 5 -> window.height / 3 - 80;
            case 6 -> window.height / 3 - 82;
            default -> window.height / 7 + 32;
        };
    }

    private static Drifter tree(String path, int x, int y) {
        Drifter drifter = new Drifter();
        drifter.image = Images.load(path);
        drifter.x = x;
        drifter.y = y;
        return drifter;
    }

    private static final class Drifter {
        private Image image;
        private int x;
        private int y;

        private void kick(Runnable motion) {
            Thread thread = new Thread(() -> {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                motion.run();
            });
            thread.start();
        }

        private void drift(boolean windToTheLeft, Dimension window) {
            if (System.currentTimeMillis() % 19 == 0) {
                x += windToTheLeft ? -1 : 1;
            }
            if (x < -300 && windToTheLeft) {
                x = window.width;
            }
            if (x > 700 && !windToTheLeft) {
                x -= 1000;
            }
        }
    }
}
