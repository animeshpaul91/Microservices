package com.in28minutes.learn_spring_framework.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.in28minutes.learn_spring_framework.game")
public class GamingConfiguration {
    // This class is used to configure the Spring application context for gaming-related beans.
    // It uses @Configuration to indicate that it provides Spring configuration,
    // and @ComponentScan to specify the package to scan for components.
}
