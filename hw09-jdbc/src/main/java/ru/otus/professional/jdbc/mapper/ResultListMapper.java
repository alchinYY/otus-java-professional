package ru.otus.professional.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.professional.jdbc.ReflectionUtility;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class ResultListMapper <T> implements Function<ResultSet, List<T>> {

    private final EntityClassMetaData<T> entityClassMetaData;

    @Override
    public List<T> apply(ResultSet resultSet) {
        try {
            List<T> tList = new ArrayList<>();
            while (resultSet.next()) {
                T object = ReflectionUtility.useConstructor(entityClassMetaData.getConstructor());
                for (Field f : entityClassMetaData.getAllFields()) {
                    ReflectionUtility.setValue(f, object, resultSet.getObject(f.getName()));
                }
                tList.add(object);
            }
            return tList;
        } catch (SQLException exception) {
            return List.of();
        }

    }
}
