package mdn.assessment.logsearch.util.querycreator.configuration;

import mdn.assessment.logsearch.util.querycreator.constants.Param;
import mdn.assessment.logsearch.util.querycreator.enums.Operator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.function.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AbstractParamConfigurationTest {
    @Test
    public void testGetConfigurationWhenParamsWithValidParams() {
        // Assign
        ParamConfigurationInterface paramConfigurationInterface = new ParamConfigurationInterface() {
            @Override
            protected List<Param> constructConfiguration() {
                return new ArrayList<>(
                        Arrays.asList(
                                Param.builder()
                                        .key("uniqueKey1")
                                        .propertyName("Duplicate")
                                        .operator(Operator.EQUALS)
                                        .build(),

                                Param.builder()
                                        .key("uniqueKey2")
                                        .propertyName("Duplicate")
                                        .operator(Operator.CONTAINS)
                                        .build()
                        )
                );
            }
        };
        var expectedOutput = 2;

        // Act
        var actualResult = paramConfigurationInterface.constructConfiguration().size();


        // Assert
        Assertions.assertEquals(expectedOutput, actualResult);
    }

    @Test
    public void testGetConfigurationWhenParamsWithDuplicateKeysIsGiven() {
        // Assign
        ParamConfigurationInterface paramConfigurationInterface = new ParamConfigurationInterface() {
            @Override
            protected List<Param> constructConfiguration() {
                return new ArrayList<>(
                        Arrays.asList(
                                Param.builder()
                                        .key("duplicateKey")
                                        .propertyName("Duplicate")
                                        .operator(Operator.EQUALS)
                                        .build(),

                                Param.builder()
                                        .key("duplicateKey")
                                        .propertyName("Duplicate")
                                        .operator(Operator.CONTAINS)
                                        .build()
                        )
                );
            }
        };

        // Act
        Executable executable = () -> {
            paramConfigurationInterface.getConfiguration();
        };

        // Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                executable
        );
    }
}
