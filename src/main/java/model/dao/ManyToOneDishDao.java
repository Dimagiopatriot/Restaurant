package model.dao;

public interface ManyToOneDishDao {

    boolean insertIntoManyToOneDishOrderTable(int dishId, int portions, int orderId);
    boolean insertIntoManyToOneDishBillTable(int dishId, int portions, int billId);
}
