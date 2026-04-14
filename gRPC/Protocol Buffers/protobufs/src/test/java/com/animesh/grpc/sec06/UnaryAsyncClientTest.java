package com.animesh.grpc.sec06;

import com.animesh.grpc.common.AccountBalanceObserverTest;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class UnaryAsyncClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(UnaryAsyncClientTest.class);

    @Test
    void testGetBalance() throws InterruptedException {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();

        final CountDownLatch latch = new CountDownLatch(1);
        this.asyncStub.getAccountBalance(request, new AccountBalanceObserverTest(latch));
        latch.await();
    }
}
