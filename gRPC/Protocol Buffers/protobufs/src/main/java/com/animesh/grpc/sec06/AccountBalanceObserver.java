package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.AccountBalance;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountBalanceObserver implements StreamObserver<AccountBalance> {
    private static final Logger log = LoggerFactory.getLogger(AccountBalanceObserver.class);

    @Override
    public void onNext(AccountBalance accountBalance) {
        log.info("Account balance is {}", accountBalance.getBalance());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        log.info("Async operation completed");
    }
}
