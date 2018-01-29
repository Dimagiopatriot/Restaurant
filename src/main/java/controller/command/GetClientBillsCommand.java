package controller.command;

import controller.Util;
import model.entity.Bill;
import model.entity.User;
import model.service.BillService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetClientBillsCommand implements Command {

    private BillService billService;

    public GetClientBillsCommand(BillService billService) {
        this.billService = billService;
    }

    private static class Holder {
        static final GetClientBillsCommand INSTANCE = new GetClientBillsCommand(BillService.getInstance());
    }

    public static GetClientBillsCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = ((User)request.getSession().getAttribute(Parameters.USER)).getUserAuth().getId();
        int ordersCount = billService.selectCountOfBillsByUserId(userId);
        int[] pages = Util.pages(ordersCount);
        List<Bill> bills = billService.selectByUserId(userId, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.BILLS, bills);
        return Pages.BILLS;
    }
}
