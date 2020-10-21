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
        ArrayList<ColumnValidator> columnValidators = getColumnValidators();

        DataValidator dataValidator = DataValidator.builder()
                .columnValidators(columnValidators)
                .firstRowIsHeader(Boolean.TRUE)
                .delimiter(",")
                .build();
        assertTrue(dataValidator.validate(getFile()));
    }

    private ArrayList<ColumnValidator> getColumnValidators() {
        ArrayList<ColumnValidator> columnValidators = new ArrayList<>();
        ColumnValidator video_id = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("video_id")
                .build();
        columnValidators.add(video_id);

        ColumnValidator trending_date = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("trending_date")
                .build();
        columnValidators.add(trending_date);

        ColumnValidator title = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("title")
                .build();
        columnValidators.add(title);

        ColumnValidator channel_title = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("channel_title")
                .build();
        columnValidators.add(channel_title);

        ColumnValidator category_id = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.INTEGER)
                .description("category_id")
                .build();
        columnValidators.add(category_id);

        ColumnValidator publish_time = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.DATE)
                .description("publish_time")
                .validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .build();
        columnValidators.add(publish_time);

        ColumnValidator tags = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("tags")
                .build();
        columnValidators.add(tags);

        ColumnValidator views = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("views")
                .build();
        columnValidators.add(views);

        ColumnValidator likes = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("likes")
                .build();
        columnValidators.add(likes);

        ColumnValidator dislikes = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("dislikes")
                .build();
        columnValidators.add(dislikes);

        ColumnValidator comment_count = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("comment_count")
                .build();
        columnValidators.add(comment_count);

        ColumnValidator thumbnail_link = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.LINK)
                .description("thumbnail_link")
                .validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                .build();
        columnValidators.add(thumbnail_link);

        ColumnValidator comments_disabled = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("comments_disabled")
                .build();
        columnValidators.add(comments_disabled);

        ColumnValidator ratings_disabled = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("ratings_disabled")
                .build();
        columnValidators.add(ratings_disabled);

        ColumnValidator video_error_or_removed = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("video_error_or_removed")
                .build();
        columnValidators.add(video_error_or_removed);

        ColumnValidator description = ColumnValidator.builder()
                .columnValidationType(ColumnValidationType.STRING_NOT_BLANK)
                .description("description")
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
