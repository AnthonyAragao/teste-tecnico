package com.app.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig 
{
    private static final String PROPERTIES_FILE = "/database.properties";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException 
    {
        loadDriver();
        Properties props = loadProperties();
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }

    private static void loadDriver() 
    {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado!", e);
        }
    }

    private static Properties loadProperties() 
    {
        Properties props = new Properties();
        try (InputStream input = DatabaseConfig.class.getResourceAsStream(PROPERTIES_FILE)) {
            props.load(input);
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao carregar propriedades: " + exception.getMessage(), exception);
        }

        return props;
    }
}
