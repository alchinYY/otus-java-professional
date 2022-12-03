package ru.otus.professional.dataprocessor;

import ru.otus.professional.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
