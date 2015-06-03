package com.quifers.email.util;

import com.quifers.Environment;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

public class EnvironmentPresentTest {

    @Test(dataProvider = "propertyDirs")
    public void shouldHaveEnvironmentPresentForPropertyFolder(String env) throws Exception {
        try {
            Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("No such enum defined in Environment class. [" + env + "]");
        }
    }

    @DataProvider(name = "propertyDirs")
    public String[][] allPropertyDirs() throws Exception {
        URL properties = EnvironmentPresentTest.class.getClassLoader().getResource("properties");
        File propertyDir = new File(properties.toURI());
        File[] environmentDirs = propertyDir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        String[][] environmentDirsName = new String[environmentDirs.length][];
        for (int i = 0; i < environmentDirs.length; i++) {
            environmentDirsName[i] = new String[]{environmentDirs[i].getName()};
        }
        return environmentDirsName;
    }

}
