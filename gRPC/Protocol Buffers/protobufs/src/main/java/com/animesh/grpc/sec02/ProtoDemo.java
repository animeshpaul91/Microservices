package com.animesh.grpc.sec02;

import com.animesh.grpc.sec02.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoDemo {
    private static final Logger log = LoggerFactory.getLogger(ProtoDemo.class);

    public static void main(String[] args) {
        final Person person = Person.newBuilder()
                .setName("John")
                .setAge(12)
                .build();

        log.info("Person Name: {}", person.getName());
        log.info("Person Age: {}", person.getAge());
    }
}
