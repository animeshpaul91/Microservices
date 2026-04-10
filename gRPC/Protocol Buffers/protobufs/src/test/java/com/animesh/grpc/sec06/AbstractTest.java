package com.animesh.grpc.sec06;

import com.animesh.grpc.common.AbstractChannelTest;
import com.animesh.grpc.common.GRPCServer;
import com.animesh.grpc.sec06.models.BankServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest extends AbstractChannelTest {

    private final GRPCServer grpcServer = GRPCServer.create(new BankService());
    protected BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        // start server
        this.grpcServer.start();

        // start client
        super.setup();

        // initialize stub
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void teardown() throws InterruptedException {
        // stop client
        super.teardown();

        // stop server
        this.grpcServer.stop();
    }
}
