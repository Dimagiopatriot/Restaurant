package model.service;

import model.dao.util.DaoFactory;
import model.entity.Dish;

import java.util.Map;

public class ManyToOneService {

    private DaoFactory daoFactory;

    public ManyToOneService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class Holder{
        static final ManyToOneService INSTANCE = new ManyToOneService(DaoFactory.getInstance());
    }

    public static ManyToOneService getInstance(){
        return Holder.INSTANCE;
    }

    public boolean insertIntoManyToOneDishOrderTable(Map<Dish, Integer> portionsToDishMap, int orderId){
        return daoFactory.getManyToOneDishDao().insertIntoManyToOneDishOrderTable(portionsToDishMap, orderId);
    }

    public boolean insertIntoManyToOneDishBillTable(Map<Dish, Integer> portionsToDishMap, int billId){
        return daoFactory.getManyToOneDishDao().insertIntoManyToOneDishBillTable(portionsToDishMap, billId);
    }
}