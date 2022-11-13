package mdn.assessment.logsearch.util.querycreator.configuration;

import mdn.assessment.logsearch.util.querycreator.constants.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ParamConfigurationInterface {

    /**
     * Returns list of query params with operators.
     *
     * @return list of params
     */
     protected abstract List<Param> constructConfiguration();


    /**
     * Validates configuration and returns configuration.
     *
     * @return list of params
     */
     public List<Param> getConfiguration() {
         var configuration = constructConfiguration();
         var paramKeys = configuration.stream().map(Param::getKey).collect(Collectors.toList());

         Set<String> paramSet = new HashSet<>(paramKeys);
         if(paramKeys.size() != paramSet.size()) {
             throw new IllegalArgumentException("The keys in the Param Configuration should be unique");
         }

         return configuration;
     }
}
