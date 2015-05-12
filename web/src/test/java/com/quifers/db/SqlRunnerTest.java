package com.quifers.db;

import org.h2.tools.Server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SqlRunnerTest {


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Server server = Server.createTcpServer("-tcpPort","9123");
        server.start();

        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9123/~/quifersdb");

        List<String> sqls = new SqlScriptParser().parseSqlFile(new File("./src/main/resources/sql/1.createClientTable.sql"));
        for(String sql : sqls) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
        }
        server.stop();
        server.shutdown();
    }
}
