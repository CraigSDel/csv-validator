package web.app.craigstroberg.enums;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;
import web.app.craigstroberg.validators.StringValidator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringValidatorTest {

    public static final String FOUR_SPACES = "    ";
    public static final String TWO_TABS = "        ";
    public static final String TESTING_A_BLANK_STRING = "Testing a blank string";

    @Test
    public void validateAValidString() {
        StringValidator stringTest = StringValidator.builder().columnDescription("String Test").build();
        boolean validate = stringTest.validate(TESTING_A_BLANK_STRING);
        assertTrue(validate);
    }

    @Test
    public void invalidString() {
        assertThrows(ValidationException.class, () -> {
            StringValidator stringTest = StringValidator.builder().columnDescription("String Test").build();
            stringTest.validate(FOUR_SPACES + TWO_TABS);
        });
    }

}