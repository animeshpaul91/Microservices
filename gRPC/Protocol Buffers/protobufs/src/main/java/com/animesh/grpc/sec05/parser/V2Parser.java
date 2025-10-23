package com.animesh.grpc.sec05.parser;


import com.animesh.grpc.sec05.v2.models.Television;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class V2Parser {
    private static final Logger log = LoggerFactory.getLogger(V2Parser.class);

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        final Television tv = Television.parseFrom(bytes);

        // field tags are used to identify the fields, not the field names.
        log.info("TV: {}", tv);
        log.info("Brand: {}", tv.getBrand());
        log.info("Model: {}", tv.getModel());
        log.info("Type: {}", tv.getType());
    }
}
