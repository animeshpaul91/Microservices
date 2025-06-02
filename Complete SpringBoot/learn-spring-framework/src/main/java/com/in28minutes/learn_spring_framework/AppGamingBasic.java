package com.in28minutes.learn_spring_framework;

import com.in28minutes.learn_spring_framework.game.GameRunner;
import com.in28minutes.learn_spring_framework.game.MarioGame;

public class AppGamingBasic {
    public static void main(String[] args) {
        final MarioGame game = new MarioGame();
        final GameRunner gameRunner = new GameRunner(game);
        gameRunner.run();
    }
}
