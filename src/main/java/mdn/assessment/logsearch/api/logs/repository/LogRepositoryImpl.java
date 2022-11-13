package mdn.assessment.logsearch.api.logs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mdn.assessment.logsearch.api.logs.configuration.LogParamConfiguration;
import mdn.assessment.logsearch.api.logs.model.Log;
import mdn.assessment.logsearch.util.querycreator.QueryCreator;

import java.util.List;
import java.util.Map;

public class LogRepositoryImpl implements CustomLogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns server logs.
     *
     * @param params Query Parameters
     * @return Log lists
     */
    @Override
    public List<Log> fetchLogs(Map<String,String> params) {
        var paramConfiguration = new LogParamConfiguration();

        return QueryCreator.createQueryWithParams(params, paramConfiguration, Log.class, entityManager);
    }
}
