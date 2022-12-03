package ru.otus.professional.dataprocessor;

import ru.otus.professional.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
