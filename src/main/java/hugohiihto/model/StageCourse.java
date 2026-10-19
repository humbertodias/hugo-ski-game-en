package hugohiihto.model;

import java.util.Arrays;

/**
 * The 71 rows Hugo skis through. Each row is four track symbols.
 */
public final class StageCourse {
    private final String[] rows;

    private StageCourse(String[] rows) {
        this.rows = rows;
    }

    public static StageCourse generate(boolean cheat) {
        StageCourse course = new StageCourse(layOut(cheat));
        System.out.println(Arrays.toString(course.rows));
        return course;
    }

    public int length() {
        return rows.length;
    }

    public String row(int index) {
        return rows[index];
    }

    public void consume(int index) {
        if (!"FFFF".equals(rows[index])) {
            rows[index] = "EEEE";
        }
    }

    private static String[] layOut(boolean cheat) {
        String[] rows = new String[71];
        int[] hazards = rollHazards();
        for (int i = 0; i < 70; i++) {
            rows[i] = textFor(hazards[i]);
        }
        rows[39] = "SSSS";
        if (cheat) {
            rows[0] = "SSSS";
            for (int i = 1; i < 4; i++) {
                rows[i] = "QBo8";
            }
            for (int i = 4; i < 15; i++) {
                rows[i] = "EMME";
            }
            for (int i = 17; i < 26; i++) {
                rows[i] = "EMME";
            }
            for (int i = 28; i < 63; i++) {
                rows[i] = "EMME";
            }
            for (int i = 63; i < 70; i++) {
                rows[i] = "FFFF";
            }
        }
        rows[15] = "1111";
        rows[16] = "EEEE";
        rows[26] = "2222";
        rows[27] = "EEEE";
        rows[70] = "FFFF";
        return rows;
    }

    private static int[] rollHazards() {
        int[] hazards = new int[71];
        int previous = 8;
        hazards[0] = 8;
        for (int i = 1; i < 40; i++) {
            int random = Die.roll(0, 8);
            while (previous == random) {
                random = Die.roll(0, 8);
            }
            hazards[i] = random;
            previous = hazards[i];
        }
        hazards[40] = 8;
        hazards[41] = 8;
        for (int i = 42; i < hazards.length; i++) {
            int random = Die.roll(5, 19);
            while (previous == random) {
                random = Die.roll(5, 19);
            }
            hazards[i] = random;
            previous = hazards[i];
        }
        for (int i = 8; i < 40; i++) {
            if (i % 2 == 0) {
                hazards[i] = 8;
            }
            if (i == 14 || i == 15 || i == 16 || i == 17 || i == 25 || i == 26 || i == 27 || i == 28) {
                hazards[i] = 8;
            }
        }
        System.out.println(Arrays.toString(hazards));
        return hazards;
    }

    private static String textFor(int hazard) {
        if (hazard <= 0) {
            return "EEoE";
        }
        return switch (hazard) {
            case 1 -> "ME8E";
            case 2 -> "EM8E";
            case 3 -> "EEME";
            case 4 -> "E8EM";
            case 5 -> "8EEE";
            case 6 -> "EEE8";
            case 7 -> "EoEE";
            case 8 -> "EEEE";
            case 9 -> "QQEQ";
            case 10 -> "QEQQ";
            case 11 -> "EQQE";
            case 12 -> "QEMQ";
            case 13 -> "BMEE";
            case 14 -> "oBEE";
            case 15 -> "EEBo";
            case 16 -> "QEEB";
            case 17 -> "EoQQ";
            default -> "QQoE";
        };
    }
}
