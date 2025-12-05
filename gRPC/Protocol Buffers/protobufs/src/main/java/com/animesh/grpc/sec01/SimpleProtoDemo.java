package com.animesh.grpc.sec01;


import com.animesh.grpc.sec01.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProtoDemo {
    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    static void main(String[] args) {
        final Person person = Person.newBuilder()
                .setName("John")
                .setAge(12)
                .build();

        log.info("Person Name: {}", person.getName());
        log.info("Person Age: {}", person.getAge());
    }
}
