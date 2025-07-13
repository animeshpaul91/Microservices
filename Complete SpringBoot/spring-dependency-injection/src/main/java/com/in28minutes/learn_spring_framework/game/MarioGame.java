package com.in28minutes.learn_spring_framework.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("MarioGame")
public class MarioGame implements GamingConsole {
    @Override
    public void up() {
        System.out.println("Mario up");
    }

    @Override
    public void down() {
        System.out.println("Mario down");
    }

    @Override
    public void left() {
        System.out.println("Mario left");
    }

    @Override
    public void right() {
        System.out.println("Mario right");
    }
}
