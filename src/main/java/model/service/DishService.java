package model.service;

import model.dao.util.DaoFactory;
import model.entity.Dish;

import java.util.Map;

public class DishService {

    private DaoFactory daoFactory;

    public DishService(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final DishService INSTANCE = new DishService(DaoFactory.getInstance());
    }

    public static DishService getInstance(){
        return Holder.INSTANCE;
    }

    public Map<Dish, Integer> selectDishesForBill(int billId){
        return daoFactory.getDishDao().selectDishesForBill(billId);
    }

    public Map<Dish, Integer> selectDishesForOrder(int orderId){
        return daoFactory.getDishDao().selectDishesForOrder(orderId);
    }
}
