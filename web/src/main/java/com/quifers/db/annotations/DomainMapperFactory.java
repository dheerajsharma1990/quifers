package com.quifers.db.annotations;

public class DomainMapperFactory {
    public static String getTableName(Class clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        return annotation.name();
    }
}
