package controller.command;

import controller.Util;
import model.entity.Order;
import model.service.OrderService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAdminOrdersCommand implements Command {

    private OrderService orderService;

    public GetAdminOrdersCommand(OrderService orderService){
        this.orderService = orderService;
    }

    private static class Holder{
        static final GetAdminOrdersCommand INSTANCE = new GetAdminOrdersCommand(OrderService.getInstance());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int ordersCount = orderService.selectCountOfOrdersByStatus(Order.Status.SEND_TO_KITCHEN);
        int[] pages = Util.pages(ordersCount);
        List<Order> orders = orderService.selectByStatus(Order.Status.SEND_TO_KITCHEN, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.ORDERS, orders);
        request.getSession().setAttribute(Parameters.SORT, Order.Status.SEND_TO_KITCHEN);
        return Pages.ORDERS;
    }
}
