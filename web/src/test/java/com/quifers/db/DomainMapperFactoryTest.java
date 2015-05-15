package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import com.quifers.domain.QuifersDomainObject;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DomainMapperFactoryTest {

    @Test
    public void shouldGetCorrectTableName() {
        assertThat(DomainMapperFactory.getTableName(XYZ.class), is("xyz"));
    }

    @Test
    public void shouldGetCorrectColumnNames() {
        DbColumn[] dbColumns = DomainMapperFactory.getColumns(XYZ.class);

        assertThat(dbColumns.length, is(3));
        assertThat(dbColumns[0].getColumnName(), is("abc"));
        assertThat(dbColumns[1].getColumnName(), is("other_date"));
        assertThat(dbColumns[2].getColumnName(), is("count"));
    }

    @Test
    public void shouldGetCorrectInsertSql() {
        String actualSql = DomainMapperFactory.getInsertSql(XYZ.class);

        String expectedSql = "INSERT INTO xyz (abc,other_date,count) VALUES (?,?,?)";
        assertThat(actualSql, is(expectedSql));
    }

    @Test
    public void shouldSetParametersCorrectly() throws Exception {
        Date date = new Date();
        XYZ object = new XYZ("abc", date, date, 4);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        DomainMapperFactory.setParameters(preparedStatement, object);

        verify(preparedStatement, times(1)).setString(1, "abc");
        verify(preparedStatement, times(1)).setTimestamp(2, new Timestamp(date.getTime()));
        verify(preparedStatement, times(1)).setInt(3, 4);

    }


    @Table(name = "xyz")
    private class XYZ implements QuifersDomainObject {

        @Column(name = "abc")
        private String abc;

        private Date someDate;

        @Column(name = "other_date")
        private Date otherDate;

        @Column(name = "count")
        private int count;

        public XYZ(String abc, Date someDate, Date otherDate, int count) {
            this.abc = abc;
            this.someDate = someDate;
            this.otherDate = otherDate;
            this.count = count;
        }
    }
}
