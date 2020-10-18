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

    public static final boolean TRUE = true;
    public static final boolean FALSE = false;
    private final List<ColumnValidator> columnValidators;
    private final String delimiter;

    public boolean validate(List<String> csvData) throws ValidationException {
        if (isColumnValidationFieldsNullOrEmpty()) {
            throw new ValidationException("Validation fields can not be empty");
        }
        if (isCsvDataNullOrEmpty(csvData)) {
            throw new ValidationException("Csv data can not be empty");
        }
        if (doesCsvColumnSizeEqualAmountOfColumnValidators(csvData)) {
            throw new ValidationException("Number of column validators do not match the csv columns");
        }
        return validateCsvData(csvData);
    }

    private boolean validateCsvData(List<String> csvData) {
        csvData.stream().forEach(s -> {
            List<String> lineData = Arrays.asList(s.split(delimiter));
            for (int i = 0; i < lineData.size(); i++) {
                columnValidators.get(i).validate(lineData.get(i));
            }
        });
        return TRUE;
    }

    private boolean doesCsvColumnSizeEqualAmountOfColumnValidators(List<String> csvData) {
        if (columnValidators.size() != csvData.size()) {
            return TRUE;
        }
        return FALSE;
    }

    private boolean isCsvDataNullOrEmpty(List<String> csvData) {
        if (csvData == null || csvData.isEmpty()) {
            return FALSE;
        }
        return TRUE;
    }

    private boolean isColumnValidationFieldsNullOrEmpty() {
        if (columnValidators == null || columnValidators.isEmpty()) {
            return FALSE;
        }
        return TRUE;
    }
}
