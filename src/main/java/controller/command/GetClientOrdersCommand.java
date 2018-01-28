package controller.command;

import controller.Util;
import model.entity.Order;
import model.entity.User;
import model.service.OrderService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetClientOrdersCommand implements Command {

    private OrderService orderService;

    public GetClientOrdersCommand(OrderService orderService){
        this.orderService = orderService;
    }

    private static class Holder{
        static final GetAdminOrdersCommand INSTANCE = new GetAdminOrdersCommand(OrderService.getInstance());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = ((User)request.getSession().getAttribute(Parameters.USER)).getUserAuth().getId();
        int ordersCount = orderService.selectCountOfOrdersByUserId(userId);
        int[] pages = Util.pages(ordersCount);
        List<Order> orders = orderService.selectByUserId(userId, Util.MIN_OFFSET, Util.LIMIT);
        request.getSession().setAttribute(Parameters.PAGES_OF_RESULT, pages);
        request.getSession().setAttribute(Parameters.CURRENT_PAGE, 1);
        request.getSession().setAttribute(Parameters.ORDERS, orders);
        return Pages.ORDERS;
    }
}
