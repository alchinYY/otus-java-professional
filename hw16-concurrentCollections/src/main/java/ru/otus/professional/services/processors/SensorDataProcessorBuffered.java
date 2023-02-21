package ru.otus.professional.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.professional.api.SensorDataProcessor;
import ru.otus.professional.api.model.SensorData;
import ru.otus.professional.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {

    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final ArrayBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        synchronized (dataBuffer) {
            dataBuffer.add(data);
            if (dataBuffer.size() >= bufferSize) {
                flush();
            }
        }
    }

    public void flush() {
        try {
            if (!dataBuffer.isEmpty()) {
                var list = new ArrayList<SensorData>();
                dataBuffer.drainTo(list);
                writer.writeBufferedData(
                        list.stream()
                                .sorted(Comparator.comparing(SensorData::getMeasurementTime))
                                .collect(Collectors.toList())
                );
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

}
