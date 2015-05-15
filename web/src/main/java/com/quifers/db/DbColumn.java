package com.quifers.db;

import java.lang.reflect.Field;

public class DbColumn {

    private String columnName;
    private Field field;

    public DbColumn(String columnName,Field field) {
        this.columnName = columnName;
        this.field = field;
    }

    public String getColumnName() {
        return columnName;
    }

    public Field getField() {
        return field;
    }
}
