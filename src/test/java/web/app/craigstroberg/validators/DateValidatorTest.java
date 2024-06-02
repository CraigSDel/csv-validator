package web.app.craigstroberg.validators;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateValidatorTest {

    public static final String TESTING_THE_CREATION_OF_A_DATE = "Testing the creation of a date";

    @Test
    public void valid() {
        Validator dateValidator = DateValidator.builder()
                .columnDescription(TESTING_THE_CREATION_OF_A_DATE)
                .validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .build();
        assertTrue(dateValidator.validate("23,2017-11-12T19:05:24.000Z"));
    }

    @Test
    public void invalid() {
        assertThrows(ValidationException.class, () -> {
            Validator dateValidator = DateValidator.builder()
                    .columnDescription(TESTING_THE_CREATION_OF_A_DATE)
                    .build();
            dateValidator.validate("I am not a date");
        });
    }
}