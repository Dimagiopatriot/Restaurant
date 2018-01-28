package controller.command;

import model.entity.Bill;
import model.entity.Order;
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
        return null;
    }

    private Bill createFromRequest(HttpServletRequest request) {
        return null;
    }

}
