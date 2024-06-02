package web.app.craigstroberg.enums;

public enum ColumnValidationType {
    STRING_NOT_BLANK("String must not be empty or blank"),
    INTEGER("Must be a number"),
    LINK("Must be a link"),
    DATE("Must be a date");

    private String description;

    private ColumnValidationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
