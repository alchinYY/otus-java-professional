package ru.otus.professional.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.professional.jdbc.ReflectionUtility;
import ru.otus.professional.jdbc.exception.ResultMapperException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.function.Function;

@RequiredArgsConstructor
public class ResultMapper<T> implements Function<ResultSet, T> {

    private final EntityClassMetaData<T> entityClassMetaData;

    @Override
    public T apply(ResultSet resultSet) {
        try {
            T object = entityClassMetaData.getConstructor().newInstance();
            if (resultSet.next()) {
                for (Field f : entityClassMetaData.getAllFields()) {
                    ReflectionUtility.setValue(f, object, resultSet.getObject(f.getName()));
                }
            }
            return object;
        } catch (Exception ex) {
            throw new ResultMapperException(ex.getMessage());
        }
    }

}
