package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    @Test
    public void testNameRegEx() {
        Validator validator = Validator.getInstance();
        boolean actual = validator.validateNameOrSurname("Огієнко");
        assertEquals(true, actual);
    }

    @Test
    public void testValidateEmail(){
        Validator validator = Validator.getInstance();
        boolean actual = validator.validateEmail("sd3@mail.com");
        assertEquals(true, actual);
    }

    @Test
    public void testValidatePassword(){
        Validator validator = Validator.getInstance();
        boolean actual = validator.validatePassword("2234ujls");
        assertEquals(true, actual);
    }

    @Test
    public void testValidatePhone(){
        Validator validator = Validator.getInstance();
        boolean actual = validator.validatePhone("+380982986452");
        assertEquals(true, actual);
    }

    @Test
    public void testValidateUserCount(){
        Validator validator = Validator.getInstance();
        boolean actual = validator.validateUserCount("3456.21");
        assertEquals(true, actual);
    }
}
