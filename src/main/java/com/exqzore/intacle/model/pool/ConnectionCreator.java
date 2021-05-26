package com.exqzore.intacle.model.pool;

import com.exqzore.intacle.exception.PropertyReaderException;
import com.exqzore.intacle.util.PropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger(ConnectionCreator.class);

    private final static String DATA_BASE_PROPERTY_PATH = "properties/database.properties";
    private final static String DATA_BASE_URL = "url";
    private final static String DATA_BASE_DRIVER = "driverClassName";

    private static final Properties properties;

    static {
        try {
            properties = PropertyReader.read(DATA_BASE_PROPERTY_PATH);
            String driver = properties.getProperty(DATA_BASE_DRIVER);
            Class.forName(driver);
        } catch (PropertyReaderException | ClassNotFoundException exception) {
            logger.log(Level.ERROR, "Property read error", exception);
            throw new RuntimeException(exception);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() throws SQLException {
        String url = properties.getProperty(DATA_BASE_URL);
        return DriverManager.getConnection(url, properties);
    }
}
