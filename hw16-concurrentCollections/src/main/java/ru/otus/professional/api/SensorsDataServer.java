package ru.otus.professional.api;

import ru.otus.professional.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
