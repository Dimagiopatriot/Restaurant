package model.dao;

import model.entity.Bill;
import model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> selectByStatus(Order.Status status, int offset, int limit);

    List<Order> selectAll();

    List<Order> selectByUserId(int userId, int offset, int limit);

    boolean updateStatus(Bill bill);
}
