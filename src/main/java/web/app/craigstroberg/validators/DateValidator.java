package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.enums.ColumnValidationType;
import web.app.craigstroberg.exception.ValidationException;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class DateValidator implements Validator {

    private final ColumnValidationType columnValidationType = ColumnValidationType.DATE;
    private final String columnDescription;
    private final String validationPattern;

    @Override
    public boolean validate(String value) {
        if (validationPattern == null || validationPattern.isBlank()) {
            throw new ValidationException(FIELD_REQUIRES_A_VALIDATION_PATTERN + value
                    + COLUMN + columnDescription);
        }
        try {
            DateTimeFormatter.ofPattern(validationPattern);
            return Boolean.TRUE;
        } catch (Exception exception) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + EXCEPTION + exception
                    + COLUMN + columnDescription);
        }
    }
}
