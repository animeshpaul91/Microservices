package com.animesh.grpc.sec04;

import com.animesh.grpc.sec04.models.Person;
import com.animesh.grpc.sec04.models.common.Address;
import com.animesh.grpc.sec04.models.common.BodyStyle;
import com.animesh.grpc.sec04.models.common.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportDemo {
    private static final Logger log = LoggerFactory.getLogger(ImportDemo.class);

    public static void main(String[] args) {
        final Address address = Address.newBuilder()
                .setStreet("123 Main St")
                .setCity("Springfield")
                .setState("IL")
                .setZipCode("62701")
                .build();

        final Car car = Car.newBuilder()
                .setMake("Toyota")
                .setModel("Camry")
                .setYear(2020)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        final Person person = Person.newBuilder()
                .setName("John Doe")
                .setAge(30)
                .setAddress(address)
                .setCar(car)
                .build();

        log.info("Person Details: {}", person);
        log.info("Person hasAge field: {}", person.hasAge());

        //hello
    }
}
