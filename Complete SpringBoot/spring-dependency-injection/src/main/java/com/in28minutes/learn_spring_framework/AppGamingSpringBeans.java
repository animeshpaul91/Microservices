package com.in28minutes.learn_spring_framework;

import com.in28minutes.learn_spring_framework.configuration.GamingConfiguration;
import com.in28minutes.learn_spring_framework.game.GameRunner;
import com.in28minutes.learn_spring_framework.game.GamingConsole;
import com.in28minutes.learn_spring_framework.game.Runner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppGamingSpringBeans {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GamingConfiguration.class);
        final Runner runner = context.getBean(GameRunner.class);
        runner.run();
        context.close();
    }
}
