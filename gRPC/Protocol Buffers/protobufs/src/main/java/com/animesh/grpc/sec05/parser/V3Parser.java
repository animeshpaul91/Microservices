package com.animesh.grpc.sec05.parser;


import com.animesh.grpc.sec05.v3.models.Television;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class V3Parser {
    private static final Logger log = LoggerFactory.getLogger(V3Parser.class);

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        final Television tv = Television.parseFrom(bytes);

        // field tags are used to identify the fields, not the field names.
        log.info("TV: {}", tv);
        log.info("Brand: {}", tv.getBrand());
        log.info("Type: {}", tv.getType());
    }
}
