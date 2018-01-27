package controller.command;

import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUserCommand implements Command{

    private static class Holder{
        static final LogoutUserCommand INSTANCE = new LogoutUserCommand();
    }

    public static LogoutUserCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return Pages.MAIN;
    }
}
