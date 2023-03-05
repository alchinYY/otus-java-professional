package ru.otus.professional.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.utility.ThreadUtility;

import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Slf4j
public class ClientCounterImpl implements Counter {

    private final long startValue;
    private final long endValue;
    private final long sleepTimeSecond;
    private final AtomicLong lastServerValue = new AtomicLong();
    private final AtomicLong currentValue;

    public ClientCounterImpl(long startValue, long endValue, long sleepTimeSecond) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.sleepTimeSecond = sleepTimeSecond;
        currentValue = new AtomicLong(startValue);
    }


    @Override
    public void correctValue(long correctValue) {
        lastServerValue.set(correctValue);
        log.info("new value: {}", correctValue);
    }

    @Override
    public long getValue() {
        return currentValue.longValue();
    }

    @Override
    public void startCounter() {
        for(var i = startValue; i <= endValue; i++) {
            log.info("currentValue:{}", currentValue.addAndGet(lastServerValue.getAndSet(0) + 1));
            ThreadUtility.sleep(sleepTimeSecond);
        }
    }

}
