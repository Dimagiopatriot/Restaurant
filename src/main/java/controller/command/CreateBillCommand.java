package controller.command;

import controller.Util;
import model.entity.Bill;
import model.entity.Order;
import model.service.BillService;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateBillCommand implements Command {

    private BillService billService;

    public CreateBillCommand(BillService billService){
        this.billService = billService;
    }

    private static class Holder{
        static final CreateBillCommand INSTANCE = new CreateBillCommand(BillService.getInstance());
    }

    public CreateBillCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Bill bill = (Bill) request.getSession().getAttribute(Parameters.BILL);
        Order order = (Order) request.getSession().getAttribute(Parameters.ORDER);
        if (!billService.insert(bill, order)){
            request.getSession().setAttribute(Parameters.ERRORS, Messages.ERROR_INSERT);
            return Pages.CREATE_BILL;
        }
        int ordersCount = billService.selectCountOfBillsByStatus(Bill.Status.NOT_PAYED);
        int[] pages = Util.pages(ordersCount);
        List<Bill> bills = billService.selectByStatus(Bill.Status.NOT_PAYED, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.BILLS, bills);
        request.getSession().setAttribute(Parameters.SORT, Bill.Status.NOT_PAYED);
        return Pages.BILLS;
    }
}
