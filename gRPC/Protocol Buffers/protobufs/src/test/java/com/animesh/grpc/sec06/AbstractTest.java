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
        super.setup();
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void teardown() throws InterruptedException {
        super.teardown();
        this.grpcServer.stop();
    }
}
