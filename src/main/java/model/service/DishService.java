package model.service;

import model.dao.util.DaoFactory;
import model.entity.Dish;

import java.util.List;

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

    public List<Dish> selectDishesForBill(int billId){
        return daoFactory.getDishDao().selectDishesForBill(billId);
    }

    public List<Dish> selectDishesForOrder(int orderId){
        return daoFactory.getDishDao().selectDishesForOrder(orderId);
    }
}
