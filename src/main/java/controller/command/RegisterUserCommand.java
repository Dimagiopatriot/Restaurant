package controller.command;

import model.entity.User;
import model.entity.UserAuth;
import model.service.UserService;
import util.Validator;
import util.constant.Messages;
import util.constant.Pages;
import util.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RegisterUserCommand implements Command {

    private UserService userService;

    RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    private static class Holder {
        static final RegisterUserCommand INSTANCE = new RegisterUserCommand(UserService.getInstance());
    }

    public static RegisterUserCommand getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = createUserFromRequest(request);
        if (user.getName() == null || user.getSurname() == null || user.getPhone() == null || user.getUserAuth() == null) {
            return Pages.MAIN;
        }
        List<String> errors = validateParams(user);
        if (!errors.isEmpty()) {
            setAttributes(request, user, errors);
            return Pages.REGISTRATION;
        }

        if (!userService.insert(user)) {
            errors.add(Messages.REGISTRATION_ERROR);
            setAttributes(request, user, errors);
            return Pages.REGISTRATION;
        }
        request.getSession().setAttribute(Parameters.USER, user);
        return Pages.USER;
    }

    private void setAttributes(HttpServletRequest request, User user, List<String> errors) {
        request.setAttribute(Parameters.NAME, user.getName());
        request.setAttribute(Parameters.SURNAME, user.getSurname());
        request.setAttribute(Parameters.PHONE, user.getPhone());
        request.setAttribute(Parameters.EMAIL, user.getUserAuth().getEmail());
        request.setAttribute(Parameters.ERRORS, errors);
    }

    private List<String> validateParams(User user) {
        List<String> errors = new ArrayList<>();
        Validator validator = Validator.getInstance();
        if (!validator.validateNameOrSurname(user.getName())) {
            errors.add(Messages.NAME_ERROR);
        }
        if (!validator.validateNameOrSurname(user.getSurname())) {
            errors.add(Messages.SURNAME_ERROR);
        }
        if (!validator.validatePhone(user.getPhone())) {
            errors.add(Messages.PHONE_ERROR);
        }
        if (!validator.validateEmail(user.getUserAuth().getEmail())) {
            errors.add(Messages.EMAIL_ERROR);
        }
        if (!validator.validatePassword(user.getUserAuth().getPassword())) {
            errors.add(Messages.PASSWORD_ERROR);
        }
        return errors;
    }

    private User createUserFromRequest(HttpServletRequest request) {
        return new User.Builder()
                .addName(request.getParameter(Parameters.NAME))
                .addSurname(request.getParameter(Parameters.SURNAME))
                .addPhone(request.getParameter(Parameters.PHONE))
                .addUserAuth(new UserAuth.Builder()
                        .addEmail(request.getParameter(Parameters.EMAIL))
                        .addPassword(request.getParameter(Parameters.PASSWORD))
                        .addRole(UserAuth.Role.CLIENT)
                        .createUserAuth())
                .createUser();
    }
}
