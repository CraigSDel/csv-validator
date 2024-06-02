package web.app.craigstroberg.validators;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegerValidatorTest {

    public static final String TESTING_THE_CREATION_OF_A_INTEGER = "Testing the creation of a integer";
    public static final String NUMBER = "1002";

    @Test
    public void valid() {
        Validator intergerValidator = IntegerValidator.builder()
                .columnDescription(TESTING_THE_CREATION_OF_A_INTEGER)
                .build();
        assertTrue(intergerValidator.validate(NUMBER));
    }

    @Test
    public void invalid() {
        assertThrows(ValidationException.class, () -> {
            Validator integerValidator = IntegerValidator.builder()
                    .columnDescription(TESTING_THE_CREATION_OF_A_INTEGER)
                    .build();
            integerValidator.validate(NUMBER + TESTING_THE_CREATION_OF_A_INTEGER);
        });
    }

}