package model.dao.impl;

import model.dao.ManyToOneDishDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManyToOneDishDaoImpl implements ManyToOneDishDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(ManyToOneDishDaoImpl.class);

    //queries
    private final static String INSERT_INTO_M2O_DISH_ORDER_QUERY = "insert into m2o_dish_order(dish_id, portions, order_id) values (?, ?, ?);";
    private final static String INSERT_INTO_M2O_DISH_BILL_QUERY = "insert into m2o_dish_bill(dish_id, portions, bill_id) values (?, ?, ?);";

    public ManyToOneDishDaoImpl(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    private static class Holder{
        static final ManyToOneDishDaoImpl INSTANCE = new ManyToOneDishDaoImpl(ConnectionManager.getInstance());
    }

    public static ManyToOneDishDaoImpl getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public boolean insertIntoManyToOneDishOrderTable(int dishId, int portions, int orderId) {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_M2O_DISH_ORDER_QUERY)) {
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, portions);
            preparedStatement.setInt(3, orderId);
            insertedRow = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT_INTO_M2O_DISH_ORDER + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }

    @Override
    public boolean insertIntoManyToOneDishBillTable(int dishId, int portions, int billId) {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_M2O_DISH_BILL_QUERY)) {
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, portions);
            preparedStatement.setInt(3, billId);
            insertedRow = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT_INTO_M2O_DISH_BILL + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }
}
