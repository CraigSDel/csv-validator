package web.app.craigstroberg;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataValidatorTest {

    @Test
    public void validatorWillThrowAnExceptionIfValidationFieldsAreNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(List.of(ColumnValidator.builder().build()))
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvDataIsNullOrEmpty() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(getSingleStringValidator())
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfCsvColumnsDontMatchTheNumberOfColumnValidators() {
        assertThrows(ValidationException.class, () -> {
            List<ColumnValidator> columnValidators = getSingleStringValidator();
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(columnValidators)
                    .build();
            dataValidator.validateCsvDataReturningFailures(new ArrayList<>());
        });
    }

    @Test
    public void validatorWillThrowAnExceptionIfColumnValidatorsDontMatchCsvColumnsValidators() {
        assertThrows(ValidationException.class, () -> {
            DataValidator dataValidator = DataValidator.builder()
                    .columnValidators(new ArrayList<>())
                    .build();
            dataValidator.validateCsvDataReturningFailures(List.of("Column One"));
        });
    }

    @Test
    public void validatorSkippingTheHeaderRow() {
        ArrayList<ColumnValidator> columnValidators = getColumnValidators();

        DataValidator dataValidator = DataValidator.builder()
                .columnValidators(columnValidators)
                .firstRowIsHeader(Boolean.TRUE)
                .delimiter(",")
                .build();
        assertEquals(0, dataValidator.validateCsvDataReturningFailures(getFile()).size());
    }

    private ArrayList<ColumnValidator> getColumnValidators() {
        ArrayList<ColumnValidator> columnValidators = new ArrayList<>();
        ColumnValidator video_id = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("video_id")
                .build();
        columnValidators.add(video_id);

        ColumnValidator trending_date = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("trending_date")
                .build();
        columnValidators.add(trending_date);

        ColumnValidator title = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("title")
                .build();
        columnValidators.add(title);

        ColumnValidator channel_title = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("channel_title")
                .build();
        columnValidators.add(channel_title);

        ColumnValidator category_id = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.INTEGER)
                .columnDescription("category_id")
                .build();
        columnValidators.add(category_id);

        ColumnValidator publish_time = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.DATE)
                .columnDescription("publish_time")
                .validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .build();
        columnValidators.add(publish_time);

        ColumnValidator tags = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("tags")
                .build();
        columnValidators.add(tags);

        ColumnValidator views = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("views")
                .build();
        columnValidators.add(views);

        ColumnValidator likes = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("likes")
                .build();
        columnValidators.add(likes);

        ColumnValidator dislikes = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("dislikes")
                .build();
        columnValidators.add(dislikes);

        ColumnValidator comment_count = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("comment_count")
                .build();
        columnValidators.add(comment_count);

        ColumnValidator thumbnail_link = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.LINK)
                .columnDescription("thumbnail_link")
                .validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                .build();
        columnValidators.add(thumbnail_link);

        ColumnValidator comments_disabled = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("comments_disabled")
                .build();
        columnValidators.add(comments_disabled);

        ColumnValidator ratings_disabled = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("ratings_disabled")
                .build();
        columnValidators.add(ratings_disabled);

        ColumnValidator video_error_or_removed = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("video_error_or_removed")
                .build();
        columnValidators.add(video_error_or_removed);

        ColumnValidator description = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .columnDescription("description")
                .build();
        columnValidators.add(description);


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

    private List<ColumnValidator> getSingleStringValidator() {
        return List.of(ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .build());
    }

}
