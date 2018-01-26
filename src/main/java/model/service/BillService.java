package model.service;

import model.dao.util.ConnectionManager;
import model.dao.util.DaoFactory;
import model.entity.Bill;
import model.entity.Order;
import util.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class BillService {

    private DaoFactory daoFactory;
    private ConnectionManager connectionManager;
    private ManyToOneService manyToOneService;
    private OrderService orderService;
    private DishService dishService;

    public BillService(DaoFactory daoFactory, ConnectionManager connectionManager, ManyToOneService manyToOneService,
                       OrderService orderService, DishService dishService) {
        this.daoFactory = daoFactory;
        this.connectionManager = connectionManager;
        this.manyToOneService = manyToOneService;
        this.orderService = orderService;
        this.dishService = dishService;
    }

    private static class Holder {
        static final BillService INSTANCE = new BillService(DaoFactory.getInstance(), ConnectionManager.getInstance(),
                ManyToOneService.getInstance(), OrderService.getInstance(), DishService.getInstance());
    }

    public static BillService getInstance() {
        return Holder.INSTANCE;
    }

    public boolean updateStatus(Bill bill) {
        return daoFactory.getBillDao().updateStatus(bill);
    }

    //TODO: refactor dish return and add dishes to each bill in bill service
    public List<Bill> selectByStatus(Bill.Status status, int offset, int limit) {
        return daoFactory.getBillDao().selectByStatus(status, offset, limit);
    }

    public List<Bill> selectAll(int offset, int limit) {
        return daoFactory.getBillDao().selectAll(offset, limit);
    }

    public List<Bill> selectByUserId(int userId, int offset, int limit) {
        return daoFactory.getBillDao().selectByUserId(userId, offset, limit);
    }

    public boolean update(Bill bill) {
        return daoFactory.getBillDao().update(bill);
    }

    public boolean insert(Bill bill, Order order) {

        boolean isCreated = false;
        try {
            connectionManager.startTransaction();
            daoFactory.getBillDao().insert(bill);
            manyToOneService.insertIntoManyToOneDishBillTable(bill.getPortionsToDishMap(), bill.getId());
            isCreated = orderService.updateBillPresence(order);
            connectionManager.commit();
        } catch (DaoException ex) {
            connectionManager.rollback();
            return isCreated;
        }
        return isCreated;
    }

    public Optional<Bill> select(int id) {
        return daoFactory.getBillDao().select(id);
    }
}
