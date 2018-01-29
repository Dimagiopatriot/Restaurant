package model.dao;

import model.entity.Bill;
import model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> selectByStatus(Order.Status status, int offset, int limit);

    List<Order> selectAll(int offset, int limit);

    List<Order> selectByUserId(int userId, int offset, int limit);

    boolean updateStatus(Order order);

    boolean updateBillPresence(Order order);

    int selectCountOfOrdersByStatus(Order.Status status);

    int selectCountOfOrdersByUserId(int userId);

    int selectCountOfAllOrders();
}
