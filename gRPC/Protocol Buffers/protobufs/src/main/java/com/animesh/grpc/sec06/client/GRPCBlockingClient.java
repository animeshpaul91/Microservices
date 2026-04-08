package com.animesh.grpc.sec06.client;

import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GRPCBlockingClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCBlockingClient.class);

    static void main() {
        final var channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        final var bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        final var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();
        final var balance = bankServiceBlockingStub.getAccountBalance(request);
        logger.info("Received response: {}", balance);
    }
}
