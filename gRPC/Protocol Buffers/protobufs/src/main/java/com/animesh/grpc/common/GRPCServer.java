package com.animesh.grpc.common;

import com.animesh.grpc.sec06.BankService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer {
    static void main() throws IOException, InterruptedException {
        final var server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
