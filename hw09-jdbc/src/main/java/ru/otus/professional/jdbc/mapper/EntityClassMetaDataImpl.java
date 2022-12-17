package ru.otus.professional.jdbc.mapper;

import ru.otus.professional.jdbc.annotation.Id;
import ru.otus.professional.jdbc.exception.EntityMetaDataException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> aClass;
    private final Field idField;
    private final Constructor<T> constructor;
    private final List<Field> withoutIdFields;

    public EntityClassMetaDataImpl(Class<T> aClass) {
        this.aClass = aClass;
        this.withoutIdFields = findAllWithoutIdFields();
        this.idField = findIdField();
        this.constructor = findConstructorWithoutParameters();
    }

    @Override
    public String getName() {
        return aClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        var fields = new ArrayList<>(withoutIdFields);
        fields.add(idField);
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return withoutIdFields;
    }

    private List<Field> findAllWithoutIdFields() {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }

    private Field findIdField() {
        for (Field field : aClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new EntityMetaDataException("@Id not found for class-entity " + getName());
    }

    private Constructor<T> findConstructorWithoutParameters() {
        try {
            return aClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            throw new EntityMetaDataException("Constructor without arguments not found for class " + getName());
        }
    }
}
