package ru.otus.professional;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.client.ClientCounterImpl;
import ru.otus.professional.client.Counter;
import ru.otus.professional.client.NumberMessageStreamObserver;
import ru.otus.professional.generated.KillBossServiceGrpc;
import ru.otus.professional.generated.NumbersRangeMessage;
import ru.otus.professional.utility.ThreadUtility;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        clientNoBlock(channel, 1, 30, 1, 50, 1);
    }


    private static void clientNoBlock(ManagedChannel channel, long startValue, long endValue, long clientStartValue, long clientEndValue, long clientDelaySecond) throws InterruptedException {
        var stub = KillBossServiceGrpc.newStub(channel);

        var range = NumbersRangeMessage.newBuilder()
                .setLeft(startValue)
                .setRight(endValue)
                .build();

        var latch = new CountDownLatch(1);
        var counter = new ClientCounterImpl(clientStartValue, clientEndValue, clientDelaySecond);

        log.info("numbers Client is starting");
        stub.getStreamOfNumbers(range, new NumberMessageStreamObserver(latch, counter));
        ThreadUtility.sleep(1);
        counter.startCounter();

        latch.await();
        channel.shutdown();
    }

}
