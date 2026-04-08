package com.animesh.grpc.common;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class GRPCServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);
    private final Server server;

    private GRPCServer(Server server) {
        this.server = server;
    }

    public static GRPCServer create(BindableService... services) {
        final var builder = ServerBuilder.forPort(6565);
        Arrays.asList(services).forEach(builder::addService);
        return new GRPCServer(builder.build());
    }

    public static GRPCServer create(int port, BindableService... services) {
        final var builder = ServerBuilder.forPort(port);
        Arrays.asList(services).forEach(builder::addService);
        return new GRPCServer(builder.build());
    }

    public GRPCServer start() {
        final var services = server.getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();

        try {
            server.start();
            logger.info("Server started, listening on port: {} | Registered services: {}", server.getPort(), services);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public void await() {
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
            logger.info("Server stopped.");
        }
    }
}
