package ru.otus.professional.jdbc;

import lombok.experimental.UtilityClass;
import ru.otus.professional.jdbc.exception.EntityMetaDataException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@UtilityClass
public class ReflectionUtility {

    public Object getValueFromObjectByField(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new EntityMetaDataException(ex.getMessage());
        }
    }

    public void setValue(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException ex) {
            throw new EntityMetaDataException(ex.getMessage());
        }
    }

    public <T> T useConstructor(Constructor<T> constructor) {
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new EntityMetaDataException(ex.getMessage());
        }
    }
}
