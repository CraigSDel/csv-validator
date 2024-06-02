package web.app.craigstroberg;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.exception.ValidationException;
import web.app.craigstroberg.validators.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataValidatorTest {

    @Test
    public void validatorWillThrowAnExceptionIfValidationFieldsAreNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = web.app.craigstroberg.DataValidator.builder()
                    .columnValidators(List.of(DateValidator.builder().build()))
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvDataIsNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = web.app.craigstroberg.DataValidator.builder()
                    .columnValidators(List.of(StringValidator.builder().build()))
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvColumnsDontMatchTheNumberOfColumnValidators() {
        assertThrows(ValidationException.class, () -> {
            List<Validator> columnValidators = List.of(StringValidator.builder().build());
            DataValidator dataValidator = web.app.craigstroberg.DataValidator.builder()
                    .columnValidators(columnValidators)
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfColumnValidatorsDontMatchCsvColumnsValidators() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = web.app.craigstroberg.DataValidator.builder()
                    .columnValidators(new ArrayList<>())
                    .build();
            dataValidator.validateCsvDataReturningFailures(List.of("Column One"));
        });
    }

    @Test
    public void validatorSkippingTheHeaderRow() {
        List<Validator> columnValidators = getColumnValidators();

        DataValidator dataValidator = DataValidator.builder().columnValidators(columnValidators).firstRowIsHeader(Boolean.TRUE).delimiter(",").build();
        assertEquals(0, dataValidator.validateCsvDataReturningFailures(getFile()).size());
    }

    private List<Validator> getColumnValidators() {
        List<Validator> validators = new ArrayList<>();
        validators.add(StringValidator.builder().columnDescription("video_id").build());
        validators.add(StringValidator.builder().columnDescription("trending_date").build());
        validators.add(StringValidator.builder().columnDescription("title").build());
        validators.add(StringValidator.builder().columnDescription("channel_title").build());
        validators.add(IntegerValidator.builder().columnDescription("category_id").build());
        validators.add(DateValidator.builder().columnDescription("publish_time").validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").build());
        validators.add(StringValidator.builder().columnDescription("tags").build());
        validators.add(StringValidator.builder().columnDescription("views").build());
        validators.add(StringValidator.builder().columnDescription("likes").build());
        validators.add(StringValidator.builder().columnDescription("dislikes").build());
        validators.add(StringValidator.builder().columnDescription("comment_count").build());
        validators.add(LinkValidator.builder().columnDescription("thumbnail_link").validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build());
        validators.add(StringValidator.builder().columnDescription("comments_disabled").build());
        validators.add(StringValidator.builder().columnDescription("ratings_disabled").build());
        validators.add(StringValidator.builder().columnDescription("video_error_or_removed").build());
        validators.add(StringValidator.builder().columnDescription("description").build());
        return validators;
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

}
