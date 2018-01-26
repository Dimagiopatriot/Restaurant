package model.dao;

import model.entity.Dish;

import java.util.Map;

public interface ManyToOneDishDao {

    boolean insertIntoManyToOneDishOrderTable(Map<Dish, Integer> portionsToDishMap, int orderId);
    boolean insertIntoManyToOneDishBillTable(Map<Dish, Integer> portionsToDishMap, int billId);
}
