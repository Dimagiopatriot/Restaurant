package model.dao.impl;

import model.dao.UserAuthDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.UserAuth;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserAuthDaoImpl implements UserAuthDao {

    private ConnectionManager connectionManager;

    private static final Logger LOGGER = Logger.getLogger(UserAuthDaoImpl.class);

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_ROLE = "role";

    private final static String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user_auth WHERE email = ? AND " +
            "user_password =?;";
    private final static String SELECT_BY_ID = "SELECT  * FROM  user_auth WHERE id=?;";
    private final static String UPDATE_QUERY = "UPDATE user_auth SET email=?, user_password =?, role" +
            "=? WHERE id = ?;";
    private final static String INSERT_QUERY = "INSERT INTO user_auth(email, user_password, role) " +
            "values(?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM user_auth WHERE id = ?;";

    public UserAuthDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final UserAuthDaoImpl INSTANCE = new UserAuthDaoImpl(ConnectionManager.getInstance());
    }

    public static UserAuthDaoImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Optional<UserAuth> selectByEmailPassword(String email, String password) {
        Optional<UserAuth> userAuth = Optional.empty();
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            userAuth = Optional.of(buildUserAuth(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserAuthDaoImpl.class.toString() + LogMessages.SELECT_BY_EMAIL_PASSWORD + e.getMessage());
            return userAuth;
        }
        return userAuth;
    }

    @Override
    public boolean deleteById(int id) {
        return delete(id, DELETE_QUERY, connectionManager);
    }

    @Override
    public boolean update(UserAuth userAuth) throws DaoException {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_QUERY)){

            statement.setString(1, userAuth.getEmail());
            statement.setString(2, userAuth.getPassword());
            statement.setString(3, userAuth.getRole().toString());
            statement.setInt(4, userAuth.getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserAuthDaoImpl.class.toString() + LogMessages.UPDATE + e.getMessage());
            throw new DaoException();
        }
        return updatedRow > 0;
    }

    @Override
    public boolean insert(UserAuth userAuth) throws DaoException {
        int updatedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, userAuth.getEmail());
            statement.setString(2, userAuth.getPassword());
            statement.setString(3, userAuth.getRole().toString());
            updatedRow = statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    userAuth.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserAuthDaoImpl.class.toString() +LogMessages.INSERT + e.getMessage());
        }
        return updatedRow > 0;
    }

    @Override
    public Optional<UserAuth> select(int id) throws DaoException {
        Optional<UserAuth> userAuth = Optional.empty();
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            userAuth = Optional.of(buildUserAuth(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(UserAuthDaoImpl.class.toString() + LogMessages.SELECT + e.getMessage());
            return userAuth;
        }
        return userAuth;
    }

    private UserAuth buildUserAuth(ResultSet resultSet) throws SQLException {
        return new UserAuth.Builder()
                .addId(resultSet.getInt(COLUMN_ID))
                .addEmail(resultSet.getString(COLUMN_EMAIL))
                .addPassword(resultSet.getString(COLUMN_USER_PASSWORD))
                .addRole(UserAuth.Role.valueOf(resultSet.getString(COLUMN_ROLE).toUpperCase()))
                .createUserAuth();
    }
}
