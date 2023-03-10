package ru.otus.professional.service;

import com.google.common.primitives.UnsignedInteger;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import ru.otus.professional.generated.KillBossServiceGrpc;
import ru.otus.professional.generated.NumberMessage;
import ru.otus.professional.generated.NumbersRangeMessage;
import ru.otus.professional.utility.ThreadUtility;

import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class RemoteKillBossServiceGrpcImpl extends KillBossServiceGrpc.KillBossServiceImplBase {

    private final UnsignedInteger second;

    @Override
    public void getStreamOfNumbers(NumbersRangeMessage request, StreamObserver<NumberMessage> responseObserver) {
        var startValue = request.getLeft();
        var endValue = request.getRight();

        for (var i = startValue; i <= endValue; i++) {
            var result = NumberMessage.newBuilder().setValue(i).build();
            ThreadUtility.sleep(second.intValue());
            responseObserver.onNext(result);
        }
        responseObserver.onCompleted();
    }
}
