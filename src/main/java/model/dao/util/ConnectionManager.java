package model.dao.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionManager {
    private DataSource dataSource;
    private static JdbcConnection jdbcConnection;

    private ConnectionManager() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/restaurantDB");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class Holder {
        static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized JdbcConnection getConnection() {
        try {
            return new JdbcConnection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTransaction() {
        JdbcConnection connection = getConnection();
        connection.startTransaction();
        jdbcConnection = connection;
    }

    public void commit() {
        JdbcConnection connection = jdbcConnection;
        connection.commit();
        close(connection);
    }

    public void rollback() {
        JdbcConnection connection = jdbcConnection;
        connection.rollback();
        close(connection);
    }

    private void close(JdbcConnection connection) {
        jdbcConnection.close();
        connection.close();
    }
}
