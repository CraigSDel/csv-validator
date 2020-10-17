package web.app.craigstroberg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnValidatorTest {

    @Test
    public void createValidationField() {
        ColumnValidator columnValidator = ColumnValidator.builder()
                .description("Testing that the field is a string")
                .columnValidationType(ColumnValidationType.STRING)
                .build();
        assertEquals("Testing that the field is a string", columnValidator.getDescription());
        assertEquals(ColumnValidationType.STRING, columnValidator.getColumnValidationType());
    }
}
