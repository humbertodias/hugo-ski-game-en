package hugohiihto.model;

/**
 * The four lanes in front of Hugo, plus the flags that pause or fling them off screen.
 */
public final class SkiField {
    private final LaneObject[] lanes = {new LaneObject(), new LaneObject(), new LaneObject(), new LaneObject()};
    private boolean hazardsHeld;
    private boolean vanishFaster;

    public LaneObject lane(int index) {
        return lanes[index];
    }

    public boolean hazardsHeld() {
        return hazardsHeld;
    }

    public void setHazardsHeld(boolean hazardsHeld) {
        this.hazardsHeld = hazardsHeld;
    }

    public boolean vanishFaster() {
        return vanishFaster;
    }

    public void setVanishFaster(boolean vanishFaster) {
        this.vanishFaster = vanishFaster;
    }

    public void show(String row) {
        for (int i = 0; i < 4; i++) {
            char symbol = row.charAt(i);
            if (isTrackSymbol(symbol)) {
                lanes[i].setSymbol(String.valueOf(symbol));
            }
        }
    }

    public void resetApproach(int width, int height, int hugoY, boolean beat) {
        hazardsHeld = false;
        if (!beat) {
            return;
        }
        lanes[0].place(width / 3 + 35, (int) (height / 3.0), 1, 1);
        lanes[1].place(width / 3 + 58, (int) (height / 3.1), 1, 1);
        lanes[2].place(width / 3 + 88, (int) (height / 3.1), 1, 1);
        lanes[3].place(width / 3 + 130, (int) (height / 3.1), 1, 1);
        for (LaneObject lane : lanes) {
            if (hugoY > 0 && lane.y() > hugoY) {
                lane.move(4000, 4000);
            }
        }
    }

    public void parkBeforeStart() {
        for (LaneObject lane : lanes) {
            lane.setY(7000);
            lane.setX(4000);
        }
        lanes[0].setWidth(1);
        lanes[0].setHeight(1);
    }

    public void collectFrom(int laneIndex) {
        for (LaneObject lane : lanes) {
            lane.move(0, 4);
        }
        lanes[laneIndex].move(0, 400);
    }

    public void dropAll(int dy) {
        for (LaneObject lane : lanes) {
            lane.move(0, dy);
        }
    }

    private static boolean isTrackSymbol(char symbol) {
        return symbol == 'E' || symbol == 'M' || symbol == '8' || symbol == 'o'
                || symbol == 'Q' || symbol == 'B' || symbol == '1' || symbol == '2'
                || symbol == 'S' || symbol == 'F';
    }
}
