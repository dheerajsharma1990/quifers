package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DomainMapperFactory {

    public static String getTableName(Class clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        return annotation.name();
    }

    public static String[] getColumnNames(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> columns = new ArrayList<>();
        for (Field field : fields) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                columns.add(annotation.name());
            }
        }
        return columns.toArray(new String[0]);
    }

    public static String getInsertSql(Class clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(getTableName(clazz)).append(" ");

        builder.append("(");
        String columns[] = getColumnNames(clazz);
        int totalColumns = columns.length;
        for (int i = 0; i < totalColumns - 1; i++) {
            builder.append(columns[i]).append(",");
        }
        builder.append(columns[totalColumns - 1]);
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
}
