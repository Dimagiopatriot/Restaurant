package model.dao.impl;

import model.dao.OrderDao;
import model.dao.util.ConnectionManager;
import model.entity.Bill;
import model.entity.Order;
import org.apache.log4j.Logger;
import util.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {


    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);

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
        return null;
    }

    @Override
    public List<Order> selectAll() {
        return null;
    }

    @Override
    public List<Order> selectByUserId(int userId, int offset, int limit) {
        return null;
    }

    @Override
    public boolean updateStatus(Bill bill) {
        return false;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        return false;
    }

    @Override
    public Optional<Order> select(int id) throws DaoException {
        return null;
    }
}
