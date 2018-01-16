package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PHONE_REGEX = Pattern.compile("^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{2}\\s?\\d{2}$");
    private static final Pattern VALID_NAME_SURNAME_REGEX = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern VALID_USER_COUNT = Pattern.compile("^[+]?[0-9]*\\.?[0-9]+$");

    public static Validator getInstance() {
        return new Validator();
    }

    public boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public boolean validatePhone(String phone) {
        Matcher matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }

    public boolean validateNameOrSurname(String nameOrSurname) {
        Matcher matcher = VALID_NAME_SURNAME_REGEX.matcher(nameOrSurname);
        return matcher.find();
    }

    public boolean validateUserCount(String count) {
        Matcher matcher = VALID_USER_COUNT.matcher(count);
        return matcher.find();
    }
}
