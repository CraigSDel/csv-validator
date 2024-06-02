package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.enums.ColumnValidationType;
import web.app.craigstroberg.exception.ValidationException;

@Data
@Builder
public class StringValidator implements Validator {

    private final ColumnValidationType columnValidationType = ColumnValidationType.STRING_NOT_BLANK;
    private final String columnDescription;

    @Override
    public boolean validate(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + VALIDATION_TYPE + columnValidationType
                    + COLUMN + columnDescription);
        } else {
            return Boolean.TRUE;
        }
    }
}
