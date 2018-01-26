package model.dao.impl;

import model.dao.ManyToOneDishDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.Dish;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class ManyToOneDishDaoImpl implements ManyToOneDishDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(ManyToOneDishDaoImpl.class);

    //queries
    private final static String INSERT_INTO_M2O_DISH_ORDER_QUERY = "insert into m2o_dish_order(dish_id, portions, order_id) values ";
    private final static String INSERT_INTO_M2O_DISH_BILL_QUERY = "insert into m2o_dish_bill(dish_id, portions, bill_id) values ";

    private final static String VALUES_FIELD = "(?,?,?)";
    private final static String SEPARATOR = ", ";

    public ManyToOneDishDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final ManyToOneDishDaoImpl INSTANCE = new ManyToOneDishDaoImpl(ConnectionManager.getInstance());
    }

    public static ManyToOneDishDaoImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean insertIntoManyToOneDishOrderTable(Map<Dish, Integer> portionsToDishMap, int orderId) {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createQuery(INSERT_INTO_M2O_DISH_ORDER_QUERY, portionsToDishMap))) {

            setUpPreparedStatementParams(portionsToDishMap, preparedStatement, orderId);
            insertedRow = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT_INTO_M2O_DISH_ORDER + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean insertIntoManyToOneDishBillTable(Map<Dish, Integer> portionsToDishMap, int billId) {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createQuery(INSERT_INTO_M2O_DISH_BILL_QUERY, portionsToDishMap))) {

            setUpPreparedStatementParams(portionsToDishMap, preparedStatement, billId);
            insertedRow = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT_INTO_M2O_DISH_BILL + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }

    private String createQuery(String startQuery, Map<Dish, Integer> portionsToDishMap) {
        StringBuilder query = new StringBuilder(startQuery);
        int separatorCounter = portionsToDishMap.size() - 1;

        for (int i = 0; i < portionsToDishMap.size(); i++) {
            query.append(VALUES_FIELD);
            if (separatorCounter > 0) {
                query.append(SEPARATOR);
                separatorCounter--;
            }
        }
        return query.toString();
    }

    private void setUpPreparedStatementParams(Map<Dish, Integer> portionsToDishMap, PreparedStatement preparedStatement, int id) throws SQLException {
        int parameterIndex = 1;
        for (Map.Entry<Dish, Integer> entry : portionsToDishMap.entrySet()) {
            preparedStatement.setInt(parameterIndex, entry.getKey().getId());
            parameterIndex++;
            preparedStatement.setInt(parameterIndex, entry.getValue());
            parameterIndex++;
            preparedStatement.setInt(parameterIndex, id);
            parameterIndex++;
        }
    }
}
