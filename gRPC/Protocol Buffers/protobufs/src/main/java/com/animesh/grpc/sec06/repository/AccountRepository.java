package com.animesh.grpc.sec06.repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {
    private static final Map<Integer, Integer> db = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), _ -> 100));

    public static int getBalance(int accountNumber) {
        return db.getOrDefault(accountNumber, 100);
    }
}
