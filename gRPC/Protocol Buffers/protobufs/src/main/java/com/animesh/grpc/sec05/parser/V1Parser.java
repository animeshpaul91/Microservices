package com.animesh.grpc.sec05.parser;

import com.animesh.grpc.sec05.v1.models.Television;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1Parser {
    private static final Logger log = LoggerFactory.getLogger(V1Parser.class);

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        final Television tv = Television.parseFrom(bytes);
        log.info("TV: {}", tv);
        log.info("Brand: {}", tv.getBrand());
        log.info("Year: {}", tv.getYear());
    }
}
