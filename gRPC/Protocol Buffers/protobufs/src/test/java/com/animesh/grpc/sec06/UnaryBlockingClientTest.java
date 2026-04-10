package com.animesh.grpc.sec06;

import com.animesh.grpc.sec06.models.AccountBalance;
import com.animesh.grpc.sec06.models.AllAccountsResponse;
import com.animesh.grpc.sec06.models.BalanceCheckRequest;
import com.google.protobuf.Empty;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnaryBlockingClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(UnaryBlockingClientTest.class);

    @Test
    public void testGetAccountBalance() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2000542313)
                .build();

        final AccountBalance accountBalance = blockingStub.getAccountBalance(request);
        log.info("Received Unary Balance: {}", accountBalance);

        assertEquals(100, accountBalance.getBalance());
    }

    @Test
    public void testAllAccounts() {
        var emptyRequest = Empty.getDefaultInstance();

        final AllAccountsResponse allAccounts = blockingStub.getAllAccounts(emptyRequest);
        log.info("All Accounts Size: {}", allAccounts.getAccountsCount());

        final List<AccountBalance> accounts = allAccounts.getAccountsList();
        assertEquals(10, accounts.size());
        accounts.forEach(account -> assertEquals(100, account.getBalance()));
    }
}
