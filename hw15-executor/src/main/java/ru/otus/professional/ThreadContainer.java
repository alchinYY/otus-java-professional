package ru.otus.professional;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadContainer {

    private final AtomicInteger currentThreadIndex = new AtomicInteger(0);
    private final List<DigitGenerator> digitGenerators;
    private final List<Thread> threads = new ArrayList<>();

    public ThreadContainer(List<DigitGenerator> digitGenerators) {

        this.digitGenerators = digitGenerators;

    }

    public void start(int circleCount) {
        threadInit(digitGenerators, circleCount);
        threads.forEach(Thread::start);
    }

    private void threadInit(List<DigitGenerator> digitGenerators, int circleCount) {

        for (int i = 0; i < digitGenerators.size(); i++) {
            int finalI = i;
            var thread = new Thread(() -> action(circleCount, digitGenerators.get(finalI)));
            thread.setName("thread-" + i);
            threads.add(thread);
        }

    }

    private void action(int circleCount, DigitGenerator digitGenerator) {
        if (circleCount <= 0) {
            while (true) {
                generatorLogic(digitGenerator);
            }
        } else {
            for (int i = 0; i < circleCount; i++) {
                generatorLogic(digitGenerator);
            }
        }
    }

    private synchronized void generatorLogic(DigitGenerator digitGenerator) {
        try {
            while (!Thread.currentThread().equals(threads.get(currentThreadIndex.get()))) {
                this.wait();
            }
            if (currentThreadIndex.get() >= threads.size() - 1) {
                currentThreadIndex.set(0);
            } else {
                currentThreadIndex.incrementAndGet();
            }
            digitGenerator.generate();
            sleep();
            notifyAll();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


}
