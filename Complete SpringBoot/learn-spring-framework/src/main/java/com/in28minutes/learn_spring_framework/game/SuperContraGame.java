package com.in28minutes.learn_spring_framework.game;

public class SuperContraGame implements Game {
    @Override
    public void up() {
        System.out.println("Super Contra up");
    }

    @Override
    public void down() {
        System.out.println("Super Contra down");
    }

    @Override
    public void left() {
        System.out.println("Super Contra left");
    }

    @Override
    public void right() {
        System.out.println("Super Contra right");
    }
}
