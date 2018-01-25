package model.dao.impl;

import model.dao.OrderDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.Order;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);

    //columns
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_USER_ID = "user_id";
    private final static String COLUMN_STATUS = "status";
    private final static String COLUMN_HAS_BILL = "has_bill";

    //queries
    private final static String INSERT_QUERY = "insert into `order`(user_id, status, has_bill) values (?, ?, ?);";

    private final static String SELECT_BY_ID_QUERY = "select * from `order` where id = ?";
    private final static String SELECT_BY_STATUS_QUERY = "select * from `order` where status=?";
    private final static String SELECT_ALL_QUERY = "select * from `order`";
    private final static String SELECT_BY_USER_ID_QUERY = "select * from `order` where user_id = ?";

    private final static String UPDATE_QUERY = "update `order` set status=?, has_bill=? where id=?";
    private final static String UPDATE_STATUS_QUERY = "update `order` set status=? where id=?";
    private final static String UPDATE_HAS_BILL_QUERY = "update `order` set has_bill=? where id=?";

    private final static String LIMIT = " limit ?,?;";

    public OrderDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final OrderDaoImpl INSTANCE = new OrderDaoImpl(ConnectionManager.getInstance());
    }

    public static OrderDaoImpl getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public List<Order> selectByStatus(Order.Status status, int offset, int limit) {
        String query = SELECT_BY_STATUS_QUERY + LIMIT;
        List<Order> orders = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status.toString());
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Order order = buildOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_BY_STATUS + e.getMessage());
            throw new DaoException();
        }
        return orders;
    }

    @Override
    public List<Order> selectAll(int offset, int limit) {
        String query = SELECT_ALL_QUERY + LIMIT;
        List<Order> orders = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Order order = buildOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_ALL + e.getMessage());
            throw new DaoException();
        }
        return orders;
    }

    @Override
    public List<Order> selectByUserId(int userId, int offset, int limit) {
        String query = SELECT_BY_USER_ID_QUERY + LIMIT;
        List<Order> orders = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Order order = buildOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_BY_USER_ID + e.getMessage());
            throw new DaoException();
        }
        return orders;
    }

    @Override
    public boolean updateStatus(Order order) {
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_QUERY)) {
            statement.setString(1, order.getStatus().toString());
            statement.setInt(2, order.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE_STATUS + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public boolean updateBillPresence(Order order){
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_HAS_BILL_QUERY)) {
            statement.setBoolean(1, order.isBillPresentForOrder());
            statement.setInt(2, order.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE_BILL_PRESENCE + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, order.getStatus().toString());
            statement.setBoolean(2, order.isBillPresentForOrder());
            statement.setInt(3, order.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, order.getStatus().toString());
            preparedStatement.setBoolean(3, order.isBillPresentForOrder());
            insertedRow = preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    order.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }

    @Override
    public Optional<Order> select(int id) throws DaoException {
        Optional<Order> order;
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            order = Optional.of(buildOrder(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(OrderDaoImpl.class.toString() + LogMessages.SELECT + e.getMessage());
            throw new DaoException();
        }

        return order;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        return new Order.Builder()
                .addId(resultSet.getInt(COLUMN_ID))
                .addStatus(Order.Status.valueOf(resultSet.getString(COLUMN_STATUS)))
                .addIsBillPresentForOrder(resultSet.getBoolean(COLUMN_HAS_BILL))
                .createOrder();
    }
}
