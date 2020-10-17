package web.app.craigstroberg;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ColumnValidator {

    private String description;
    private ColumnValidationType columnValidationType;
}
