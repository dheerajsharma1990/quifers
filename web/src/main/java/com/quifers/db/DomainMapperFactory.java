package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import com.quifers.domain.QuifersDomainObject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
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

    public static DbColumn[] getColumns(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<DbColumn> dbColumns = new ArrayList<>();
        for (Field field : fields) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                dbColumns.add(new DbColumn(annotation.name(), field));
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

    public static void setParameters(PreparedStatement preparedStatement, QuifersDomainObject domainObject) throws IllegalAccessException, SQLException {
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
            } else {
                throw new IllegalArgumentException("No mapper found for type:[" + type + "].");
            }
        }
    }
}
