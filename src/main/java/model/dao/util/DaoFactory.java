package model.dao.util;

import model.dao.*;
import model.dao.impl.*;

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

    public UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

    public ManyToOneDishDao getManyToOneDishDao(){
        return ManyToOneDishDaoImpl.getInstance();
    }
}
