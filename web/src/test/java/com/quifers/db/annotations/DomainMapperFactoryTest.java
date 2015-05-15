package com.quifers.db.annotations;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DomainMapperFactoryTest {

    @Test
    public void shouldGetCorrectTableName() {
        assertThat(DomainMapperFactory.getTableName(XYZ.class), is("xyz"));
    }


    @Table(name = "xyz")
    private class XYZ {

    }
}
