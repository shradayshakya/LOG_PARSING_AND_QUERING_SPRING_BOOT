package mdn.assessment.logsearch.util.querycreator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import mdn.assessment.logsearch.util.BaseUtil;
import mdn.assessment.logsearch.util.querycreator.constants.Param;
import mdn.assessment.logsearch.util.querycreator.configuration.ParamConfigurationInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryCreator {
    /**
     * Creates a query from the given params and params configuration.
     *
     * @param params
     * @param paramConfiguration
     * @param tClass
     * @return List of objects of given class.
     * @param <T> Class of the Entity.
     */
    public static <T> List<T> createQueryWithParams(
            Map<String,String> params,
            ParamConfigurationInterface paramConfiguration,
            Class<T> tClass,
            EntityManager entityManager
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        Predicate finalPredicate = constructFinalPredicateFromConfiguration(
                params,
                paramConfiguration,
                criteriaBuilder,
                root
        );

        assignPredicate(finalPredicate, criteriaQuery);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Constructs a predicate from the given params and paramconfiguration.
     *
     * @param params
     * @param paramConfiguration
     * @param criteriaBuilder
     * @param root
     * @return Final predicate combined with AND operator.
     * @param <T> Class of the Entity.
     */
    private static  <T> Predicate constructFinalPredicateFromConfiguration(
            Map<String,String> params,
            ParamConfigurationInterface paramConfiguration,
            CriteriaBuilder criteriaBuilder,
            Root<T> root
    ) {
        if(params.isEmpty()) {
            return null;
        }

        return criteriaBuilder.and(
                constructPredicatesFromConfiguration(
                        params,
                        paramConfiguration,
                        criteriaBuilder,
                        root
                )
        );
    }

    /**
     * Constructs a list of predicates from given params and configuration.
     *
     * @param params
     * @param paramConfiguration
     * @param criteriaBuilder
     * @param root
     * @return List of Predicates.
     * @param <T>
     */
    private static <T> Predicate[] constructPredicatesFromConfiguration(
            Map<String,String> params,
            ParamConfigurationInterface paramConfiguration,
            CriteriaBuilder criteriaBuilder,
            Root<T> root
    ) {
        var predicates = new ArrayList<Predicate>();
        var configuredKeysAndColumns = paramConfiguration.getConfiguration().stream().collect(
                Collectors.toMap(Param::getKey, Function.identity())
        );
        var configuredParamKeys = configuredKeysAndColumns.keySet();
        var validParamKeys = params.keySet().stream().filter(
                        paramKey -> configuredParamKeys.contains(paramKey))
                .collect(Collectors.toList());

        if(!validParamKeys.isEmpty()) {
            validParamKeys.stream().forEach(givenParamKey -> {
                var paramConfig = configuredKeysAndColumns.get(givenParamKey);
                var givenParamValue = params.get(givenParamKey);

                predicates.add(
                  constructPredicateFromOperator(givenParamValue, paramConfig, criteriaBuilder, root )
                );


            });
        }


        return predicates.toArray(new Predicate[predicates.size()]);
    }

    /**
     * Creates Predicate based on param configuration operator.
     * @param givenParamValue
     * @param paramConfig
     * @param criteriaBuilder
     * @param root
     * @return Predicate
     * @param <T>
     */
    private static <T> Predicate constructPredicateFromOperator(
            String givenParamValue,
            Param paramConfig,
            CriteriaBuilder criteriaBuilder,
            Root<T> root
    ) {

        switch(paramConfig.getOperator()){
            case EQUALS:
                return criteriaBuilder.equal(root.get(paramConfig.getPropertyName()), givenParamValue);
            case CONTAINS:
                return criteriaBuilder.like (
                        criteriaBuilder.lower(root.get(paramConfig.getPropertyName())),
                        "%" + givenParamValue.toLowerCase() + "%"
                );

            case DATE_TIME_GREATER_THAN:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(paramConfig.getPropertyName()), getDateTime(givenParamValue));

            case DATE_TIME_LESS_THAN:
                return criteriaBuilder.lessThanOrEqualTo(root.get(paramConfig.getPropertyName()), getDateTime(givenParamValue));

            default:
                throw new IllegalArgumentException("Invalid Operator provided in Param Configuration");
        }
    }

    /**
     * Assigns Predicate to the criteria query.
     * @param finalPredicate
     * @param criteriaQuery
     * @param <T>
     */
    private static  <T> void assignPredicate(Predicate finalPredicate, CriteriaQuery<T> criteriaQuery) {
        if(finalPredicate != null) {
            criteriaQuery.where(finalPredicate);
        }
    }

    /**
     * Cleans the date value and returns object of LocalDateTime object.
     *
     * @param paramValue
     * @return LocalDateTime
     */
    private static LocalDateTime getDateTime(String paramValue) {
        return BaseUtil.parseStringToDateTime(paramValue.replaceAll("T", " "));
    }
}
