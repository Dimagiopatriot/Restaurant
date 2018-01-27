package controller.command;

import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenMainPageCommand implements Command {

    private static class Holder {
        static final OpenMainPageCommand INSTANCE = new OpenMainPageCommand();
    }

    public static OpenMainPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.MAIN;
    }
}
