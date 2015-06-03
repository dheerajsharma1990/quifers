package com.quifers.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final SqlFilesSorter sqlFilesSorter;

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlFilesExecutor.class);

    public SqlFilesExecutor(Connection connection, SqlFilesSorter sqlFilesSorter, SqlScriptParser parser) {
        this.connection = connection;
        this.sqlFilesSorter = sqlFilesSorter;
        this.parser = parser;
    }

    public void execute(String dirName) throws IOException, SQLException {
        List<String> fileNames = getFileAsNamesFromDir(dirName);
        List<String> sqlFilesInOrder = sqlFilesSorter.getSqlFilesInOrder(fileNames);
        for (String file : sqlFilesInOrder) {;
            List<String> sqls = parser.parseSqlFile(new File(dirName, file));
            for (String sql : sqls) {
                LOGGER.info("Executing sql: [{}]", sql);
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
