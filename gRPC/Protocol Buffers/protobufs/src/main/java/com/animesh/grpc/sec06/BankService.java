package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.AccountBalance;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

// Server side implementation of the BankService (Service Class)
// This is not the Server
public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        final var accountNumber = request.getAccountNumber();
        final var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(1000)
                .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }
}
