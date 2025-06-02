package com.in28minutes.learn_spring_framework;

import com.in28minutes.learn_spring_framework.game.GameRunner;
import com.in28minutes.learn_spring_framework.game.MarioGame;
import com.in28minutes.learn_spring_framework.game.SuperContraGame;

public class AppGamingBasic {
    public static void main(String[] args) {
        final MarioGame marioGame = new MarioGame();
        final SuperContraGame superContraGame = new SuperContraGame();
        final GameRunner gameRunner = new GameRunner(superContraGame);
        gameRunner.run();
    }
}
