package com.quifers.db;

import java.util.*;

public class SqlFilesSorter {

    public List<String> getSqlFilesInOrder(Collection<String> sqlFiles) {
        List<String> orderedSqls = new ArrayList<>();
        List<SqlFileFormat> fileFormats = new ArrayList<>();
        for (String sqlFile : sqlFiles) {
            int fileNumber = Integer.valueOf(sqlFile.substring(0, sqlFile.indexOf(".")).trim());
            fileFormats.add(new SqlFileFormat(fileNumber, sqlFile));
        }
        Collections.sort(fileFormats);
        for (SqlFileFormat fileFormat : fileFormats) {
            orderedSqls.add(fileFormat.getFileName());
        }
        return Collections.unmodifiableList(orderedSqls);
    }

    private class SqlFileFormat implements Comparable<SqlFileFormat> {
        int fileNumber;
        String fileName;

        public SqlFileFormat(int fileNumber, String fileName) {
            this.fileNumber = fileNumber;
            this.fileName = fileName;
        }

        private String getFileName() {
            return fileName;
        }

        @Override
        public int compareTo(SqlFileFormat file) {
            return fileNumber - file.fileNumber;
        }
    }

}
