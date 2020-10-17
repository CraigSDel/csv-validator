package web.app.craigstroberg;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataValidatorTest {

    @Test
    public void validatorWillThrowAnExceptionIfValidationFieldsAreNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(List.of(ColumnValidator.builder().build()))
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvDataIsNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(getSingleStringValidator())
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvColumnsDontMatchTheNumberOfColumnValidators() {
        assertThrows(ValidationException.class, () -> {
            List<ColumnValidator> columnValidators = getSingleStringValidator();
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(columnValidators)
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfColumnValidatorsDontMatchCsvColumnsValidators() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(new ArrayList<>())
                    .build();
            dataValidator.validate(List.of("Column One"));
        });
    }

    private List<ColumnValidator> getSingleStringValidator() {
        return List.of(ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING)
                .build());
    }
}
