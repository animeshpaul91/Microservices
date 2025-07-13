package com.in28minutes.learn_spring_framework.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GameRunner implements Runner {
    private final GamingConsole game;

    @Autowired
    public GameRunner(@Qualifier("MarioGame") final GamingConsole game) {
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
