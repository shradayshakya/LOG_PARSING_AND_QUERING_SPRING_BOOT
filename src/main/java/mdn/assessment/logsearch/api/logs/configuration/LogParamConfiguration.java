package mdn.assessment.logsearch.api.logs.configuration;

import mdn.assessment.logsearch.util.querycreator.configuration.ParamConfigurationInterface;
import mdn.assessment.logsearch.util.querycreator.constants.Param;
import mdn.assessment.logsearch.util.querycreator.enums.Operator;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class LogParamConfiguration extends ParamConfigurationInterface {

    /**
     * Returns list of query params along with the operator for logs.
     *
     * @return List of params.
     */
    @Override
    protected List<Param> constructConfiguration() {
        return new ArrayList<>(
                Arrays.asList(
                        Param.builder()
                                .key("level")
                                .propertyName("level")
                                .operator(Operator.EQUALS)
                                .build(),

                        Param.builder()
                                .key("message")
                                .propertyName("message")
                                .operator(Operator.CONTAINS)
                                .build(),

                        Param.builder()
                                .key("startTime")
                                .propertyName("time")
                                .operator(Operator.DATE_TIME_GREATER_THAN)
                                .build(),

                        Param.builder()
                                .key("endTime")
                                .propertyName("time")
                                .operator(Operator.DATE_TIME_LESS_THAN)
                                .build()
                )
        );
    }
}
