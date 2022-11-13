package mdn.assessment.logsearch.api.logs.service;

import mdn.assessment.logsearch.api.logs.model.Log;
import mdn.assessment.logsearch.api.logs.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    /**
     * Returns server logs.
     *
     * @param params Query Parameters
     * @return Log lists
     */
    public List<Log> fetchLogs(Map<String,String> params) {
        return logRepository.fetchLogs(params);
    }

    /**
     * Saves a list of provided logs.
     * @param logs
     * @return List<Log>
     */
    public List<Log> saveLogs(List<Log> logs) {
        return logRepository.saveAll(logs);
    }
}
