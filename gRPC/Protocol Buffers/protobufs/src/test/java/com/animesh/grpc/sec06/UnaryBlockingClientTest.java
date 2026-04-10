package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnaryBlockingClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(UnaryBlockingClientTest.class);

    @Test
    public void testGetAccountBalance() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();

        var unaryResponse = blockingStub.getAccountBalance(request);
        log.info("Received Unary Balance: {}", unaryResponse);

        assertEquals(100,  unaryResponse.getBalance());
    }
}
