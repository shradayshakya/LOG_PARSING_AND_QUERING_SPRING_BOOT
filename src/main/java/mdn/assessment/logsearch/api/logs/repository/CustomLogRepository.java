package mdn.assessment.logsearch.api.logs.repository;

import mdn.assessment.logsearch.api.logs.model.Log;

import java.util.List;
import java.util.Map;

public interface CustomLogRepository {

    /**
     * Returns server logs.
     *
     * @param params Query Parameters
     * @return Log lists
     */
    List<Log> fetchLogs(Map<String,String> params);
}
