package com.in28minutes.learn_spring_framework.game;

public class GameRunner {
    private final MarioGame marioGame;

    public GameRunner(final MarioGame marioGame) {
        this.marioGame = marioGame;
    }

    public void run() {
        System.out.println("Running Game: " + marioGame);
    }
}
