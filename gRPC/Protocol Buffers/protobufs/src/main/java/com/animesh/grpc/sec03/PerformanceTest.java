package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class PerformanceTest {
    private static final Logger log = LoggerFactory.getLogger(PerformanceTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Path PATH = Path.of("person.out");

    static void main(String[] args) throws IOException {
        final Person protoPerson = Person.newBuilder()
                .setLastName("Paul")
                .setAge(32)
                .setEmail("ani.nitmz@gmail.com")
                .setEmployed(true)
                .setSalary(5000)
                .setBankAccountNumber(200054231)
                .setBalance(-1000)
                .build();

        final JsonPerson jsonPerson = new JsonPerson("Paul", 32, "ani.nitmz@gmail.com", true, 5000, 200054231, -1000);

        proto(protoPerson); // proto is binary format
        json(jsonPerson); // json is text format


//        for (int i = 0; i < 5; i++) {
//            test("Proto", () -> proto(protoPerson));
//            test("JSON", () -> json(jsonPerson));
//            IO.println("-----");
//        }
    }

    private static void proto(Person person) {
        final var bytes = person.toByteArray();
        log.info("Person bytes length: {}", bytes.length);
        try {
            Person decodedPerson = Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson jsonPerson) {
        final byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(jsonPerson);
            log.info("JsonPerson bytes length: {}", bytes.length);
            JsonPerson decodedJsonPerson = mapper.readValue(bytes, JsonPerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test(String testName, Runnable runnable) {
        final var start = System.currentTimeMillis();

        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
        }

        final var end = System.currentTimeMillis();
        log.info("{} took {} ms", testName, (end - start));
    }
}
