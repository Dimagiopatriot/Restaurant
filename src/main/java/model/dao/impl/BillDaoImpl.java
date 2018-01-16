package model.dao.impl;

import model.dao.BillDao;
import model.dao.util.ConnectionManager;
import model.entity.Bill;
import org.apache.log4j.Logger;
import util.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class BillDaoImpl implements BillDao {

    private ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(BillDaoImpl.class);

    public BillDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static class Holder{
        final static BillDaoImpl INSTANCE = new BillDaoImpl(ConnectionManager.getInstance());
    }

    public static BillDaoImpl getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public boolean updateStatus(Bill bill) {
        return false;
    }

    @Override
    public List<Bill> selectByStatus(Bill.Status status, int offset, int limit) {
        return null;
    }

    @Override
    public List<Bill> selectAll() {
        return null;
    }

    @Override
    public List<Bill> selectByUserId(int userId, int offset, int limit) {
        return null;
    }

    @Override
    public boolean update(Bill bill) throws DaoException {
        return false;
    }

    @Override
    public boolean insert(Bill bill) throws DaoException {
        return false;
    }

    @Override
    public Optional<Bill> select(int id) throws DaoException {
        return null;
    }
}
