package hugohiihto.view;

import hugohiihto.model.LaneObject;
import hugohiihto.model.SkiField;

/**
 * One step of each lane, using the original deltas.
 * The caller decides when a step is due so a repaint flood cannot skip the approach.
 */
public final class LaneMotion {
    private static int stepCount;

    private LaneMotion() {
    }

    public static void kick(GameSurface surface, boolean step) {
        if (!step) {
            return;
        }
        stepCount++;
        SkiField slope = surface.session().slope();
        first(surface, slope);
        second(surface, slope);
        third(surface, slope);
        fourth(surface, slope);
    }

    /** The original grew on about one paint in three. The paced step keeps that ratio. */
    private static boolean growThisStep() {
        return stepCount % 3 == 0;
    }

    private static boolean nudgeThisStep() {
        return stepCount % 5 == 0;
    }

    private static void first(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(0);
        int progress = surface.session().progress();
        if (progress == 14 || progress == 25) {
            if (lane.x() > 0 && lane.y() > 0) {
                lane.move(-2, -1);
            }
            lane.setHeight(150);
            lane.setWidth(150);
            return;
        }
        boolean held = slope.hazardsHeld();
        if (growThisStep() && !held) {
            lane.move(-2, -2);
            lane.grow(1, 2);
        }
        if (lane.y() < 185 && !slope.hazardsHeld()) {
            lane.move(0, 2);
        } else if (lane.y() > surface.skierY() - 25 && nudgeThisStep() && !slope.hazardsHeld()) {
            lane.move(-1, 1);
        }
        if (lane.y() > surface.screenHeight() - 262 || lane.x() < 40 || !surface.session().beat()) {
            slope.setHazardsHeld(true);
        }
        if (lane.y() > surface.screenHeight() - 250 || !surface.session().beat()) {
            lane.setY(8000);
            lane.setWidth(1);
            lane.setHeight(1);
        }
        fling(slope, lane, -5);
    }

    private static void second(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(1);
        if (growThisStep() && !slope.hazardsHeld()) {
            lane.move(0, 1);
            lane.grow(1, 2);
        }
        if (lane.y() < 175 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        if (growThisStep() && lane.x() > 192 && !slope.hazardsHeld()) {
            lane.move(-1, 0);
        }
        if (lane.y() > surface.screenHeight() - 262) {
            slope.setHazardsHeld(true);
        }
        if (lane.y() > surface.screenHeight() - 230 || !surface.session().beat()) {
            lane.setY(8000);
            lane.setWidth(1);
            lane.setHeight(1);
        }
        if (lane.y() > surface.skierY() + 30 && nudgeThisStep() && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        fling(slope, lane, -1);
    }

    private static void third(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(2);
        if (growThisStep() && !slope.hazardsHeld()) {
            lane.move(0, 1);
            lane.grow(1, 2);
        }
        if (lane.y() < 175 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        if (growThisStep() && lane.x() < 340 && !slope.hazardsHeld()) {
            lane.move(1, 0);
        }
        if (lane.y() > surface.screenHeight() - 262) {
            slope.setHazardsHeld(true);
        }
        if (lane.y() > surface.screenHeight() - 230 || !surface.session().beat()) {
            lane.setY(8000);
            lane.setWidth(1);
            lane.setHeight(1);
        }
        if (lane.y() > surface.skierY() + 30 && nudgeThisStep() && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        fling(slope, lane, 0);
    }

    private static void fourth(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(3);
        if (growThisStep() && !slope.hazardsHeld()) {
            lane.move(2, 1);
            lane.grow(1, 2);
        }
        if (lane.y() < 165 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        if (lane.y() > surface.screenHeight() - 262) {
            slope.setHazardsHeld(true);
        }
        if (lane.y() > surface.screenHeight() - 230 || !surface.session().beat()) {
            lane.setY(8000);
            lane.setWidth(1);
            lane.setHeight(1);
        }
        if (lane.y() > surface.skierY() + 30 && nudgeThisStep() && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        fling(slope, lane, 3);
    }

    private static void fling(SkiField slope, LaneObject lane, int dx) {
        if (!slope.vanishFaster()) {
            return;
        }
        slope.setHazardsHeld(false);
        lane.move(dx, 3);
        lane.grow(3, 3);
    }
}
