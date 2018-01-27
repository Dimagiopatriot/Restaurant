package controller.command;

import model.entity.User;
import model.service.UserService;
import util.Validator;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginUserCommand implements Command {
    private UserService userService;

    LoginUserCommand(UserService userService) {
        this.userService = userService;
    }

    private static class Holder{
        static final LoginUserCommand INSTANCE = new LoginUserCommand(UserService.getInstance());
    }

    public static LoginUserCommand getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(Parameters.EMAIL);
        String password = request.getParameter(Parameters.PASSWORD);
        List<String> errors = validateParams(email, password);
        if (email == null || password == null){
            errors.add(Messages.STUB_ERROR_MESSAGE);
            return Pages.MAIN;
        }
        if (!errors.isEmpty()){
            setAttributes(request, email, errors);
            return Pages.LOGIN;
        }

        Optional<User> user = userService.selectByEmailPassword(email, password);
        if (!user.isPresent()){
            errors.add(Messages.LOGIN_ERROR);
            setAttributes(request, email, errors);
            return Pages.LOGIN;
        }
        request.getSession().setAttribute(Parameters.USER, user.get());
        return Pages.USER;
    }


    private void setAttributes(HttpServletRequest request, String email, List<String> errors){
        request.setAttribute(Parameters.EMAIL, email);
        request.setAttribute(Parameters.ERRORS, errors);
    }

    private List<String> validateParams(String email, String password){
        List<String> errors = new ArrayList<>();
        Validator validator = Validator.getInstance();
        if (!validator.validateEmail(email)){
            errors.add(Messages.EMAIL_ERROR);
        }
        if (!validator.validatePassword(password)){
            errors.add(Messages.PASSWORD_ERROR);
        }
        return errors;
    }
}
