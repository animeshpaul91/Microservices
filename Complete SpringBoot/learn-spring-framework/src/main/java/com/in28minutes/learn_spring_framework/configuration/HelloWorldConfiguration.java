package com.in28minutes.learn_spring_framework.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configure beans that we want Spring to manage
@Configuration
public class HelloWorldConfiguration {
    // contains beans that we want Spring to manage

    @Bean
    public String getName() {
        return "Animesh";
    }
}
