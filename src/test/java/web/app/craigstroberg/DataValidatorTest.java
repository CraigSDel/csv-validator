package web.app.craigstroberg;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataValidatorTest {

    @Test
    public void validatorWillThrowAnExceptionIfValidationFieldsAreNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(List.of(ColumnValidator.builder().build()))
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvDataIsNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(getSingleStringValidator())
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvColumnsDontMatchTheNumberOfColumnValidators() {
        assertThrows(ValidationException.class, () -> {
            List<ColumnValidator> columnValidators = getSingleStringValidator();
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(columnValidators)
                    .build();
            dataValidator.validate(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfColumnValidatorsDontMatchCsvColumnsValidators() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(new ArrayList<>())
                    .build();
            dataValidator.validate(List.of("Column One"));
        });
    }

    @Test
    public void validatorSkippingTheHeaderRow() {
        ArrayList<ColumnValidator> columnValidators = new ArrayList<>();
        ColumnValidator video_id = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("video_id")
                .build();

        ColumnValidator trending_date = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("trending_date")
                .build();

        ColumnValidator title = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("title")
                .build();

        columnValidators.add(video_id);
        columnValidators.add(trending_date);
        columnValidators.add(title);

        DataValidator dataValidator = DataValidator.builder()
                .columnValidators(columnValidators)
                .firstRowIsHeader(Boolean.TRUE)
                .delimiter(",")
                .build();
        assertTrue(dataValidator.validate(getFile()));
    }

    private List<String> getFile() {
        Path filePath = Paths.get("src/test/resources/ThreeLinesCAvideos.csv");
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ColumnValidator> getSingleStringValidator() {
        return List.of(ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .build());
    }

}
