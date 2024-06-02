package web.app.craigstroberg.validators;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringValidatorTest {

    public static final String FOUR_SPACES = "    ";
    public static final String TWO_TABS = "        ";
    public static final String TESTING_A_BLANK_STRING = "Testing a blank string";

    @Test
    public void validate() {
        Validator stringValidator = StringValidator.builder().columnDescription("String Test").build();
        boolean validate = stringValidator.validate(TESTING_A_BLANK_STRING);
        assertTrue(validate);
    }

    @Test
    public void invalid() {
        assertThrows(ValidationException.class, () -> {
            Validator stringValidator = StringValidator.builder().columnDescription("String Test").build();
            stringValidator.validate(FOUR_SPACES + TWO_TABS);
        });
    }

}