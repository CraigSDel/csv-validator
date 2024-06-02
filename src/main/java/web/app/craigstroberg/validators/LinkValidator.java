package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.enums.ColumnValidationType;
import web.app.craigstroberg.exception.ValidationException;

@Data
@Builder
public class LinkValidator implements Validator {

    private final ColumnValidationType columnValidationType = ColumnValidationType.LINK;
    private final String columnDescription;
    private final String validationPattern;

    @Override
    public boolean validate(String value) {
        if (validationPattern == null || validationPattern.isBlank()) {
            throw new ValidationException(FIELD_REQUIRES_A_VALIDATION_PATTERN + value);
        }
        boolean matches = value.matches(validationPattern);
        if (!matches) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + COLUMN + columnDescription);
        } else {
            return Boolean.TRUE;
        }
    }
}
