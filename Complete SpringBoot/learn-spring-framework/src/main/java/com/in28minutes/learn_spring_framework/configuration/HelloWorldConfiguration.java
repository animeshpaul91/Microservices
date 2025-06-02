package com.in28minutes.learn_spring_framework.configuration;

import com.in28minutes.learn_spring_framework.dto.Address;
import com.in28minutes.learn_spring_framework.dto.Person;
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

    @Bean
    public int getAge() {
        return 32;
    }

    @Bean
    public Person getPerson() { // by direct method call
        return new Person(getName(), getAge(), getAddress());
    }

    @Bean
    public Person getPersonWithParameters(String getName, int getAge, Address getAddress) { // getName(), getAge(), getAddress()
        return new Person(getName, getAge, getAddress);
    }

    @Bean
            //(name = "address")
    public Address getAddress() {
        return new Address("89 Spring Ville Ave", "Buffalo", "NY", 14226);
    }
}
