package ru.otus.jdbc.mapper;

import ru.otus.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        Field[] fieldList = clazz.getDeclaredFields();
        Class<?>[] classes = new Class[fieldList.length];
        for (int i=0; i<fieldList.length; i++) {
            classes[i] = fieldList[i].getType();
        }
        try {
            return clazz.getConstructor(classes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        for (Field field: clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
