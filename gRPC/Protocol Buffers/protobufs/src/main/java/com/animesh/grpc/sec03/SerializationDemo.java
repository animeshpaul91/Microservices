package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SerializationDemo {
    private static final Logger log = LoggerFactory.getLogger(SerializationDemo.class);
    private static final Path PATH = Path.of("person.out");

    static void main(String[] args) throws IOException {

        // primitive values that are not assigned takes default values. It treats those fields to be non-existent.
        final Person person = Person.newBuilder()
                .setLastName("Paul")
                .setAge(32)
                .setEmail("ani.nitmz@gmail.com")
                .setEmployed(true)
                .setSalary(5000)
                .setBankAccountNumber(200054231)
                .setBalance(-1000)
                .build();

        serialize(person);
        final Person deserializedPerson = deserialize();
        log.info("Person: {}", deserializedPerson);
        log.info("Equals: {}", person.equals(deserializedPerson));
        log.info("Bytes Length: {}", person.toByteArray().length);
    }

    public static void serialize(Person person) throws IOException {
        try (var stream = Files.newOutputStream(PATH)) {
            person.writeTo(stream);
        }
    }

    public static Person deserialize() throws IOException {
        try (var stream = Files.newInputStream(PATH)) {
            return Person.parseFrom(stream);
        }
    }
}
