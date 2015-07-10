package com.quifers.db;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SqlFilesSorterTest {

    private final SqlFilesSorter sorter = new SqlFilesSorter();

    @Test
    public void shouldSortMoreThanOneFile() {
        //given
        List<String> files = new ArrayList<>();
        files.add("47.file3");
        files.add("1.file1");
        files.add("23.file2");

        //when
        List<String> sqlFilesInOrder = sorter.getSqlFilesInOrder(files);

        //then
        assertThat(sqlFilesInOrder.size(), is(3));
        assertThat(sqlFilesInOrder.get(0), is("1.file1"));
        assertThat(sqlFilesInOrder.get(1), is("23.file2"));
        assertThat(sqlFilesInOrder.get(2), is("47.file3"));

    }

    @Test
    public void shouldSortSingleFile() {
        //given
        List<String> files = new ArrayList<>();
        files.add("1.file1");

        //when
        List<String> sqlFilesInOrder = sorter.getSqlFilesInOrder(files);

        //then
        assertThat(sqlFilesInOrder.size(), is(1));
        assertThat(sqlFilesInOrder.get(0), is("1.file1"));

    }

    @Test
    public void shouldSortNoFile() {
        //given
        List<String> files = new ArrayList<>();

        //when
        List<String> sqlFilesInOrder = sorter.getSqlFilesInOrder(files);

        //then
        assertThat(sqlFilesInOrder.size(), is(0));
    }

}
