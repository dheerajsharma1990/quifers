package com.quifers.email.properties;

import com.quifers.Environment;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class PropertiesLoaderTest {

    @Test
    public void shouldLoadLocalProperties() throws Exception {
        EmailUtilProperties properties = PropertiesLoader.loadEmailUtilProperties(Environment.LOCAL);

        assertThat(properties, notNullValue());

    }

}
