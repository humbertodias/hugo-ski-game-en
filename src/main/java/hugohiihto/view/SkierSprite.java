package hugohiihto.view;

/**
 * Hugo sliding between the four tracks.
 */
public final class SkierSprite {
    private int x = 55;
    private int y;

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void place(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void kick(GameSurface surface) {
        Thread thread = new Thread(() -> {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            int span = (surface.screenWidth() - 220) / 3;
            int target = 55 + span * surface.lane();
            if (x > target) {
                x -= 6;
                surface.repaintSurface();
            }
            if (x < target) {
                x += 6;
                surface.repaintSurface();
            }
        });
        thread.start();
    }
}
