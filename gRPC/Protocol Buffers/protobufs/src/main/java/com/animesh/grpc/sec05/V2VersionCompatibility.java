package com.animesh.grpc.sec05;

import com.animesh.grpc.sec05.parser.V1Parser;
import com.animesh.grpc.sec05.parser.V2Parser;
import com.animesh.grpc.sec05.v2.models.Television;
import com.animesh.grpc.sec05.v2.models.TelevisionType;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class V2VersionCompatibility {
    private static final Logger log = LoggerFactory.getLogger(V2VersionCompatibility.class);

    static void main(String[] args) throws InvalidProtocolBufferException {
        final Television tv = Television.newBuilder()
                .setBrand("Samsung")
                .setModel(2019)
                .setType(TelevisionType.UHD)
                .build();

        V1Parser.parse(tv.toByteArray()); // deserialize V2 television to V1 television
        log.info("==============================");
        V2Parser.parse(tv.toByteArray());
    }
}
