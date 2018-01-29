package controller.command;

import model.entity.Dish;
import model.entity.Order;
import model.service.OrderService;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CreateOrderCommand implements Command {

    private OrderService orderService;

    public CreateOrderCommand(OrderService orderService){
        this.orderService = orderService;
    }

    private static class Holder{
        static final CreateOrderCommand INSTANCE = new CreateOrderCommand(OrderService.getInstance());
    }

    public CreateOrderCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = createOrderFromRequest(request);
        if (!orderService.insert(order)){
            request.setAttribute(Parameters.ERRORS, Messages.ERROR_INSERT);
            return Pages.CREATE_ORDER;
        }
        request.setAttribute(Parameters.SUCCESS, Messages.SUCCESS_ORDER_INSERT);
        return Pages.CREATE_ORDER;
    }

    public Order createOrderFromRequest(HttpServletRequest request){
        Map<Dish, Integer> portionsToDish = new HashMap<>();
        portionsToDish.put((Dish) request.getSession().getAttribute(Parameters.ENTREE),
                Integer.valueOf(request.getParameter(Parameters.ENTREE_PORTIONS)));
        portionsToDish.put((Dish) request.getSession().getAttribute(Parameters.SECOND),
                Integer.valueOf(request.getParameter(Parameters.SECOND_PORTIONS)));
        portionsToDish.put((Dish) request.getSession().getAttribute(Parameters.DESSERT),
                Integer.valueOf(request.getParameter(Parameters.DESSERT_PORTIONS)));
        portionsToDish.put((Dish) request.getSession().getAttribute(Parameters.DRINK),
                Integer.valueOf(request.getParameter(Parameters.DRINK_PORTIONS)));

        return new Order.Builder()
                .addStatus(Order.Status.NEW)
                .addIsBillPresentForOrder(false)
                .addPortionsToDishMap(portionsToDish)
                .createOrder();
    }
}
