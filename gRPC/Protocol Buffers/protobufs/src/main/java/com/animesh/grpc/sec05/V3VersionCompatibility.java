package com.animesh.grpc.sec05;

import com.animesh.grpc.sec05.parser.V1Parser;
import com.animesh.grpc.sec05.parser.V2Parser;
import com.animesh.grpc.sec05.parser.V3Parser;
import com.animesh.grpc.sec05.v3.models.Television;
import com.animesh.grpc.sec05.v3.models.TelevisionType;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class V3VersionCompatibility {
    private static final Logger log = LoggerFactory.getLogger(V3VersionCompatibility.class);

    static void main(String[] args) throws InvalidProtocolBufferException {
        final Television tv = Television.newBuilder()
                .setBrand("Samsung")
                .setType(TelevisionType.UHD)
                .build();

        V1Parser.parse(tv.toByteArray()); // deserialize V2 television to V1 television
        log.info("==============================");
        V2Parser.parse(tv.toByteArray());
        log.info("==============================");
        V3Parser.parse(tv.toByteArray());
    }
}
