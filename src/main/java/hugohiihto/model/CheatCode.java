package hugohiihto.model;

/**
 * Title-screen sequence 9700 4954 4744.
 * The checks stay in the original order because one key can advance more than one step.
 */
public final class CheatCode {
    private final boolean[] step = new boolean[13];
    private boolean active;

    public void accept(int digit) {
        if (digit == 9 && step[5]) {
            step[6] = true;
        }
        if (digit == 9) {
            step[1] = true;
        }
        if (digit == 7 && step[9]) {
            step[10] = true;
        }
        if (digit == 7 && step[1]) {
            step[2] = true;
        }
        if (digit == 0 && step[3]) {
            step[4] = true;
        }
        if (digit == 0 && step[2]) {
            step[3] = true;
        }
        if (digit == 5 && step[6]) {
            step[7] = true;
        }
        if (digit == 4 && step[11]) {
            step[12] = true;
        }
        if (digit == 4 && step[10]) {
            step[11] = true;
        }
        if (digit == 4 && step[8]) {
            step[9] = true;
        }
        if (digit == 4 && step[7]) {
            step[8] = true;
        }
        if (digit == 4 && step[4]) {
            step[5] = true;
        }
        if (step[12]) {
            active = true;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void reset() {
        for (int i = 0; i < step.length; i++) {
            step[i] = false;
        }
        active = false;
    }
}
