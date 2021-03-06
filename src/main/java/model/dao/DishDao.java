package model.dao;

import model.entity.Dish;

import java.util.List;
import java.util.Map;

public interface DishDao extends GenericDao<Dish> {
    Map<Dish, Integer> selectDishesForBill(int billId);
    Map<Dish, Integer> selectDishesForOrder(int billId);
    List<Dish> selectByDishType(String dishType);
}
