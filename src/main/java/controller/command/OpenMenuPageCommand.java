package controller.command;

import model.entity.Dish;
import model.service.DishService;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenMenuPageCommand implements Command {

    private DishService dishService;

    public OpenMenuPageCommand(DishService dishService) {
        this.dishService = dishService;
    }

    private static class Holder {
        static final OpenMenuPageCommand INSTANCE = new OpenMenuPageCommand(DishService.getInstance());
    }

    public static OpenMenuPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Dish> entrees = dishService.selectByDishType(Dish.DishType.ENTREE.toString());
        List<Dish> seconds = dishService.selectByDishType(Dish.DishType.SECOND.toString());
        List<Dish> desserts = dishService.selectByDishType(Dish.DishType.DESSERT.toString());
        List<Dish> drinks = dishService.selectByDishType(Dish.DishType.DRINKS.toString());

        request.setAttribute(Parameters.DISH_ENTREES, entrees);
        request.setAttribute(Parameters.DISH_SECONDS, seconds);
        request.setAttribute(Parameters.DISH_DESSERTS, desserts);
        request.setAttribute(Parameters.DISH_DRINKS, drinks);
        return Pages.MENU;
    }
}
