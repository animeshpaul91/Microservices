package com.in28minutes.learn_spring_framework.game;

public class GameRunner implements Runner {
    private final Game game;

    public GameRunner(final Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("Running Game: " + game.getClass().getSimpleName());
        game.up();
        game.down();
        game.left();
        game.right();
    }
}
