package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.exception.ValidationException;
import web.app.craigstroberg.enums.ColumnValidationType;

@Data
@Builder
public class StringValidator implements ValidatorStrategy {

    private final ColumnValidationType columnValidationType = ColumnValidationType.STRING_NOT_BLANK;
    private String columnDescription;

    @Override
    public boolean validate(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + VALIDATION_TYPE + columnValidationType
                    + COLUMN + columnDescription);
        } else {
            return true;
        }
    }
}
