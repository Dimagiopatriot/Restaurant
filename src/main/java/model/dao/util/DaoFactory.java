package model.dao.util;

import model.dao.BillDao;
import model.dao.DishDao;
import model.dao.OrderDao;
import model.dao.UserAuthDao;
import model.dao.impl.BillDaoImpl;
import model.dao.impl.DishDaoImpl;
import model.dao.impl.OrderDaoImpl;
import model.dao.impl.UserAuthDaoImpl;

public class DaoFactory {

    private static class Holder {
        static final DaoFactory INSTANCE = new DaoFactory();
    }

    public static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    public BillDao getBillDao(){
        return BillDaoImpl.getInstance();
    }

    public DishDao getDishDao(){
        return DishDaoImpl.getInstance();
    }

    public OrderDao getOrderDao(){
        return OrderDaoImpl.getInstance();
    }

    public UserAuthDao getUserAuthDao(){
        return UserAuthDaoImpl.getInstance();
    }
}
