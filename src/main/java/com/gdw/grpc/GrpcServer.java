package com.gdw.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    private Server server;

    public static void main(String[] args) throws Exception {
        GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(8089)
                .addService(new StudentServiceImpl())
                .build()
                .start();

        System.out.println("server started");

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                GrpcServer.this.stop();
                System.out.println("server shut down");
            }
        });

    }

    private void stop(){
        if(server != null){
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if(server != null){
            server.awaitTermination();
        }
    }
}