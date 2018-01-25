package model.dao;

import model.entity.Dish;

import java.util.List;

public interface DishDao extends GenericDao<Dish> {
    List<Dish> selectDishesForBill(int billId);
    List<Dish> selectDishesForOrder(int billId);
}
