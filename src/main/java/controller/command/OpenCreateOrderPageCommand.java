package controller.command;

import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenCreateOrderPageCommand implements Command {

    private static class Holder {
        static final OpenCreateOrderPageCommand INSTANCE = new OpenCreateOrderPageCommand();
    }

    public static OpenCreateOrderPageCommand getInstnce() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.CREATE_ORDER;
    }
}
