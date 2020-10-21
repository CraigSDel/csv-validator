package web.app.craigstroberg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnValidatorTest {

    public static final String FOUR_SPACES = "    ";
    public static final String TWO_TABS = "        ";
    public static final String TESTING_A_BLANK_STRING = "Testing a blank string";
    public static final String TESTING_THE_CREATION_OF_A_INTEGER = "Testing the creation of a integer";
    public static final String NUMBER = "1002";

    @Test
    public void createValidationField() {
        ColumnValidator columnValidator = ColumnValidator.builder()
                .description("Testing that the field is a string")
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .build();
        assertEquals("Testing that the field is a string", columnValidator.getDescription());
        assertEquals(ColumnValidationType.STRING_NOT_BLANK, columnValidator.getColumnValidationType());
    }

    @Test
    public void validateAValidString() {
        ColumnValidator testing_a_blank_string = ColumnValidator.builder()
                .description(TESTING_A_BLANK_STRING)
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .build();
        assertTrue(testing_a_blank_string.validate("I am not a blank string Jan"));
    }

    @Test
    public void invalidString() {
        assertThrows(ValidationException.class, () -> {
            ColumnValidator testing_a_blank_string = ColumnValidator.builder()
                    .description(TESTING_A_BLANK_STRING)
                    .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                    .build();
            testing_a_blank_string.validate(FOUR_SPACES + TWO_TABS);
        });
    }

    @Test
    public void validInteger() {
            ColumnValidator testing_a_blank_string = ColumnValidator.builder()
                    .description(TESTING_THE_CREATION_OF_A_INTEGER)
                    .columnValidationType(ColumnValidationType.INTEGER)
                    .build();
            assertTrue(testing_a_blank_string.validate(NUMBER));
    }

    @Test
    public void invalidInteger() {
        assertThrows(ValidationException.class, () -> {
            ColumnValidator testing_a_blank_string = ColumnValidator.builder()
                    .description(TESTING_THE_CREATION_OF_A_INTEGER)
                    .columnValidationType(ColumnValidationType.INTEGER)
                    .build();
            testing_a_blank_string.validate(NUMBER + TESTING_THE_CREATION_OF_A_INTEGER);
        });
    }
}
