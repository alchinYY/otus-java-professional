package ru.otus.professional;

import com.google.common.primitives.UnsignedInteger;
import io.grpc.ServerBuilder;
import ru.otus.professional.service.RemoteKillBossServiceGrpcImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteKillBossService = new RemoteKillBossServiceGrpcImpl(UnsignedInteger.valueOf(2));

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteKillBossService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }

}
