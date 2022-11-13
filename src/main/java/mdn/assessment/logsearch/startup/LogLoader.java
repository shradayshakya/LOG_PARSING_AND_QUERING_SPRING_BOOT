package mdn.assessment.logsearch.startup;

import mdn.assessment.logsearch.api.logs.service.LogService;
import mdn.assessment.logsearch.util.logparser.LogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LogLoader {

    @Autowired
    private LogParser logParser;

    @Autowired
    private LogService logService;

    private Logger logger = LoggerFactory.getLogger(LogLoader.class);

    private static final String LOG_FILE_NAME = "standard_log.log";

    /**
     * Parses log file and loads database.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initLogDatabase() {
        logger.info(String.format(
                "Parsing logs from %s file is in progress.", LOG_FILE_NAME
        ));

        var logs = logParser.parseLogs(LOG_FILE_NAME);

        logService.saveLogs(logs);

        logger.info(String.format(
                "Parsing logs from %s file was successfully completed.", LOG_FILE_NAME
        ));
    }
}
