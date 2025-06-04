package com.in28minutes.learn_spring_framework;

import com.in28minutes.learn_spring_framework.configuration.GamingConfiguration;
import com.in28minutes.learn_spring_framework.game.GamingConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppGamingSpringBeans {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GamingConfiguration.class);
        final GamingConsole gamingConsole = context.getBean(GamingConsole.class);
        gamingConsole.up();
        context.close();
    }
}
