package com.quifers.db;

import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

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

        DomainMapperFactory.setInsertParameters(preparedStatement, object);

        verify(preparedStatement, times(1)).setString(1, "abc");
        verify(preparedStatement, times(1)).setTimestamp(2, new Timestamp(date.getTime()));
        verify(preparedStatement, times(1)).setInt(3, 4);

    }

    @Test
    public void shouldGetCorrectSelectSql() throws Exception {

        String actualSql = DomainMapperFactory.getCreateSql(XYZ.class, new DbColumn(XYZ.class.getDeclaredField("abc")));
        String expectedSql = "SELECT abc,other_date,count FROM xyz WHERE abc = ?";
        assertThat(actualSql, is(expectedSql));
    }

    @Test
    public void shouldGetMappedObject() throws Exception {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("abc")).thenReturn("abcValue");
        when(resultSet.getTimestamp("other_date")).thenReturn(timestamp);
        when(resultSet.getInt("count")).thenReturn(4);

        List<XYZ> quifersDomainObjects = DomainMapperFactory.mapObjects(resultSet, XYZ.class);

        assertThat(quifersDomainObjects.size(), is(1));
        XYZ xyz = quifersDomainObjects.iterator().next();
        assertThat(xyz.getAbc(), is("abcValue"));
        assertThat(xyz.getOtherDate(), is(new Date(timestamp.getTime())));
        assertThat(xyz.getCount(), is(4));
    }


}
