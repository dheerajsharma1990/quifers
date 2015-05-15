package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import org.testng.annotations.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DomainMapperFactoryTest {

    @Test
    public void shouldGetCorrectTableName() {
        assertThat(DomainMapperFactory.getTableName(XYZ.class), is("xyz"));
    }

    @Test
    public void shouldGetCorrectColumnNames() {
        String[] columns = DomainMapperFactory.getColumnNames(XYZ.class);

        assertThat(columns.length, is(2));
        assertThat(columns[0], is("abc"));
        assertThat(columns[1], is("other_date"));
    }

    @Test
    public void shouldGetCorrectInsertSql() {
        String actualSql = DomainMapperFactory.getInsertSql(XYZ.class);

        String expectedSql = "INSERT INTO xyz (abc,other_date) VALUES (?,?)";
        assertThat(actualSql, is(expectedSql));
    }


    @Table(name = "xyz")
    private class XYZ {

        @Column(name = "abc")
        private String abc;

        private Date someDate;

        @Column(name = "other_date")
        private Date otherDate;
    }
}
