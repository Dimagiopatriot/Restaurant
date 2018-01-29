package controller.command;

import controller.Util;
import model.entity.Bill;
import model.entity.User;
import model.service.BillService;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PayBillCommand implements Command {

    private BillService billService;

    public PayBillCommand(BillService billService) {
        this.billService = billService;
    }

    private static class Holder {
        static final PayBillCommand INSTANCE = new PayBillCommand(BillService.getInstance());
    }

    public static PayBillCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        float userCount = ((User) request.getSession().getAttribute(Parameters.USER)).getCount();
        Bill bill = createBillFromRequest(request);
        if (!Util.checkSolvency(bill.getTotal(), userCount)) {
            request.setAttribute(Parameters.ERRORS, Messages.ERROR_NOT_ENOUGH_MONEY);
            return openResultPage(request);
        }

        if (!billService.updateStatus(bill)){
            request.setAttribute(Parameters.ERRORS, Messages.ERROR_BILL_UPDATE);
            return openResultPage(request);
        }

        return openResultPage(request);
    }

    private String openResultPage(HttpServletRequest request) {
        int userId = ((User) request.getSession().getAttribute(Parameters.USER)).getUserAuth().getId();
        int ordersCount = billService.selectCountOfBillsByUserId(userId);
        int[] pages = Util.pages(ordersCount);
        List<Bill> bills = billService.selectByUserId(userId, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.BILLS, bills);
        return Pages.BILLS;
    }

    private Bill createBillFromRequest(HttpServletRequest request) {
        return new Bill.Builder()
                .addId(Integer.parseInt(request.getParameter(Parameters.BILL_ID)))
                .addTotal(Float.parseFloat(request.getParameter(Parameters.BILL_TOTAL)))
                .addStatus(Bill.Status.PAYED)
                .createBill();
    }
}
