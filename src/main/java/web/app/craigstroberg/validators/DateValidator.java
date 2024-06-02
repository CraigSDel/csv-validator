package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.exception.ValidationException;
import web.app.craigstroberg.enums.ColumnValidationType;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class DateValidator implements ValidatorStrategy {

    private final ColumnValidationType columnValidationType = ColumnValidationType.DATE;
    private String columnDescription;
    private String validationPattern;

    @Override
    public boolean validate(String value) {
        if (validationPattern == null || validationPattern.isBlank()) {
            throw new ValidationException(FIELD_REQUIRES_A_VALIDATION_PATTERN + value
                    + COLUMN + columnDescription);
        }
        try {
            DateTimeFormatter.ofPattern(validationPattern);
            return true;
        } catch (Exception exception) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + EXCEPTION + exception
                    + COLUMN + columnDescription);
        }
    }
}
