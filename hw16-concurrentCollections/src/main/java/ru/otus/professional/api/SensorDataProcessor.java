package ru.otus.professional.api;

import ru.otus.professional.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {
    }
}