package model.dao.impl;

import model.dao.UserDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.User;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private final static String COLUMN_ID = "id";
    private final static String COLUMN_USER_NAME = "user_name";
    private final static String COLUMN_USER_SURNAME = "user_surname";
    private final static String COLUMN_PHONE = "phone";
    private final static String COLUMN_COUNT = "count";

    private final static String INSERT_QUERY = "INSERT INTO user(id, user_name, user_surname, phone, count) " +
            "values(?, ?, ?, ?, ?);";
    private final static String SELECT_QUERY_BY_ID = "SELECT * FROM user WHERE id=?;";
    private final static String UPDATE_QUERY = "UPDATE user SET user_name=?, user_surname=?, phone=?, count=?" +
            "WHERE id=?";
    private final static String UPDATE_USER_PHONE_QUERY = "UPDATE user SET phone=? WHERE id=?";
    private final static String UPDATE_USER_COUNT_QUERY = "UPDATE user SET count=? WHERE id=?";

    UserDaoImpl(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final UserDaoImpl INSTANCE = new UserDaoImpl(ConnectionManager.getInstance());
    }

    public static UserDaoImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean updateUserPhone(User user) {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_USER_PHONE_QUERY)){

            statement.setString(1, user.getPhone());
            statement.setInt(2, user.getUserAuth().getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserDaoImpl.class.toString() + LogMessages.UPDATE_USER_PHONE + e.getMessage());
            throw new DaoException();
        }
        return updatedRow > 0;
    }

    @Override
    public boolean updateUserCount(User user) {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_USER_COUNT_QUERY)){

            statement.setFloat(1, user.getCount());
            statement.setInt(2, user.getUserAuth().getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserDaoImpl.class.toString() + LogMessages.UPDATE_USER_COUNT + e.getMessage());
            throw new DaoException();
        }
        return updatedRow > 0;
    }

    @Override
    public boolean update(User user) throws DaoException {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_QUERY)){

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setFloat(4, user.getCount());
            statement.setInt(5, user.getUserAuth().getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserDaoImpl.class.toString() + LogMessages.UPDATE + e.getMessage());
            throw new DaoException();
        }
        return updatedRow > 0;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, user.getUserAuth().getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPhone());
            statement.setFloat(5, user.getCount());

            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserDaoImpl.class.toString() + LogMessages.INSERT + e.getMessage());
            throw new DaoException();
        }
        return updatedRow > 0;
    }

    @Override
    public Optional<User> select(int id) throws DaoException {
        Optional<User> user = Optional.empty();
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_QUERY_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            user = Optional.of(buildUser(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserDaoImpl.class.toString() + LogMessages.SELECT + e.getMessage());
            throw new DaoException();
        }
        return user;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .addName(resultSet.getString(COLUMN_USER_NAME))
                .addSurname(resultSet.getString(COLUMN_USER_SURNAME))
                .addPhone(resultSet.getString(COLUMN_PHONE))
                .addCount(resultSet.getFloat(COLUMN_COUNT))
                .createUser();
    }
}
