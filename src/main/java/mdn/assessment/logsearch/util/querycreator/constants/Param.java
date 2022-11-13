package mdn.assessment.logsearch.util.querycreator.constants;

import lombok.Data;
import lombok.Builder;
import mdn.assessment.logsearch.util.querycreator.enums.Operator;

@Data
@Builder
public class Param {
    private String key;

    private String propertyName;

    private Operator operator;
}
