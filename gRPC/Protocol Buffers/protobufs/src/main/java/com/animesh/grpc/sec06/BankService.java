package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.AccountBalance;
import com.animesh.grpc.sec06.models.AllAccountsResponse;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import com.animesh.grpc.sec06.repository.AccountRepository;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Map;

// Server side implementation of the BankService (Service Class)
// This is not the Server
public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        final var accountNumber = request.getAccountNumber();
        final var accountBalance = getAccountBalance(accountNumber, AccountRepository.getBalance(accountNumber));

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
        final List<AccountBalance> allAccounts = AccountRepository.getAccounts()
                .entrySet()
                .stream()
                .map(entry -> getAccountBalance(entry.getKey(), entry.getValue())
                )
                .toList();

        final var allAccountsResponse = AllAccountsResponse.newBuilder()
                .addAllAccounts(allAccounts)
                .build();

        responseObserver.onNext(allAccountsResponse);
        responseObserver.onCompleted();
    }

    private static AccountBalance getAccountBalance(Integer accountNumber, Integer accountBalance) {
        return AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(accountBalance)
                .build();
    }
}
