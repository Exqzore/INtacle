package com.exqzore.intacle.dao.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private final static int CONNECTION_POOL_SIZE = 10;

    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(CONNECTION_POOL_SIZE);
        busyConnections = new LinkedBlockingDeque<>();
        try {
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                freeConnections.add(new ProxyConnection(ConnectionCreator.createConnection()));
            }
        } catch (SQLException exception) {
            logger.log(Level.FATAL, "Connection creation error", exception);
            throw new RuntimeException(exception);
        }
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.offer(connection);
        } catch (InterruptedException exception) {
            logger.log(Level.ERROR, "Error getting connection", exception);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && busyConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
            logger.log(Level.INFO, "Connection has been released");
        } else {
            logger.log(Level.ERROR, "Connection release error");
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i < freeConnections.size(); i++) {
                freeConnections.take().reallyClose();
            }
            for (int i = 0; i < busyConnections.size(); i++) {
                busyConnections.take().reallyClose();
            }
            while (DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
        } catch (InterruptedException | SQLException exception) {
            logger.log(Level.ERROR, "Error destroying connection pool", exception);
            Thread.currentThread().interrupt();
        }
    }
}
