package com.animesh.grpc.sec04;

import com.animesh.grpc.sec04.models.Sample;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class WellKnownTypesDemo {
    private static final Logger log = LoggerFactory.getLogger(WellKnownTypesDemo.class);

    static void main(String[] args) {
        final Sample sample = Sample.newBuilder()
                .setAge(Int32Value.of(123))
                .setLoginTime(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build())
                .build();

        log.info("sample has age: {}", sample.hasAge());
        log.info("sample age: {}", sample.getAge().getValue());
        log.info("{}", Instant.ofEpochSecond(sample.getLoginTime().getSeconds()));
    }
}
