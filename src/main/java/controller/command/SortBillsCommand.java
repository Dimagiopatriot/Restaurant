package controller.command;

import controller.Util;
import model.entity.Bill;
import model.service.BillService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SortBillsCommand implements Command{
    private BillService billService;

    public SortBillsCommand(BillService orderService) {
        this.billService = orderService;
    }

    private static class Holder{
        static final SortBillsCommand INSTANCE = new SortBillsCommand(BillService.getInstance());
    }

    public static SortBillsCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        List<Bill> bills;
        String sortParam = request.getParameter(Parameters.SORT);
        int[] pages;
        if (sortParam.equals("ALL")) {
            int ordersCount = billService.selectCountOfAllBills();
            pages = Util.pages(ordersCount);
            bills = billService.selectAll(Util.MIN_OFFSET, Util.LIMIT);
        } else {
            Bill.Status status = Bill.Status.valueOf(request.getParameter(Parameters.SORT));
            int ordersCount = billService.selectCountOfBillsByStatus(status);
            pages = Util.pages(ordersCount);
            bills = billService.selectByStatus(status, Util.MIN_OFFSET, Util.LIMIT);
        }
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.BILLS, bills);
        request.getSession().setAttribute(Parameters.SORT, sortParam);
        return Pages.ORDERS;
    }
}
