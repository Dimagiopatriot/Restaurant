package controller.command;

import controller.Util;
import model.entity.Bill;
import model.entity.Order;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenCreateBillPageCommand implements Command {

    private static class Holder {
        static final OpenCreateBillPageCommand INSTANCE = new OpenCreateBillPageCommand();
    }

    public static OpenCreateBillPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Bill bill = createFromRequest(request);
        request.getSession().setAttribute(Parameters.BILL, bill);
        return Pages.CREATE_BILL;
    }

    private Bill createFromRequest(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute(Parameters.ORDER);
        return new Bill.Builder()
                .addId(order.getId())
                .addStatus(Bill.Status.NOT_PAYED)
                .addPortionsToDishMap(order.getPortionsToDishMap())
                .addTotal(Util.calculateTotal(order))
                .createBill();
    }

}
