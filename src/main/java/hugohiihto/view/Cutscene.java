package hugohiihto.view;

/**
 * Still pictures that play between the skiing, the cave and the score.
 */
public enum Cutscene {
    SCYLLA_INTRO(0, "scylla_intro"),
    START(1, "start_hoplaa"),
    BUTTON(2, "scylla_button_press"),
    ROPES(3, "scylla0"),
    REMEMBER_ASK(4, "remember2forKey_intro"),
    REMEMBER_WIN(5, "remember2forKey_win"),
    REMEMBER_FAIL(6, "remember2forKey_fail"),
    FINISH(7, "screentalk_finish_line"),
    WAKE(8, "screentalk_heraa_pahvi"),
    LAST_LIFE(9, "screentalk_viimeista_viedaan"),
    GAME_OVER(10, "screentalk_game_over"),
    ROPE_GOOD(11, "scylla1"),
    ROPE_BAD(12, "scylla2"),
    ROPE_BEST(13, "scylla3"),
    HIT_SNOWMAN(14, "loselife_snowman"),
    HIT_SNOWBALL(15, "loselife_snowball"),
    HIT_BOMB(16, "loselife_bomb"),
    HIT_BEAVER(17, "loselife_beaver");

    private final int id;
    private final String baseName;

    Cutscene(int id, String baseName) {
        this.id = id;
        this.baseName = baseName;
    }

    public static Cutscene byId(int id) {
        for (Cutscene cutscene : values()) {
            if (cutscene.id == id) {
                return cutscene;
            }
        }
        return SCYLLA_INTRO;
    }

    public String gif() {
        return "res/" + baseName + "_s.gif";
    }

    public String audio() {
        return "res/" + baseName + ".aiff";
    }

    public String mp4() {
        return "res/" + baseName + ".mp4";
    }
}
