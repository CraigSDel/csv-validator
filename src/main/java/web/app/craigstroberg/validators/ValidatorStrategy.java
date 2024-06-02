package web.app.craigstroberg.validators;

public interface ValidatorStrategy {

    String ITEM_FAILED_VALIDATION = "Item failed validation : ";
    String EXCEPTION = " Exception : ";
    String FIELD_REQUIRES_A_VALIDATION_PATTERN = "Field requires a validation pattern ";
    String COLUMN = " Column ";
    String VALIDATION_TYPE = " Validation Type ";

    boolean validate(String value);
}
