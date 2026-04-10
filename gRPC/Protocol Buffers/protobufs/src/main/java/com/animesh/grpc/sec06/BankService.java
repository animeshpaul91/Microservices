package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.AccountBalance;
import com.animesh.grpc.sec06.models.AllAccountsResponse;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import com.animesh.grpc.sec06.repository.AccountRepository;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

import java.util.List;

// Server side implementation of the BankService (Service Class)
// This is not the Server
public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        final var accountNumber = request.getAccountNumber();
        final var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
        final List<AccountBalance> allAccounts = AccountRepository.getAccounts()
                .entrySet()
                .stream()
                .map(entry -> AccountBalance.newBuilder()
                        .setAccountNumber(entry.getKey())
                        .setBalance(entry.getValue())
                        .build()
                )
                .toList();

        final var allAccountsResponse = AllAccountsResponse.newBuilder()
                .addAllAccounts(allAccounts)
                .build();

        responseObserver.onNext(allAccountsResponse);
        responseObserver.onCompleted();
    }
}
