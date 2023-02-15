package ru.otus.professional.services;

import ru.otus.professional.api.SensorsDataChannel;
import ru.otus.professional.api.SensorsDataServer;
import ru.otus.professional.api.model.SensorData;

public class SensorsDataServerImpl implements SensorsDataServer {

    private final SensorsDataChannel sensorsDataChannel;

    public SensorsDataServerImpl(SensorsDataChannel sensorsDataChannel) {
        this.sensorsDataChannel = sensorsDataChannel;
    }

    @Override
    public void onReceive(SensorData sensorData) {
        sensorsDataChannel.push(sensorData);
    }
}