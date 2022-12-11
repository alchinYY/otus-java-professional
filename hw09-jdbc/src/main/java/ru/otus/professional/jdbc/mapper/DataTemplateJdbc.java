package ru.otus.professional.jdbc.mapper;

import ru.otus.professional.jdbc.ReflectionUtility;
import ru.otus.professional.jdbc.repository.DataTemplate;
import ru.otus.professional.jdbc.repository.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final ResultMapper<T> resultMapper;
    private final ResultListMapper<T> resultListMapper;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
        this.resultMapper = new ResultMapper<>(entityClassMetaData);
        this.resultListMapper = new ResultListMapper<>(entityClassMetaData);
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultMapper);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultListMapper)
                .orElse(Collections.emptyList());
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                createArgumentList(client, entityClassMetaData.getFieldsWithoutId())
        );
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                createArgumentList(client, entityClassMetaData.getAllFields())
        );
    }

    private List<Object> createArgumentList(T client, List<Field> fields) {
        return fields.stream()
                .map(f -> ReflectionUtility.getValueFromObjectByField(f, client))
                .toList();
    }
}
