package com.animesh.grpc.sec01;


import com.animesh.grpc.sec01.models.PersonOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProtoDemo {
    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {
        final PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
                .setName("John")
                .setAge(12)
                .build();

        log.info("Person Name: {}", person.getName());
        log.info("Person Age: {}", person.getAge());
    }
}
