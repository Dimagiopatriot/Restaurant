package model.service;

import model.dao.util.ConnectionManager;
import model.dao.util.DaoFactory;
import model.entity.Order;
import util.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private DaoFactory daoFactory;
    private ConnectionManager connectionManager;
    private ManyToOneService manyToOneService;
    private DishService dishService;

    public OrderService(DaoFactory daoFactory, ConnectionManager connectionManager, ManyToOneService manyToOneService,
                        DishService dishService) {
        this.daoFactory = daoFactory;
        this.connectionManager = connectionManager;
        this.manyToOneService = manyToOneService;
        this.dishService = dishService;
    }

    private static class Holder {
        static final OrderService INSTANCE = new OrderService(DaoFactory.getInstance(), ConnectionManager.getInstance(),
                ManyToOneService.getInstance(), DishService.getInstance());
    }

    public static OrderService getInstance() {
        return Holder.INSTANCE;
    }

    //TODO: refactor dish return and add dishes to each bill in order service
    public List<Order> selectByStatus(Order.Status status, int offset, int limit) {
        return daoFactory.getOrderDao().selectByStatus(status, offset, limit);
    }

    public List<Order> selectAll(int offset, int limit) {
        return daoFactory.getOrderDao().selectAll(offset, limit);
    }

    public List<Order> selectByUserId(int userId, int offset, int limit) {
        return daoFactory.getOrderDao().selectByUserId(userId, offset, limit);
    }

    public Optional<Order> select(int id) {
        return daoFactory.getOrderDao().select(id);
    }

    public boolean updateStatus(Order order) {
        return daoFactory.getOrderDao().updateStatus(order);
    }

    public boolean updateBillPresence(Order order) {
        return daoFactory.getOrderDao().updateBillPresence(order);
    }

    public boolean update(Order order) {
        return daoFactory.getOrderDao().update(order);
    }

    public boolean insert(Order order) {

        boolean isCreated = false;
        try {
            connectionManager.startTransaction();
            daoFactory.getOrderDao().insert(order);
            isCreated = manyToOneService.insertIntoManyToOneDishOrderTable(order.getPortionsToDishMap(), order.getId());
            connectionManager.commit();
        } catch (DaoException ex) {
            connectionManager.rollback();
            return isCreated;
        }
        return isCreated;
    }
}
