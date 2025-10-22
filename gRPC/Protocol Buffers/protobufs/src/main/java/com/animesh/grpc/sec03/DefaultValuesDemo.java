package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultValuesDemo {
    private static final Logger log = LoggerFactory.getLogger(DefaultValuesDemo.class);

    public static void main(String[] args) {
        final School school = School.newBuilder().build();
    }
}
