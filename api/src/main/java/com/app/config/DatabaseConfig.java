package com.app.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig 
{
    public static Connection getConnection() throws SQLException 
    {
        Properties props = loadProperties();
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }

    private static Properties loadProperties() 
    {
        Properties props = new Properties();
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/database.properties")) {
            props.load(input);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return props;
    }
}
