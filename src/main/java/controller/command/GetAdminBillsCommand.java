package controller.command;

import controller.Util;
import model.entity.Bill;
import model.service.BillService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAdminBillsCommand implements Command {

    private BillService billService;

    public GetAdminBillsCommand(BillService billService) {
        this.billService = billService;
    }

    private static class Holder {
        static final GetAdminBillsCommand INSTANCE = new GetAdminBillsCommand(BillService.getInstance());
    }

    public static GetAdminBillsCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int ordersCount = billService.selectCountOfBillsByStatus(Bill.Status.NOT_PAYED);
        int[] pages = Util.pages(ordersCount);
        List<Bill> bills = billService.selectByStatus(Bill.Status.NOT_PAYED, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.ORDERS, bills);
        request.getSession().setAttribute(Parameters.SORT, Bill.Status.NOT_PAYED);
        return Pages.BILLS;
    }
}
