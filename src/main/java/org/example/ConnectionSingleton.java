package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static Connection connection;
    private static final String NAME = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        if (connection==null){
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", NAME, PASSWORD);
        }
        return connection;
    }


}
