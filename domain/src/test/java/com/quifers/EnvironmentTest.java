package com.quifers;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EnvironmentTest {

    private Environment environment = Environment.getEnvironment("local");

    @Test
    public void shouldReturnCorrectPropertyFilePath() {
        //when
        String pathToPropertyFile = environment.getPropertiesFilePath("myFile.properties");

        //then
        assertThat(pathToPropertyFile, is("properties/local/myFile.properties"));
    }
}