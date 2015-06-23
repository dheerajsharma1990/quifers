package com.quifers.servlet;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandComparatorTest {

    @Test
    public void shouldReturnTrueIrrespectiveOfCase() {
        String command = "abc";
        assertThat(CommandComparator.isEqual("Abc", command), is(true));
        assertThat(CommandComparator.isEqual("abc", command), is(true));
        assertThat(CommandComparator.isEqual("abz", command), is(false));
    }
}
