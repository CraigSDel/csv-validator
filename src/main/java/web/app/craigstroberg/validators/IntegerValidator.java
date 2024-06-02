package web.app.craigstroberg.validators;

import lombok.Builder;
import lombok.Data;
import web.app.craigstroberg.enums.ColumnValidationType;
import web.app.craigstroberg.exception.ValidationException;

import java.math.BigInteger;

@Data
@Builder
public class IntegerValidator implements Validator {

    private final ColumnValidationType columnValidationType = ColumnValidationType.INTEGER;
    private final String columnDescription;

    @Override
    public boolean validate(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException numberFormatException) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + value
                    + EXCEPTION + numberFormatException.getMessage()
                    + COLUMN + columnDescription);
        }
    }
}
