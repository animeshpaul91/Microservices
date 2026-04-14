package com.animesh.grpc.common;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResponseObserver<T> implements StreamObserver<T> {
    private static final Logger log = LoggerFactory.getLogger(ResponseObserver.class);

    private final List<T> list;
    private Throwable error;
    private final CountDownLatch latch;

    private ResponseObserver(int count) {
        this.latch = new CountDownLatch(count);
        this.list = Collections.synchronizedList(new ArrayList<>());
    }

    public static <T> ResponseObserver<T> create() {
        return new ResponseObserver<>(1);
    }

    public static <T> ResponseObserver<T> create(int countDown) {
        return new ResponseObserver<>(countDown);
    }

    @Override
    public void onNext(T t) {
        log.info("[{}] onNext - Received item of type {}: {}", Thread.currentThread().getName(), t != null ? t.getClass().getSimpleName() : "null", t);
        this.list.add(t);
        // If you want to assert a value, do it outside or specialize this observer
        this.latch.countDown();
    }

    @Override
    public void onError(Throwable throwable) {
        log.warn("[{}] onError - Exception in stream observer", Thread.currentThread().getName(), throwable);
        this.error = throwable;
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info("[{}] onCompleted - Stream completed. Total items received: {}", Thread.currentThread().getName(), this.list.size());
        this.latch.countDown();
    }

    public void await() {
        try {
            this.latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("[{}] await - Interrupted while waiting for response", Thread.currentThread().getName(), e);
        }
    }

    public List<T> getList() {
        return list;
    }

    public Throwable getError() {
        return error;
    }
}
