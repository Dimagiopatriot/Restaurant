package controller.command;

import controller.Util;
import model.entity.Order;
import model.service.OrderService;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeOrderStatusCommand implements Command {

    private OrderService orderService;

    public ChangeOrderStatusCommand(OrderService orderService){
        this.orderService = orderService;
    }

    private static class Holder{
        static final ChangeOrderStatusCommand INSTANCE = new ChangeOrderStatusCommand(OrderService.getInstance());
    }

    public static ChangeOrderStatusCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = createFromRequest(request);
        if (!orderService.updateStatus(order)){
            request.setAttribute(Parameters.ERROR_ORDER_UPDATE, Messages.ERROR_ORDER_UPDATE);
            return openResultPage(request);
        }
        return openResultPage(request);
    }

    private String openResultPage(HttpServletRequest request){
        int ordersCount = orderService.selectCountOfOrdersByStatus(Order.Status.SEND_TO_KITCHEN);
        int[] pages = Util.pages(ordersCount);
        List<Order> orders = orderService.selectByStatus(Order.Status.SEND_TO_KITCHEN, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.ORDERS, orders);
        request.getSession().setAttribute(Parameters.SORT, Order.Status.SEND_TO_KITCHEN);
        return Pages.ORDERS;
    }

    private Order createFromRequest(HttpServletRequest request){
        return new Order.Builder()
                .addId(Integer.parseInt(request.getParameter(Parameters.ORDER_ID)))
                .addStatus(Order.Status.valueOf(request.getParameter(Parameters.ORDER_STATUS)))
                .createOrder();
    }
}
