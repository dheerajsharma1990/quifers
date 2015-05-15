package com.quifers.db;

import com.quifers.db.annotations.Column;

import java.lang.reflect.Field;

public class DbColumn {

    private String columnName;
    private Field field;

    public DbColumn(Field field) {
        this.columnName = field.getAnnotation(Column.class).name();
        this.field = field;
    }

    public String getColumnName() {
        return columnName;
    }

    public Field getField() {
        return field;
    }
}
