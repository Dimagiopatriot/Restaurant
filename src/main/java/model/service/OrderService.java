package model.service;

import model.dao.util.ConnectionManager;
import model.dao.util.DaoFactory;
import model.entity.Order;
import util.exception.DaoException;

import java.util.ArrayList;
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

    public List<Order> selectByStatus(Order.Status status, int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        try {
            connectionManager.startTransaction();
            orders = daoFactory.getOrderDao().selectByStatus(status, offset, limit);
            getDishesForOrders(orders);
            connectionManager.commit();
        } catch (DaoException e) {
            connectionManager.rollback();
            return orders;
        }
        return orders;
    }

    public List<Order> selectAll(int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        try {
            connectionManager.startTransaction();
            orders = daoFactory.getOrderDao().selectAll(offset, limit);
            getDishesForOrders(orders);
            connectionManager.commit();
        } catch (DaoException e) {
            connectionManager.commit();
            return orders;
        }
        return orders;
    }

    public List<Order> selectByUserId(int userId, int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        try {
            connectionManager.startTransaction();
            orders = daoFactory.getOrderDao().selectByUserId(userId, offset, limit);
            getDishesForOrders(orders);
            connectionManager.commit();
        } catch (DaoException e) {
            connectionManager.commit();
            return orders;
        }
        return orders;
    }

    public Optional<Order> select(int id) {
        Optional<Order> orderOptional = daoFactory.getOrderDao().select(id);
        try {
            connectionManager.startTransaction();
            if (orderOptional.isPresent()){
                Order order = orderOptional.get();
                order.setPortionsToDishMap(dishService.selectDishesForOrder(order.getId()));
            }
            connectionManager.commit();
        } catch (DaoException e){
            connectionManager.commit();
            return orderOptional;
        }
        return orderOptional;
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

    private void getDishesForOrders(List<Order> orders) {
        for (Order order : orders) {
            order.setPortionsToDishMap(dishService.selectDishesForOrder(order.getId()));
        }
    }
}
