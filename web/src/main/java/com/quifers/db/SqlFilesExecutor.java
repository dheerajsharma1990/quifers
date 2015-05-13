package com.quifers.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlFilesExecutor {

    private final Connection connection;
    private final SqlScriptParser parser;

    public SqlFilesExecutor(Connection connection, SqlScriptParser parser) {
        this.connection = connection;
        this.parser = parser;
    }

    public void execute(String dirName) throws IOException, SQLException {
        List<String> fileNames = getFileAsNamesFromDir(dirName);
        for (String file : fileNames) {
            List<String> sqls = parser.parseSqlFile(new File(dirName, file));
            for (String sql : sqls) {
                executeSql(sql);
            }
        }
    }

    private void executeSql(String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    private List<String> getFileAsNamesFromDir(String dirName) {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(dirName);
        for (File f : dir.listFiles()) {
            fileNames.add(f.getName());
        }
        return fileNames;
    }
}
