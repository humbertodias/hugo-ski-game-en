package hugohiihto.model;

import java.util.Arrays;

/**
 * Six-digit score. Each place carries into the next, and the leading zeros stay hidden
 * until that place has been used.
 */
public final class Score {
    private final int[] digits = new int[6];
    private final boolean[] visible = new boolean[6];
    private boolean maxed;

    public Score() {
        reset();
    }

    public void reset() {
        Arrays.fill(digits, 0);
        Arrays.fill(visible, false);
        visible[0] = true;
        maxed = false;
    }

    public int digit(int place) {
        return digits[place];
    }

    public boolean visible(int place) {
        return visible[place];
    }

    public boolean maxed() {
        return maxed;
    }

    public void addOnes() {
        add(0);
    }

    public void addTens() {
        add(1);
    }

    public void addHundreds() {
        add(2);
    }

    public void addThousands() {
        add(3);
    }

    public void addTenThousands() {
        add(4);
    }

    public void addHundredThousands() {
        add(5);
    }

    public void awardMoneyBag(boolean cheat) {
        if (cheat) {
            addTenThousands();
            for (int i = 0; i < 8; i++) {
                addThousands();
            }
            return;
        }
        addOnes();
        addTens();
        addHundreds();
        addThousands();
        if (Math.random() < 0.4) {
            addHundreds();
        }
        if (Math.random() < 0.6) {
            addTens();
            addOnes();
            addOnes();
        }
    }

    public void awardFinish(int lives) {
        addTenThousands();
        if (lives >= 1) {
            addTenThousands();
            addTenThousands();
            if (lives >= 2) {
                addTenThousands();
                addTenThousands();
                if (lives >= 3) {
                    addTenThousands();
                    addTenThousands();
                    if (lives >= 4) {
                        addTenThousands();
                    }
                }
            }
        }
    }

    /** Good ending, historically 1004 points before carry. */
    public void awardGoodRope() {
        addThousands();
        addOnes();
        addOnes();
        addOnes();
        addOnes();
    }

    /** Best ending, historically 2026 points before carry. */
    public void awardBestRope() {
        addThousands();
        addThousands();
        addTens();
        addTens();
        addOnes();
        addOnes();
        addOnes();
        addOnes();
        addOnes();
        addOnes();
    }

    private void add(int place) {
        for (int i = 0; i <= place; i++) {
            visible[i] = true;
        }
        if (digits[place] < 9 && digits[place] > -1 && !maxed) {
            digits[place]++;
            return;
        }
        if (place == 5) {
            maxed = true;
            System.out.println("THE PLAYER GOT A MILLION POINTS");
        } else {
            add(place + 1);
            digits[place] = 0;
        }
        if (maxed) {
            Arrays.fill(digits, 9);
        }
    }
}
