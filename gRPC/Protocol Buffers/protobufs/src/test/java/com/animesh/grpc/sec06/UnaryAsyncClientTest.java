package com.animesh.grpc.sec06;

import com.animesh.grpc.common.ResponseObserver;
import com.animesh.grpc.sec06.models.AccountBalance;
import com.animesh.grpc.sec06.models.AllAccountsResponse;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.google.protobuf.Empty;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UnaryAsyncClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(UnaryAsyncClientTest.class);

    @Test
    void testGetBalance() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();

        final ResponseObserver<AccountBalance> accountBalanceObserver = ResponseObserver.create();
        this.asyncStub.getAccountBalance(request, accountBalanceObserver);
        accountBalanceObserver.await(); // main thread waits in latch.await() until latch.countdown() is called either in happy or sad paths

        // at this point either list or error will be populated
        assertEquals(1, accountBalanceObserver.getList().size());
        assertEquals(100, accountBalanceObserver.getList().getFirst().getBalance());
        assertNull(accountBalanceObserver.getError());
    }

    @Test
    void testAllAccounts() {
        var request = Empty.getDefaultInstance();

        final ResponseObserver<AllAccountsResponse> accountBalanceObserver = ResponseObserver.create();
        this.asyncStub.getAllAccounts(request, accountBalanceObserver);
        accountBalanceObserver.await(); // main thread waits in latch.await() until latch.countdown() is called either in happy or sad paths

        // at this point either list or error will be populated
        assertEquals(1, accountBalanceObserver.getList().size());
        assertEquals(10, accountBalanceObserver.getList().getFirst().getAccountsCount());
        assertEquals(100, accountBalanceObserver.getList().getFirst().getAccountsList().getFirst().getBalance());
        assertNull(accountBalanceObserver.getError());
    }
}
