package web.app.craigstroberg;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Builder
@Data
public class ColumnValidator {

    public static final String ITEM_FAILED_VALIDATION = "Item failed validation : ";
    public static final String EXCEPTION = " Exception : ";
    public static final String VALIDATION_TYPE = " Validation Type ";
    private String description;
    private ColumnValidationType columnValidationType;

    public Boolean validate(String item) {
        if (ColumnValidationType.STRING_NOT_BLANK.equals(columnValidationType)) {
            if(item == null || item.isBlank()){
                throw new ValidationException(ITEM_FAILED_VALIDATION + item + VALIDATION_TYPE + columnValidationType.getDescription());
            }
        }
        if (ColumnValidationType.INTEGER.equals(columnValidationType)) {
            try {
                new BigInteger(item);
            } catch (NumberFormatException numberFormatException) {
                throw new ValidationException(ITEM_FAILED_VALIDATION + item + EXCEPTION + numberFormatException.getMessage());
            }
        }
        return true;
    }
}
