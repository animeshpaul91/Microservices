package com.in28minutes.learn_spring_framework.configuration;

import com.in28minutes.learn_spring_framework.dto.Address;
import com.in28minutes.learn_spring_framework.dto.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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

    // using existing beans to create new beans
    @Bean
    public Person getPerson() { // by direct method call
        return new Person(getName(), getAge(), getAddress());
    }

    @Bean
    public Person getPersonWithParameters(String getName, int getAge, Address getAddress2) { // getName(), getAge(), getAddress()
        return new Person(getName, getAge, getAddress2);
    }

    @Bean
    public Person getAnotherPerson(String getName, int getAge, Address address) { // getName(), getAge(), getAddress()
        return new Person(getName, getAge, address);
    }

    @Bean
    public Person getPersonWithQualifier(String getName, int getAge, @Qualifier("CO") Address address) { // getName(), getAge(), getAddress()
        return new Person(getName, getAge, address);
    }

    @Bean
    //(name = "address")
    @Primary // resolves conflicts for multiple beans of same type
    public Address getAddress() {
        return new Address("89 Spring Ville Ave", "Buffalo", "NY", 14226);
    }

    @Bean
    public Address getAddress2() {
        return new Address("7001 Old Redmond Road", "Redmond", "WA", 98052);
    }

    @Bean
    @Qualifier("CO")
    public Address getAddress3() {
        return new Address("492 W Burgundy St", "Highlands Ranch", "CO", 80129);
    }
}
