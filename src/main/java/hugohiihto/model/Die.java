package hugohiihto.model;

/**
 * Inclusive-exclusive random integer, matching the original {@code Math.random} range.
 */
public final class Die {
    private Die() {
    }

    public static int roll(int minInclusive, int maxExclusive) {
        return (int) ((Math.random() * (maxExclusive - minInclusive)) + minInclusive);
    }
}
