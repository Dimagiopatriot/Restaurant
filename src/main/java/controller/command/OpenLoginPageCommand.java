package controller.command;

import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenLoginPageCommand implements Command{
    private static class Holder{
        static final OpenLoginPageCommand INSTANCE = new OpenLoginPageCommand();
    }

    public static OpenLoginPageCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.LOGIN;
    }
}
