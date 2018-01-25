package model.dao.impl;

import model.dao.BillDao;
import model.dao.util.ConnectionManager;
import model.dao.util.JdbcConnection;
import model.entity.Bill;
import org.apache.log4j.Logger;
import util.constant.LogMessages;
import util.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillDaoImpl implements BillDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(BillDaoImpl.class);

    //columns
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_TOTAL = "total";
    private final static String COLUMN_STATUS = "status";

    //queries
    private final static String INSERT_QUERY = "insert into bill(id, total, status) values(?, ?, ?);";

    private final static String SELECT_BY_ID_QUERY = "select * from bill where id = ?";
    private final static String SELECT_BY_STATUS_QUERY = "select * from bill where status=?";
    private final static String SELECT_ALL_QUERY = "select * from bill";
    private final static String SELECT_BY_USER_ID_QUERY = "select * from bill where id in (select id from `order` where user_id=?)";

    private final static String UPDATE_QUERY = "update bill set status=?, total=? where id=?";
    private final static String UPDATE_STATUS_QUERY = "update bill set status=? where id=?";

    private final static String LIMIT = " limit ?,?;";

    public BillDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        final static BillDaoImpl INSTANCE = new BillDaoImpl(ConnectionManager.getInstance());
    }

    public static BillDaoImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean updateStatus(Bill bill) {
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_QUERY)) {
            statement.setString(1, bill.getStatus().toString());
            statement.setInt(2, bill.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE_STATUS + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public List<Bill> selectByStatus(Bill.Status status, int offset, int limit) {
        String query = SELECT_BY_STATUS_QUERY + LIMIT;
        List<Bill> bills = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status.toString());
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Bill bill = buildBill(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_BY_STATUS + e.getMessage());
            throw new DaoException();
        }
        return bills;
    }

    @Override
    public List<Bill> selectAll(int offset, int limit) {
        String query = SELECT_ALL_QUERY + LIMIT;
        List<Bill> bills = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Bill bill = buildBill(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_ALL + e.getMessage());
            throw new DaoException();
        }
        return bills;
    }

    @Override
    public List<Bill> selectByUserId(int userId, int offset, int limit) {
        String query = SELECT_BY_USER_ID_QUERY + LIMIT;
        List<Bill> bills = new ArrayList<>();
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Bill bill = buildBill(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.SELECT_BY_USER_ID + e.getMessage());
            throw new DaoException();
        }
        return bills;
    }

    @Override
    public boolean update(Bill bill) throws DaoException {
        int updateRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, bill.getStatus().toString());
            statement.setFloat(2, bill.getTotal());
            statement.setInt(3, bill.getId());
            updateRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(BillDaoImpl.class.toString() + LogMessages.UPDATE + e.getMessage());
            throw new DaoException();
        }
        return updateRow > 0;
    }

    @Override
    public boolean insert(Bill bill) throws DaoException {
        int insertedRow = 0;
        try (JdbcConnection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, bill.getId());
            preparedStatement.setFloat(2, bill.getTotal());
            preparedStatement.setString(3, bill.getStatus().toString());
            insertedRow = preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    bill.setId(generatedKeys.getInt(1));
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
    public Optional<Bill> select(int id) throws DaoException {
        Optional<Bill> bill;
        try(JdbcConnection connection = connectionManager.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            bill = Optional.of(buildBill(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(OrderDaoImpl.class.toString() + LogMessages.SELECT + e.getMessage());
            throw new DaoException();
        }

        return bill;
    }

    private Bill buildBill(ResultSet resultSet) throws SQLException {
        return new Bill.Builder()
                .addId(resultSet.getInt(COLUMN_ID))
                .addStatus(Bill.Status.valueOf(resultSet.getString(COLUMN_STATUS)))
                .addTotal(resultSet.getFloat(COLUMN_TOTAL))
                .createBill();
    }
}
