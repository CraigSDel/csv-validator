package web.app.craigstroberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DataValidator {

    private final List<ColumnValidator> columnValidators;

    public boolean validate(List<String> csvData) throws ValidationException {
        if (isColumnValidationFieldsNullOrEmpty()) {
            throw new ValidationException("Validation fields can not be empty");
        }
        if (isCsvDataNullOrEmpty(csvData)) {
            throw new ValidationException("Csv data can not be empty");
        }
        if (doesCsvColumnSizeEqualColumnValidators(csvData)) {
            throw new ValidationException("Number of column validators do not match the csv columns");
        }
        return validateCsvData(csvData);
    }

    private boolean validateCsvData(List<String> csvData) {
        return false;
    }

    private boolean doesCsvColumnSizeEqualColumnValidators(List<String> csvData) {
        if (columnValidators.size() != csvData.size()) {
            return true;
        }
        return false;
    }

    private boolean isCsvDataNullOrEmpty(List<String> csvData) {
        if (csvData == null || csvData.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isColumnValidationFieldsNullOrEmpty() {
        if (columnValidators == null || columnValidators.isEmpty()) {
            return false;
        }
        return true;
    }
}
