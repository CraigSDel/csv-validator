package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.exception.ValidationException;
import web.app.craigstroberg.enums.ColumnValidationType;

@Data
@Builder
public class LinkValidator implements ValidatorStrategy {

    private final ColumnValidationType columnValidationType = ColumnValidationType.LINK;
    private String columnDescription;
    private String validationPattern;

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
            return true;
        }
    }
}
