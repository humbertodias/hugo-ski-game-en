package hugohiihto.model;

/**
 * Two groups of three symbols. A capital letter is the correct choice in that column.
 */
public final class MemoryPuzzle {
    private final String pattern;
    private boolean secondPhase;
    private boolean allCorrect = true;
    private boolean solved;

    private MemoryPuzzle(String pattern) {
        this.pattern = pattern;
    }

    public static MemoryPuzzle random() {
        return new MemoryPuzzle(patternFor(Die.roll(0, 16)));
    }

    public static MemoryPuzzle fixed(String pattern) {
        return new MemoryPuzzle(pattern);
    }

    public String pattern() {
        return pattern;
    }

    public boolean isSecondPhase() {
        return secondPhase;
    }

    public boolean isAllCorrect() {
        return allCorrect;
    }

    public boolean isSolved() {
        return solved;
    }

    public boolean correctAt(int index) {
        char symbol = pattern.charAt(index);
        return symbol == 'A' || symbol == 'B' || symbol == 'C'
                || symbol == 'D' || symbol == 'H' || symbol == 'S';
    }

    /**
     * @param column 0, 1 or 2
     * @return whether the answer is still correct after this press
     */
    public boolean choose(int column) {
        int index = secondPhase ? column + 3 : column;
        if (!correctAt(index)) {
            allCorrect = false;
        }
        if (!secondPhase) {
            secondPhase = true;
        } else if (allCorrect) {
            solved = true;
            System.out.println("Both correct!");
        }
        return allCorrect;
    }

    static String patternFor(int problem) {
        if (problem <= 1) {
            return "AcdBhs100100";
        }
        if (problem == 2) {
            return "AdsbCh100010";
        }
        if (problem == 3) {
            return "AcbshD100001";
        }
        if (problem == 4) {
            return "bAcHds010100";
        }
        if (problem == 5) {
            return "cAbhSd010010";
        }
        if (problem == 6) {
            return "aBdhsC010001";
        }
        if (problem == 7) {
            return "chBDas001100";
        }
        if (problem == 8) {
            return "acBsHd001010";
        }
        if (problem == 9) {
            return "haBcdS001001";
        }
        if (problem == 10) {
            return "CabDsh100100";
        }
        if (problem == 11) {
            return "CbdaHs100010";
        }
        if (problem == 12) {
            return "haCdbS001001";
        }
        if (problem == 13) {
            return "DasHbc100100";
        }
        if (problem == 14) {
            return "aDbcSh010010";
        }
        return "HbaSdc100100";
    }
}
