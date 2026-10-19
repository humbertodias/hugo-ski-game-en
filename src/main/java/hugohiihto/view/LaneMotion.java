package hugohiihto.view;

import hugohiihto.model.LaneObject;
import hugohiihto.model.SkiField;

/**
 * One step of each lane. Started from the paint thread as a short-lived worker, like the original.
 */
public final class LaneMotion {
    private LaneMotion() {
    }

    public static void kick(GameSurface surface) {
        SkiField slope = surface.session().slope();
        step(Thread.MAX_PRIORITY, () -> first(surface, slope), false, surface);
        step(Thread.MAX_PRIORITY, () -> second(surface, slope), true, surface);
        step(Thread.MAX_PRIORITY, () -> third(surface, slope), true, surface);
        step(Thread.MAX_PRIORITY, () -> fourth(surface, slope), true, surface);
    }

    private static void step(int priority, Runnable motion, boolean repaintFirst, GameSurface surface) {
        Thread thread = new Thread(() -> {
            if (repaintFirst) {
                surface.repaintSurface();
            }
            Thread.currentThread().setPriority(priority);
            motion.run();
        });
        thread.start();
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
            surface.repaintSurface();
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
            surface.repaintSurface();
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
