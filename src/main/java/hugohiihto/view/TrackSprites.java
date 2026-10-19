package hugohiihto.view;

import hugohiihto.model.LaneObject;
import hugohiihto.model.MemoryPuzzle;
import hugohiihto.model.SkiField;
import java.awt.Image;

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

    public static Image[] load(SkiField slope, MemoryPuzzle memory) {
        String first = slope.lane(0).symbol();
        boolean scroll = "1".equals(first) || "2".equals(first);
        Image[] images = new Image[4];
        for (int i = 0; i < 4; i++) {
            if (scroll && i > 0) {
                continue;
            }
            LaneObject lane = slope.lane(i);
            String path = path(lane.symbol(), memory);
            if (path.isEmpty() || "E".equals(lane.symbol()) || "S".equals(lane.symbol()) || "F".equals(lane.symbol())) {
                continue;
            }
            images[i] = Images.load(path, lane.width(), lane.height());
        }
        return images;
    }

    private static String remember(MemoryPuzzle memory, int from, int until) {
        String path = "";
        for (int i = from; i < until; i++) {
            if (memory.correctAt(i)) {
                path = switch (memory.pattern().charAt(i)) {
                    case 'A' -> "res/remember_A_asterisk.png";
                    case 'B' -> "res/remember_B_bell.png";
                    case 'C' -> "res/remember_C_clock.png";
                    case 'D' -> "res/remember_D_diamond.png";
                    case 'H' -> "res/remember_H_hash.png";
                    case 'S' -> "res/remember_S_star.png";
                    default -> path;
                };
            }
        }
        return path;
    }
}
