package web.app.craigstroberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DataValidator {

    public static final String PLEASE_CONFIGURE_A_DELIMITER = "Please configure a delimiter.";
    public static final String REGEX_FOR_SPLITTING_CSV_DATA = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
    public static final String NUMBER_OF_COLUMN_VALIDATORS_DO_NOT_MATCH_THE_CSV_COLUMNS = "Number of column validators do not match the csv columns";
    public static final String CSV_DATA_CAN_NOT_BE_EMPTY = "Csv data can not be empty";
    public static final String VALIDATION_FIELDS_CAN_NOT_BE_EMPTY = "Validation fields can not be empty";
    private final List<ColumnValidator> columnValidators;
    private final String delimiter;
    private final Boolean firstRowIsHeader;

    public boolean validate(List<String> csvData) throws ValidationException {
        if (isColumnValidationFieldsNullOrEmpty()) {
            throw new ValidationException(VALIDATION_FIELDS_CAN_NOT_BE_EMPTY);
        }
        if (isCsvDataNullOrEmpty(csvData)) {
            throw new ValidationException(CSV_DATA_CAN_NOT_BE_EMPTY);
        }
        if (!isCsvColumnSizeEqualToTheAmountOfColumnValidators(csvData)) {
            throw new ValidationException(NUMBER_OF_COLUMN_VALIDATORS_DO_NOT_MATCH_THE_CSV_COLUMNS);
        }
        if (firstRowIsHeader) {
            csvData.remove(0);
        }
        return validateCsvData(csvData);
    }

    private boolean validateCsvData(List<String> csvData) {
        if (delimiter == null || delimiter.isBlank()) {
            throw new ValidationException(PLEASE_CONFIGURE_A_DELIMITER);
        }
        csvData.stream().forEach(s -> {
            List<String> lineData = Arrays.asList(s.split(REGEX_FOR_SPLITTING_CSV_DATA));
            try {
                for (int i = 0; i < columnValidators.size(); i++) {
                    columnValidators.get(i).validate(lineData.get(i));
                }
            } catch (Exception e) {
                throw new ValidationException(s, e);
            }
        });
        return Boolean.TRUE;
    }

    private boolean isCsvColumnSizeEqualToTheAmountOfColumnValidators(List<String> csvData) {
        if (csvData.get(0).split(delimiter).length == columnValidators.size()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean isCsvDataNullOrEmpty(List<String> csvData) {
        if (csvData == null || csvData.isEmpty()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean isColumnValidationFieldsNullOrEmpty() {
        if (columnValidators == null || columnValidators.isEmpty()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
