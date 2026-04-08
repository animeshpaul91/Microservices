package com.animesh.grpc.common;

import com.animesh.grpc.sec06.BankService;

public class Demo {
    static void main() {
        GRPCServer.create(new BankService())
                .start()
                .await();
    }
}
