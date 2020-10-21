package web.app.craigstroberg;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;

@Builder
@Data
public class ColumnValidator {

    public static final String ITEM_FAILED_VALIDATION = "Item failed validation : ";
    public static final String EXCEPTION = " Exception : ";
    public static final String VALIDATION_TYPE = " Validation Type ";
    public static final String FIELD_REQUIRES_A_VALIDATION_PATTERN = "Field requires a validation pattern ";
    private String description;
    private ColumnValidationType columnValidationType;
    private String validationPattern;

    public Boolean validate(String item) {
        if (ColumnValidationType.STRING_NOT_BLANK.equals(columnValidationType)) {
            stringNotBlank(item);
        }
        if (ColumnValidationType.INTEGER.equals(columnValidationType)) {
            integer(item);
        }
        if (ColumnValidationType.LINK.equals(columnValidationType)) {
            link(item);
        }
        if (ColumnValidationType.DATE.equals(columnValidationType)) {
            date(item);
        }
        return true;
    }

    /**
     * Example date pattern "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @param item
     */
    private void date(String item) {
        if (validationPattern == null || validationPattern.isBlank()) {
            throw new ValidationException(FIELD_REQUIRES_A_VALIDATION_PATTERN + item);
        }
        try {
            DateTimeFormatter.ofPattern(validationPattern);
        } catch (Exception exception) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + item + EXCEPTION + exception);
        }
    }

    /**
     * pattern regex example: ^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
     * @param item
     */
    private void link(String item) {
        if (validationPattern == null || validationPattern.isBlank()) {
            throw new ValidationException(FIELD_REQUIRES_A_VALIDATION_PATTERN + item);
        }
        boolean matches = item.matches(validationPattern);
        if (!matches) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + item);
        }
    }

    private void integer(String item) {
        try {
            new BigInteger(item);
        } catch (NumberFormatException numberFormatException) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + item + EXCEPTION + numberFormatException.getMessage());
        }
    }

    private void stringNotBlank(String item) {
        if (item == null || item.isBlank()) {
            throw new ValidationException(ITEM_FAILED_VALIDATION + item + VALIDATION_TYPE + columnValidationType.getDescription());
        }
    }
}
