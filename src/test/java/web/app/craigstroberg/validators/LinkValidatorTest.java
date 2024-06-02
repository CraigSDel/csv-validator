package web.app.craigstroberg.validators;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkValidatorTest {

    public static final String TESTING_THE_CREATION_OF_A_LINK = "Testing the creation of a link";
    public static final String LINK = "https://i.ytimg.com/vi/JzCsM1vtn78/default.jpg";

    @Test
    public void valid() {
        Validator linlinkValidator = LinkValidator.builder()
                .columnDescription(TESTING_THE_CREATION_OF_A_LINK)
                .validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                .build();
        assertTrue(linlinkValidator.validate(LINK));
    }

    @Test
    public void invalid() {
        assertThrows(ValidationException.class, () -> {
            Validator linkValidator = LinkValidator.builder()
                    .columnDescription(TESTING_THE_CREATION_OF_A_LINK)
                    .validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                    .build();
            linkValidator.validate(LINK + TESTING_THE_CREATION_OF_A_LINK);
        });
    }
}