package model.dao.impl;

import model.dao.DishDao;
import model.dao.util.ConnectionManager;
import model.entity.Dish;
import org.apache.log4j.Logger;
import util.exception.DaoException;

import java.util.Optional;

public class DishDaoImpl implements DishDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(DishDaoImpl.class);

    public DishDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder {
        static final DishDaoImpl INSTANCE = new DishDaoImpl(ConnectionManager.getInstance());
    }

    public static DishDaoImpl getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public boolean update(Dish dish) throws DaoException {
        return false;
    }

    @Override
    public boolean insert(Dish dish) throws DaoException {
        return false;
    }

    @Override
    public Optional<Dish> select(int id) throws DaoException {
        return null;
    }
}
