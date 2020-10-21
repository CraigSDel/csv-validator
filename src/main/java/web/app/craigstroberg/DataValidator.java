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

    private final List<ColumnValidator> columnValidators;
    private final String delimiter;
    private final Boolean firstRowIsHeader;

    public boolean validate(List<String> csvData) throws ValidationException {
        if (isColumnValidationFieldsNullOrEmpty()) {
            throw new ValidationException("Validation fields can not be empty");
        }
        if (isCsvDataNullOrEmpty(csvData)) {
            throw new ValidationException("Csv data can not be empty");
        }
        if (!isCsvColumnSizeEqualToTheAmountOfColumnValidators(csvData)) {
            throw new ValidationException("Number of column validators do not match the csv columns");
        }
        if (firstRowIsHeader) {
            csvData.remove(0);
        }
        return validateCsvData(csvData);
    }

    private boolean validateCsvData(List<String> csvData) {
        if (delimiter == null || delimiter.isBlank()) {
            throw new ValidationException("Please configure a delimiter.");
        }
        csvData.stream().forEach(s -> {
            List<String> lineData = Arrays.asList(s.split("(?:" + delimiter + "|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\"" + delimiter + "\\n]*|(?:\\n|$))"));
            for (int i = 0; i < lineData.size(); i++) {
                columnValidators.get(i).validate(lineData.get(i));
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
