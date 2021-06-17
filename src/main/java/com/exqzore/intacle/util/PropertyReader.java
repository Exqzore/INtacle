package com.exqzore.intacle.util;

import com.exqzore.intacle.exception.PropertyReaderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private final static Logger logger = LogManager.getLogger();

    private PropertyReader() {
    }

    public static Properties read(String path) throws PropertyReaderException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            logger.log(Level.ERROR, "Property read error", exception);
            throw new PropertyReaderException(exception);
        }
    }
}
