package com.animesh.grpc.sec06.client;

import com.animesh.grpc.sec06.AccountBalanceObserver;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    static void main() throws InterruptedException {
        final var channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        final var bankServiceStub = BankServiceGrpc.newStub(channel);
        final var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();
        final var accountBalanceObserver = new AccountBalanceObserver();

        bankServiceStub.getAccountBalance(request, accountBalanceObserver);

        logger.info("Request sent to server, waiting for response");
        Thread.sleep(1000);
    }
}
