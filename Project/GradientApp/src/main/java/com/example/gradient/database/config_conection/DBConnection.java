package com.example.gradient.database.config_conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + DBConfig.DB_HOST + ":" + DBConfig.DB_PORT + "/" + DBConfig.DB_NAME;
        return DriverManager.getConnection(url, DBConfig.USER, DBConfig.PASSWORD);
    }
}
