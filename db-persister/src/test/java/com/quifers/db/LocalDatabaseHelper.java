package com.quifers.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class LocalDatabaseHelper {

    private final Connection connection;

    public LocalDatabaseHelper(Connection connection) {
        this.connection = connection;
    }

    public void executeSQLs() throws IOException, SQLException {
        connection.prepareStatement("DROP ALL OBJECTS").execute();
        SqlFilesSorter sqlFilesSorter = new SqlFilesSorter();
        SqlFilesExecutor executor = new SqlFilesExecutor(connection, sqlFilesSorter, new SqlScriptParser());
        executor.execute("../db-persister/src/main/resources/sql");
    }


    public void cleanAllTables() throws SQLException {
        disableReferentialIntegrity();
        Collection<String> allTables = new ArrayList<>();
        populateAllTables(allTables);
        deleteRowsFromTables(allTables);
        enableReferentialIntegrity();
    }

    private void deleteRowsFromTables(Collection<String> allTables) throws SQLException {
        for (String tableName : allTables) {
            String sql = "TRUNCATE TABLE " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
        }
    }

    private void populateAllTables(Collection<String> allTables) throws SQLException {
        String sql = "SHOW TABLES";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            allTables.add(resultSet.getString(1));
        }
    }

    private void disableReferentialIntegrity() throws SQLException {
        String sql = "SET REFERENTIAL_INTEGRITY FALSE";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }


    private void enableReferentialIntegrity() throws SQLException {
        String sql = "SET REFERENTIAL_INTEGRITY TRUE";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

}
