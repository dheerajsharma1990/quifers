package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import com.quifers.domain.OrderState;
import com.quifers.domain.QuifersDomainObject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DomainMapperFactory {

    public static String getTableName(Class clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        return annotation.name();
    }

    public static DbColumn getColumn(Class clazz,String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        return new DbColumn(field);
    }

    public static DbColumn[] getColumns(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<DbColumn> dbColumns = new ArrayList<>();
        for (Field field : fields) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                dbColumns.add(new DbColumn(field));
            }
        }
        return dbColumns.toArray(new DbColumn[0]);
    }

    public static String getInsertSql(Class clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(getTableName(clazz)).append(" ");

        builder.append("(");
        DbColumn dbColumns[] = getColumns(clazz);
        int totalColumns = dbColumns.length;
        for (int i = 0; i < totalColumns - 1; i++) {
            builder.append(dbColumns[i].getColumnName()).append(",");
        }
        builder.append(dbColumns[totalColumns - 1].getColumnName());
        builder.append(")").append(" ");

        builder.append("VALUES").append(" ");
        builder.append("(");
        for (int i = 0; i < totalColumns - 1; i++) {
            builder.append("?").append(",");
        }
        builder.append("?");
        builder.append(")");
        return builder.toString();
    }

    public static void setInsertParameters(PreparedStatement preparedStatement, QuifersDomainObject domainObject) throws IllegalAccessException, SQLException {
        Class<? extends QuifersDomainObject> clazz = domainObject.getClass();
        DbColumn[] dbColumns = getColumns(clazz);
        for (int i = 1; i <= dbColumns.length; i++) {
            DbColumn column = dbColumns[i - 1];
            Field field = column.getField();
            Class<?> type = field.getType();
            field.setAccessible(true);
            if (type.equals(String.class)) {
                String value = (String) field.get(domainObject);
                preparedStatement.setString(i, value);
            } else if (type.equals(Date.class)) {
                Date date = (Date) field.get(domainObject);
                preparedStatement.setTimestamp(i, new Timestamp(date.getTime()));
            } else if (type.equals(int.class)) {
                int value = field.getInt(domainObject);
                preparedStatement.setInt(i, value);
            } else if (type.equals(long.class)) {
                long value = field.getLong(domainObject);
                preparedStatement.setLong(i, value);
            } else if (type.equals(OrderState.class)) {
                OrderState value = (OrderState) field.get(domainObject);
                preparedStatement.setObject(i, value.toString());
            } else {
                throw new IllegalArgumentException("No mapper found for type:[" + type + "].");
            }
        }
    }

    public static String getCreateSql(Class clazz, DbColumn dbColumn) {
        StringBuilder builder = new StringBuilder();
        DbColumn[] columns = getColumns(clazz);
        builder.append("SELECT ");
        int totalColumns = columns.length;
        for (int i = 0; i < totalColumns - 1; i++) {
            builder.append(columns[i].getColumnName()).append(",");
        }
        builder.append(columns[totalColumns - 1].getColumnName());
        builder.append(" ").append("FROM").append(" ");
        builder.append(getTableName(clazz)).append(" ");
        builder.append("WHERE ").append(dbColumn.getColumnName()).append(" =").append(" ?");

        return builder.toString();
    }


    public static void createSelectStatement(PreparedStatement preparedStatement, DbColumn dbColumn, Object value) throws SQLException {
        Field field = dbColumn.getField();
        Class<?> type = field.getType();
        if (type.equals(String.class)) {
            String string = (String) value;
            preparedStatement.setString(1, string);
        } else if (type.equals(Date.class)) {
            Date date = (Date) value;
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
        } else if (type.equals(int.class)) {
            int integer = (int) value;
            preparedStatement.setInt(1, integer);
        } else if (type.equals(long.class)) {
            long longValue = (long) value;
            preparedStatement.setLong(1, longValue);
        } else if (type.equals(OrderState.class)) {
            preparedStatement.setObject(1, value.toString());
        } else {
            throw new IllegalArgumentException("No mapper found for type:[" + type + "].");
        }
    }

    public static <T extends QuifersDomainObject> List<T> mapObjects(ResultSet resultSet, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        DbColumn[] columns = getColumns(clazz);
        List<T> domainObjects = new ArrayList<>();
        while (resultSet.next()) {
            T domainObject = clazz.newInstance();
            for (DbColumn column : columns) {
                Field field = column.getField();
                Class<?> type = field.getType();
                field.setAccessible(true);
                if (type.equals(String.class)) {
                    String value = resultSet.getString(column.getColumnName());
                    field.set(domainObject, value);
                } else if (type.equals(Date.class)) {
                    Date value = new Date(resultSet.getTimestamp(column.getColumnName()).getTime());
                    field.set(domainObject, value);
                } else if (type.equals(int.class)) {
                    int value = resultSet.getInt(column.getColumnName());
                    field.setInt(domainObject, value);
                } else if (type.equals(long.class)) {
                    long value = resultSet.getLong(column.getColumnName());
                    field.setLong(domainObject, value);
                } else if (type.equals(OrderState.class)) {
                    String object = resultSet.getString(column.getColumnName());
                    field.set(domainObject, OrderState.valueOf(object));
                } else {
                    throw new IllegalArgumentException("No mapper found for type:[" + type + "].");
                }
            }
            domainObjects.add(domainObject);
        }
        return domainObjects;
    }


}
