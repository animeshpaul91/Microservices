package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScalarDemo {
    private static final Logger log = LoggerFactory.getLogger(ScalarDemo.class);

    static void main(String[] args) {
        final Person person = Person.newBuilder()
                .setLastName("Paul")
                .setAge(32)
                .setEmail("ani.nitmz@gmail.com")
                .setEmployed(true)
                .setSalary(5000)
                .setBankAccountNumber(200054231)
                .setBalance(-1000)
                .build();

        log.info("Person: {}", person);
    }
}
