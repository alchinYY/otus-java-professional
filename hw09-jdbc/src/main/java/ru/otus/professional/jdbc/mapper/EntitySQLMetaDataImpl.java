package ru.otus.professional.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> metaData;
    private final String selectByIdQuery;
    private final String selectAllQuery;
    private final String saveQuery;
    private final String updateQuery;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;

        selectAllQuery = String.format("SELECT * FROM %s", metaData.getName());
        selectByIdQuery = String.format("SELECT * FROM %s WHERE %s = ?", metaData.getName(), metaData.getIdField().getName());
        saveQuery = String.format("INSERT INTO %s (%s) VALUES (%s)", metaData.getName(), getFieldsStringWithoutId(), getValuesList());
        updateQuery = String.format("UPDATE %s SET %s WHERE %s = ?", metaData.getName(), createUpdateStringQuery(), metaData.getIdField().getName());
    }

    @Override
    public String getSelectAllSql() {
        return selectAllQuery;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdQuery;
    }

    @Override
    public String getInsertSql() {
        return saveQuery;
    }

    @Override
    public String getUpdateSql() {
        return updateQuery;
    }

    private String createUpdateStringQuery() {
        return metaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .reduce((a1, a2) -> a1 + "," + a2).orElse("");
    }

    private String getFieldsStringWithoutId() {
        return String.join(", ",
                metaData.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .toList()
        );
    }

    private String getValuesList() {
        return String.join(", ",
                metaData.getFieldsWithoutId().stream()
                        .map(f -> "?")
                        .toList()
        );
    }
}
