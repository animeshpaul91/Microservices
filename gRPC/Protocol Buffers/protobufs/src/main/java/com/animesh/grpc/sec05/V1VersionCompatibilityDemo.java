package com.animesh.grpc.sec05;

import com.animesh.grpc.sec05.parser.V1Parser;
import com.animesh.grpc.sec05.v1.models.Television;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class V1VersionCompatibilityDemo {
    private static final Logger log = LoggerFactory.getLogger(V1VersionCompatibilityDemo.class);

    public static void main(String[] args) throws InvalidProtocolBufferException {
        final Television tv = Television.newBuilder()
                .setBrand("Samsung")
                .setYear(2019)
                .build();

        V1Parser.parse(tv.toByteArray());
    }
}
