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
}
