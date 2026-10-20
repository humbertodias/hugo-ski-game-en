package hugohiihto.view;

/**
 * The original slope moved once per paint, and that paint was slow.
 * A light paint repeats the same step so often that an enemy leaves the screen
 * before it can be seen. This clock keeps one step at that older pace.
 */
public final class SlopeClock {
    static final long FRAME_MS = 45;
    private static long lastFrame;

    private SlopeClock() {
    }

    public static boolean due() {
        long now = System.currentTimeMillis();
        if (now - lastFrame < FRAME_MS) {
            return false;
        }
        lastFrame = now;
        return true;
    }
}
