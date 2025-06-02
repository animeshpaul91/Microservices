package com.in28minutes.learn_spring_framework;

import com.in28minutes.learn_spring_framework.configuration.HelloWorldConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldSpringApp {
    public static void main(String[] args) {
        // 1. Launch a Spring Context with our Configuration
        final ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);

        // 2. Configure things that we want Spring to manage
        // HelloWorldConfiguration @Configuration and @Bean

        // 3. Configure stuff that we want spring to manage @Configuration
        System.out.println(context.getBean("getName"));
        System.out.println(context.getBean("getAge"));
        System.out.println(context.getBean("getPerson"));
        System.out.println(context.getBean("getAddress"));
    }
}
