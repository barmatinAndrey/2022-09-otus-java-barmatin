package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return entityClassMetaData.getConstructor().newInstance(getObjectArgs(resultSet));
                }
                return null;
            } catch (SQLException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
            try {
                List<T> objectList = new ArrayList<>();
                while (resultSet.next()) {
                    objectList.add(entityClassMetaData.getConstructor().newInstance(getObjectArgs(resultSet)));
                }
                return objectList;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> paramList = getSqlParamList(client, entityClassMetaData.getFieldsWithoutId());
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), paramList);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Field> fieldList = new ArrayList<>(entityClassMetaData.getFieldsWithoutId());
            fieldList.add(entityClassMetaData.getIdField());
            List<Object> paramList = getSqlParamList(client, fieldList);
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), paramList);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private Object[] getObjectArgs(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Object[] args = new Object[columnCount];
        for (int i=0; i<columnCount; i++) {
            args[i] = resultSet.getObject(i+1);
        }
        return args;
    }

    private List<Object> getSqlParamList(T client, List<Field> fieldList) throws IllegalAccessException {
        List<Object> argList = new ArrayList<>();
        for (Field field: fieldList) {
            field.setAccessible(true);
            if (field.get(client) == null) {
                if (field.getType().getName().equals("java.lang.String")) {
                    argList.add("");
                }
            }
            else {
                argList.add(field.get(client));
            }
        }
        return argList;
    }

}
