package com.quifers.db;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class SqlDirectoryReader {
    public List<File> getSqlFilesInSequence(File dir) {
        List<File> files = new ArrayList<>();
        for (File file : dir.listFiles(getSqlFileFilter())) {
            files.add(file);
        }
        return files;
    }


    private FileFilter getSqlFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".sql;");
            }
        };
    }
}
