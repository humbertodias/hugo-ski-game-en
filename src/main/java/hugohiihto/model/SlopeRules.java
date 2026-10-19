package hugohiihto.model;

import hugohiihto.audio.SoundBoard;
import hugohiihto.view.GameSurface;

/**
 * One timer tick of the slope: collide with the row Hugo is on, then advance to the next row.
 */
public final class SlopeRules {
    private boolean resolvingHit = true;
    private boolean awardedFinish;
    private String upcoming = "";

    public void tick(GameSession game, GameSurface ui) {
        if (game.progress() >= 71 || game.isGameOver()) {
            game.cancelLoop();
            game.setPaused(true);
            if (game.progress() >= 71 && game.paused()) {
                ui.openCutscene(7, 6);
            }
        }
        if (ui.nextScreen() == 2) {
            game.setLane(0);
            game.setPaused(true);
        } else if (game.progress() < 37) {
            ui.audio().playSkiing();
        }

        if (!game.paused()) {
            play(game, ui);
            return;
        }
        if (!awardedFinish && game.progress() > -2 && game.progress() < 70) {
            game.setProgress(game.progress() - 1);
        }
        awardedFinish = true;
    }

    private void play(GameSession game, GameSurface ui) {
        if (game.progress() < 70) {
            awardedFinish = false;
        }
        if (resolvingHit) {
            resolve(game, ui);
            return;
        }
        advance(game, ui);
    }

    private void resolve(GameSession game, GameSurface ui) {
        resolvingHit = false;
        System.out.println("\nTIC, location is " + game.progress() + ", next will be " + game.furthest());
        if (game.furthest() <= -1 || upcoming.length() < 4) {
            return;
        }
        char here = upcoming.charAt(game.lane());
        if (here == 'E' || here == '1' || here == '2') {
            game.slope().setVanishFaster(true);
        }
        if (here == 'F') {
            game.setProgress(71);
        }
        if (here == '8') {
            hit(game, ui, "ENEMY HIT -1---- SNOWMAN", 14);
        }
        if (here == 'o') {
            hit(game, ui, "ENEMY HIT --2--- SNOWBALL", 15);
        }
        if (here == 'Q') {
            hit(game, ui, "ENEMY HIT ---3-- BOMB", 16);
        }
        if (here == 'B') {
            hit(game, ui, "ENEMY HIT ----4- BEAVER", 17);
        }
        if (here == 'S') {
            scylla(game, ui);
        }
        if (here == 'M') {
            money(game, ui);
        }
        if (here == 'F' && !awardedFinish) {
            awardedFinish = true;
            game.score().awardFinish(game.lives());
            ui.audio().playScoreFanfare();
        }
    }

    private void advance(GameSession game, GameSurface ui) {
        resolvingHit = true;
        game.slope().setVanishFaster(false);
        if (game.progress() < 0) {
            game.slope().parkBeforeStart();
        }
        game.slope().resetApproach(ui.screenWidth(), ui.screenHeight(), ui.skierY(), game.beat());
        if (game.progress() < 70 && game.furthest() < 70) {
            game.setProgress(game.progress() + 1);
            if (game.progress() >= game.furthest()) {
                game.setFurthest(game.furthest() + 1);
            }
        }
        System.out.println("TAC, location is " + game.progress() + ", next will be " + game.furthest());
        StageCourse stage = game.stage();
        if (stage == null) {
            return;
        }
        for (int i = 0; i < stage.length(); i++) {
            if (game.furthest() != i) {
                continue;
            }
            System.out.print(" ... ABOUT TO GIVE THE NEXT 4 with index " + i + " --- " + stage.row(i));
            upcoming = stage.row(i);
            game.slope().show(upcoming);
            stage.consume(i);
        }
    }

    private void hit(GameSession game, GameSurface ui, String message, int video) {
        System.out.println(message + ", line (from 0 to 3) is " + game.lane());
        game.setPaused(true);
        ui.openCutscene(video, 2);
        game.loseLife();
    }

    private void scylla(GameSession game, GameSurface ui) {
        System.out.println("SCYLLA BUTTON PRESS");
        game.setPaused(true);
        ui.openCutscene(2, 2);
        SoundBoard audio = ui.audio();
        if (game.cheat().isActive()) {
            audio.playFinnishTheme();
            return;
        }
        if (game.lives() <= 3) {
            audio.playSkateboard();
            return;
        }
        audio.playPopcorn();
    }

    private void money(GameSession game, GameSurface ui) {
        game.slope().setVanishFaster(true);
        game.slope().collectFrom(game.lane());
        ui.audio().playMoney();
        game.score().awardMoneyBag(game.cheat().isActive());
    }
}
