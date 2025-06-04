package com.in28minutes.learn_spring_framework.configuration;

import com.in28minutes.learn_spring_framework.game.GameRunner;
import com.in28minutes.learn_spring_framework.game.GamingConsole;
import com.in28minutes.learn_spring_framework.game.MarioGame;
import com.in28minutes.learn_spring_framework.game.PacmanGame;
import com.in28minutes.learn_spring_framework.game.SuperContraGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamingConfiguration {
    @Bean
    public GamingConsole game() {
        return new PacmanGame();
    }

//    final GamingConsole marioGame = new MarioGame();
//    final GamingConsole superContraGame = new SuperContraGame();
//    final GamingConsole pacmanGame =
//    final GameRunner gameRunner = new GameRunner(pacmanGame);
//        gameRunner.run();
}
