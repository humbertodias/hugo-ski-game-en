package hugohiihto.view;

import hugohiihto.model.LaneObject;
import hugohiihto.model.SkiField;

/**
 * One step of each lane. Runs on the painting thread, and at most once every few milliseconds,
 * so a burst of repaints cannot throw the sprite off screen before it is drawn.
 */
public final class LaneMotion {
    private static final long STEP_MS = 16;
    private static long lastStep;

    private LaneMotion() {
    }

    public static void kick(GameSurface surface) {
        long now = System.currentTimeMillis();
        if (now - lastStep < STEP_MS) {
            return;
        }
        lastStep = now;
        SkiField slope = surface.session().slope();
        first(surface, slope);
        second(surface, slope);
        third(surface, slope);
        fourth(surface, slope);
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
        if (System.currentTimeMillis() % 3 == 0 && !held) {
            lane.move(-2, -2);
            lane.grow(1, 2);
        }
        if (lane.y() < 185 && !slope.hazardsHeld()) {
            lane.move(0, 2);
        } else if (lane.y() > surface.skierY() - 25 && System.currentTimeMillis() % 5 == 0 && !slope.hazardsHeld()) {
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
        if (System.currentTimeMillis() % 3 == 0 && !slope.hazardsHeld()) {
            lane.move(0, 1);
            lane.grow(1, 2);
        }
        if (lane.y() < 175 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        if (System.currentTimeMillis() % 3 == 0 && lane.x() > 192 && !slope.hazardsHeld()) {
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
        if (lane.y() > surface.skierY() + 30 && System.currentTimeMillis() % 5 == 0 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        fling(slope, lane, -1);
    }

    private static void third(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(2);
        if (System.currentTimeMillis() % 3 == 0 && !slope.hazardsHeld()) {
            lane.move(0, 1);
            lane.grow(1, 2);
        }
        if (lane.y() < 175 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        if (System.currentTimeMillis() % 3 == 0 && lane.x() < 340 && !slope.hazardsHeld()) {
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
        if (lane.y() > surface.skierY() + 30 && System.currentTimeMillis() % 5 == 0 && !slope.hazardsHeld()) {
            lane.move(0, 1);
        }
        fling(slope, lane, 0);
    }

    private static void fourth(GameSurface surface, SkiField slope) {
        if (surface.paused()) {
            return;
        }
        LaneObject lane = slope.lane(3);
        if (System.currentTimeMillis() % 3 == 0 && !slope.hazardsHeld()) {
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
        if (lane.y() > surface.skierY() + 30 && System.currentTimeMillis() % 5 == 0 && !slope.hazardsHeld()) {
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
