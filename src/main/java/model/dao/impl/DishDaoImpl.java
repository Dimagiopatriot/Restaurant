package model.dao.impl;

import model.dao.DishDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.Dish;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DishDaoImpl implements DishDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(DishDaoImpl.class);

    //columns
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_DESCRIPTION = "description";
    private final static String COLUMN_GRAM = "gram";
    private final static String COLUMN_PRICE = "price";
    private final static String COLUMN_DISH_TYPE = "dish_type";

    private final static String COLUMN_PORTIONS = "portions";

    //queries
    private final static String SELECT_BY_ID_QUERY = "select * from dish where id=?";
    private final static String SELECT_BY_DISH_TYPE = "select * from dish where dish_type = ?;";
    private final static String UPDATE_QUERY = "update dish set name=?, description=?, gram=?, price=?, dish_type=? " +
            "where id=?;";
    private final static String INSERT_QUERY = "insert into dish(name, description, gram, price, dish_type) " +
            "values(?,?,?,?,?)";
    private final static String SELECT_FOR_BILL_QUERY = "select t1.*, t2.portions from " +
            "(select * from dish where id in " +
            "(select dish_id from m2o_dish_bill where bill_id = ?))t1 " +
            "join m2o_dish_bill t2 on t2.dish_id=t1.id and t2.bill_id=?;";
    private final static String SELECT_FOR_ORDER_QUERY = "select t1.*, t2.portions from " +
            "(select * from dish where id in " +
            "(select dish_id from m2o_dish_order where order_id = ?))t1 " +
            "join m2o_dish_order t2 on t1.id=t2.dish_id and t2.order_id=?;";

    public DishDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final DishDaoImpl INSTANCE = new DishDaoImpl(ConnectionManager.getInstance());
    }

    public static DishDaoImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean update(Dish dish) throws DaoException {
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, dish.getName());
            statement.setString(2, dish.getDescription());
            statement.setFloat(3, dish.getPortionInGrams());
            statement.setFloat(4, dish.getPricePerPortion());
            statement.setString(5, dish.getDishType().toString());
            statement.setInt(6, dish.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public boolean insert(Dish dish) throws DaoException {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getDescription());
            preparedStatement.setFloat(3, dish.getPortionInGrams());
            preparedStatement.setFloat(4, dish.getPricePerPortion());
            preparedStatement.setString(5, dish.getDishType().toString());
            insertedRow = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.INSERT + e.getMessage());
            throw new DaoException();
        }
        return insertedRow > 0;
    }

    @Override
    public Optional<Dish> select(int id) throws DaoException {
        Optional<Dish> dish;
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            dish = Optional.of(buildDish(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(OrderDaoImpl.class.toString() + LogMessages.SELECT + e.getMessage());
            throw new DaoException();
        }
        return dish;
    }

    @Override
    public Map<Dish, Integer> selectDishesForBill(int billId){
        Map<Dish, Integer> dishes = new HashMap<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FOR_BILL_QUERY)) {
            statement.setInt(1, billId);
            statement.setInt(2, billId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Dish dish = buildDish(resultSet);
                    int billPortions = resultSet.getInt(COLUMN_PORTIONS);
                    dishes.put(dish, billPortions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_FOR_BILL + e.getMessage());
            throw new DaoException();
        }
        return dishes;
    }

    @Override
    public Map<Dish, Integer> selectDishesForOrder(int orderId){
        Map<Dish, Integer> dishes = new HashMap<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ORDER_QUERY)) {
            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Dish dish = buildDish(resultSet);
                    int orderPortions = resultSet.getInt(COLUMN_PORTIONS);
                    dishes.put(dish, orderPortions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_FOR_ORDER + e.getMessage());
            throw new DaoException();
        }
        return dishes;
    }

    @Override
    public List<Dish> selectByDishType(String dishType) {
        List<Dish> orders = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DISH_TYPE)) {
            statement.setString(1, dishType);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Dish dish = buildDish(resultSet);
                    orders.add(dish);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_BY_DISH_TYPE + e.getMessage());
            throw new DaoException();
        }
        return orders;
    }

    private Dish buildDish(ResultSet resultSet) throws SQLException {
        return new Dish.Builder()
                .addName(resultSet.getString(COLUMN_NAME))
                .addDescription(resultSet.getString(COLUMN_DESCRIPTION))
                .addPortionInGrams(resultSet.getFloat(COLUMN_GRAM))
                .addPricePerPortion(resultSet.getFloat(COLUMN_PRICE))
                .addDishType(Dish.DishType.valueOf(resultSet.getString(COLUMN_DISH_TYPE)))
                .createDish();
    }
}
