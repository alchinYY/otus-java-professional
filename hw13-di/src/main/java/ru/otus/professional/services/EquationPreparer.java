package ru.otus.professional.services;

import ru.otus.professional.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}