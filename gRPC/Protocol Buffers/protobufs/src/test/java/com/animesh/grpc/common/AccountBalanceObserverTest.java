package com.animesh.grpc.common;

import com.animesh.grpc.sec06.models.AccountBalance;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountBalanceObserverTest implements StreamObserver<AccountBalance> {
    private static final Logger log = LoggerFactory.getLogger(AccountBalanceObserverTest.class);
    private final CountDownLatch latch;

    public AccountBalanceObserverTest(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(AccountBalance accountBalance) {
        final var balance = accountBalance.getBalance();
        log.debug("Received Account Balance: {}", balance);
        assertEquals(100, balance);
        this.latch.countDown();
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error in AccountBalanceStreamObserver", throwable);
    }

    @Override
    public void onCompleted() {
        log.debug("Completed AccountBalanceStreamObserver");
    }
}
