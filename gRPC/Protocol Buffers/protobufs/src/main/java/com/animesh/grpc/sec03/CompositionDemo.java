package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Address;
import com.animesh.grpc.sec03.models.School;
import com.animesh.grpc.sec03.models.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositionDemo {
    private static final Logger log = LoggerFactory.getLogger(CompositionDemo.class);

    static void main(String[] args) {
        final Address address = Address.newBuilder()
                .setStreet("101 Main St")
                .setCity("Redmond")
                .setState("WA")
                .setZipCode("98052")
                .build();

        final Student student = Student.newBuilder()
                .setName("John Doe")
                .setAddress(address)
                .build();

        final School school = School.newBuilder()
                .setId(100)
                .setName("Springfield High School")
                .setAddress(address)
                .build();

        log.info("Address: {}", address);
        log.info("Student: {}", student);
        log.info("School: {}", school);
    }
}
