package ru.otus.professional.client;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.generated.NumberMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Slf4j
public class NumberMessageStreamObserver implements StreamObserver<NumberMessage> {

    private final CountDownLatch latch;
    private final Counter counter;

    @Override
    public void onNext(NumberMessage um) {
        counter.correctValue(um.getValue());
    }

    @Override
    public void onError(Throwable t) {
        System.err.println(t);
    }

    @Override
    public void onCompleted() {
        System.out.println("\n\nrequest completed");
        latch.countDown();
    }
}
