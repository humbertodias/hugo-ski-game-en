package hugohiihto.view;

import hugohiihto.model.MemoryPuzzle;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Picks the picture for a track symbol, including the two scrolls Hugo must remember.
 */
public final class TrackSprites {
    private TrackSprites() {
    }

    public static String path(String symbol, MemoryPuzzle memory) {
        return switch (symbol) {
            case "M" -> "res/money.png";
            case "8" -> "res/enemy_snowman.png";
            case "o" -> "res/enemy_snowball.png";
            case "Q" -> "res/enemy_bomb.png";
            case "B" -> "res/enemy_beaver_masi.png";
            case "1" -> remember(memory, 0, 3);
            case "2" -> remember(memory, 3, 6);
            default -> "";
        };
    }

    private static final Map<String, Image> CACHE = new HashMap<>();

    /**
     * The source picture, already loaded. The caller scales it while drawing.
     * A scroll occupies the whole slope, so the other three lanes stay blank.
     */
    public static Image sprite(String symbol, MemoryPuzzle memory, boolean hiddenByScroll) {
        if (hiddenByScroll || symbol == null || symbol.isEmpty()) {
            return null;
        }
        String path = path(symbol, memory);
        if (path.isEmpty()) {
            return null;
        }
        return CACHE.computeIfAbsent(path, Images::load);
    }

    private static String remember(MemoryPuzzle memory, int from, int until) {
        String path = "";
        for (int i = from; i < until; i++) {
            if (memory.correctAt(i)) {
                path = switch (memory.pattern().charAt(i)) {
                    case 'A' -> "res/remember_a_asterisk.png";
                    case 'B' -> "res/remember_b_bell.png";
                    case 'C' -> "res/remember_c_clock.png";
                    case 'D' -> "res/remember_d_diamond.png";
                    case 'H' -> "res/remember_h_hash.png";
                    case 'S' -> "res/remember_s_star.png";
                    default -> path;
                };
            }
        }
        return path;
    }
}
