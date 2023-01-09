package ru.otus.professional.mapper;

public interface Mapper<S, T> {

    T map(S s);

}
