package web.app.craigstroberg.enums;

import lombok.Getter;

@Getter
public enum ColumnValidationType {

    STRING_NOT_BLANK("String must not be empty or blank"),
    INTEGER("Must be a number"),
    LINK("Must be a link"),
    DATE("Must be a date");

    private final String description;

    ColumnValidationType(String description) {
        this.description = description;
    }

}
