package controller.command;

import controller.Util;
import model.entity.Order;
import model.service.OrderService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SortOrdersCommand implements Command {

    private OrderService orderService;

    public SortOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    private static class Holder {
        static final SortOrdersCommand INSTANCE = new SortOrdersCommand(OrderService.getInstance());
    }

    public static SortOrdersCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        List<Order> orders;
        String sortParam = request.getParameter(Parameters.SORT);
        int[] pages;
        if (sortParam.equals("ALL")) {
            int ordersCount = orderService.selectCountOfAllOrders();
            pages = Util.pages(ordersCount);
            orders = orderService.selectAll(Util.MIN_OFFSET, Util.LIMIT);
        } else {
            Order.Status status = Order.Status.valueOf(request.getParameter(Parameters.SORT));
            int ordersCount = orderService.selectCountOfOrdersByStatus(status);
            pages = Util.pages(ordersCount);
            orders = orderService.selectByStatus(status, Util.MIN_OFFSET, Util.LIMIT);
        }
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.ORDERS, orders);
        request.getSession().setAttribute(Parameters.SORT, sortParam);
        return Pages.ORDERS;
    }
}
