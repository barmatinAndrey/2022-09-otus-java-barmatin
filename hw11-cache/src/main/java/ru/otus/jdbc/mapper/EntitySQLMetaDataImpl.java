package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sql = new StringBuilder("SELECT ");
        List<Field> fieldList = entityClassMetaData.getAllFields();
        for (int i=0; i<fieldList.size(); i++) {
            String fieldName = fieldList.get(i).getName();
            sql.append(i == fieldList.size()-1 ? fieldName + " " : fieldName + ", ");
        }
        sql.append("FROM ");
        sql.append(entityClassMetaData.getName());
        return sql.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sql = new StringBuilder("SELECT ");
        List<Field> fieldList = entityClassMetaData.getAllFields();
        for (int i=0; i<fieldList.size(); i++) {
            String fieldName = fieldList.get(i).getName();
            sql.append(i == fieldList.size()-1 ? fieldName + " " : fieldName + ", ");
        }
        sql.append("FROM ");
        sql.append(entityClassMetaData.getName());
        sql.append(" WHERE ");
        sql.append(entityClassMetaData.getIdField().getName());
        sql.append(" = ?");
        return sql.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(entityClassMetaData.getName());
        sql.append(" (");
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        for (int i=0; i<fieldList.size(); i++) {
            String fieldName = fieldList.get(i).getName();
            sql.append(i == fieldList.size()-1 ? fieldName : fieldName + ", ");
        }
        sql.append(") VALUES (");
        for (int i=0; i<fieldList.size(); i++) {
            sql.append(i == fieldList.size()-1 ? "?)" : "?, ");
        }
        return sql.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(entityClassMetaData.getName());
        sql.append(" SET ");
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        for (int i=0; i<fieldList.size(); i++) {
            String fieldName = fieldList.get(i).getName();
            sql.append(fieldName);
            sql.append(" = ?");
            sql.append(i == fieldList.size()-1 ? " " : ", ");
        }
        sql.append("WHERE ");
        sql.append(entityClassMetaData.getIdField().getName());
        sql.append(" = ?");
        return sql.toString();
    }
}
