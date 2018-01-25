package model.dao;

import model.entity.Bill;

import java.util.List;

public interface BillDao extends GenericDao<Bill> {

    boolean updateStatus(Bill bill);

    List<Bill> selectByStatus(Bill.Status status, int offset, int limit);

    List<Bill> selectAll(int offset, int limit);

    List<Bill> selectByUserId(int userId, int offset, int limit);
}
