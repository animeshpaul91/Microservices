package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.BodyStyle;
import com.animesh.grpc.sec03.models.Car;
import com.animesh.grpc.sec03.models.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MapDemo {
    private static final Logger log = LoggerFactory.getLogger(MapDemo.class);

    public static void main(String[] args) {
        final Car car1 = Car.newBuilder()
                .setMake("Toyota")
                .setModel("Camry")
                .setYear(2006)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        final Car car2 = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2010)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        final Car car3 = Car.newBuilder()
                .setMake("Toyota")
                .setModel("RAV-4")
                .setYear(2023)
                .setBodyStyle(BodyStyle.SUV)
                .build();

        final Map<String, Car> inventory = Map.of(getKey(car1), car1, getKey(car2), car2, getKey(car3), car3);
        final Dealer dealer = Dealer.newBuilder()
                .setName("AutoWorld")
                .putAllInventory(inventory)
                .build();

        log.info("Dealer: {}", dealer);
        log.info("Toyota: {}", dealer.containsInventory("Toyota"));
        log.info("Tesla: {}", dealer.containsInventory("Tesla"));
    }

    private static String getKey(Car car) {
        return car.getMake() + "#" + car.getModel();
    }
}
