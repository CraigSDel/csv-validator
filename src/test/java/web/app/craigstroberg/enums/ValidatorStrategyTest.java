package web.app.craigstroberg.enums;

import org.junit.jupiter.api.Test;
import web.app.craigstroberg.DataValidator;
import web.app.craigstroberg.validators.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorStrategyTest {

//    @Test
//    public void validatorWillThrowAnExceptionIfValidationFieldsAreNullOrEmpty() {
//        assertThrows(ValidationException.class, () -> {
//            DataValidator dataValidator = DataValidator.builder()
//                    .columnValidators(List.of(ColumnValidator.builder().build()))
//                    .build();
//            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
//        });
//    }
//
//    @Test
//    public void validatorWillThrowAnExceptionIfCsvDataIsNullOrEmpty() {
//        assertThrows(ValidationException.class, () -> {
//            DataValidator dataValidator = DataValidator.builder()
//                    .columnValidators(getSingleStringValidator())
//                    .build();
//            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
//        });
//    }
//
//    @Test
//    public void validatorWillThrowAnExceptionIfCsvColumnsDontMatchTheNumberOfColumnValidators() {
//        assertThrows(ValidationException.class, () -> {
//            List<ColumnValidator> columnValidators = getSingleStringValidator();
//            DataValidator dataValidator = DataValidator.builder()
//                    .columnValidators(columnValidators)
//                    .build();
//            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
//        });
//    }
//
//    @Test
//    public void validatorWillThrowAnExceptionIfColumnValidatorsDontMatchCsvColumnsValidators() {
//        assertThrows(ValidationException.class, () -> {
//            DataValidator dataValidator = DataValidator.builder()
//                    .columnValidators(new ArrayList<>())
//                    .build();
//            dataValidator.validateCsvDataReturningFailures(List.of("Column One"));
//        });
//    }

    @Test
    public void validatorSkippingTheHeaderRow() {
        ArrayList<ValidatorStrategy> columnValidators = getColumnValidators();

        DataValidator dataValidator = DataValidator.builder().columnValidators(columnValidators).firstRowIsHeader(Boolean.TRUE).delimiter(",").build();
        assertEquals(0, dataValidator.validateCsvDataReturningFailures(getFile()).size());
    }

    private ArrayList<ValidatorStrategy> getColumnValidators() {
        ArrayList<ValidatorStrategy> columnValidators = new ArrayList<>();
        columnValidators.add(StringValidator.builder().columnDescription("video_id").build());
        columnValidators.add(StringValidator.builder().columnDescription("trending_date").build());
        columnValidators.add(StringValidator.builder().columnDescription("title").build());
        columnValidators.add(StringValidator.builder().columnDescription("channel_title").build());
        columnValidators.add(IntegerValidator.builder().columnDescription("category_id").build());
        columnValidators.add(DateValidator.builder().columnDescription("publish_time").validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").build());
        columnValidators.add(StringValidator.builder().columnDescription("tags").build());
        columnValidators.add(StringValidator.builder().columnDescription("views").build());
        columnValidators.add(StringValidator.builder().columnDescription("likes").build());
        columnValidators.add(StringValidator.builder().columnDescription("dislikes").build());
        columnValidators.add(StringValidator.builder().columnDescription("comment_count").build());
        columnValidators.add(LinkValidator.builder().columnDescription("thumbnail_link").validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build());
        columnValidators.add(StringValidator.builder().columnDescription("comments_disabled").build());
        columnValidators.add(StringValidator.builder().columnDescription("ratings_disabled").build());
        columnValidators.add(StringValidator.builder().columnDescription("video_error_or_removed").build());
        columnValidators.add(StringValidator.builder().columnDescription("description").build());
        return columnValidators;
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
