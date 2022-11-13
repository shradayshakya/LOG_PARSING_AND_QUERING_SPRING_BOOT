package mdn.assessment.logsearch.util.logparser;

import mdn.assessment.logsearch.api.logs.model.Log;
import mdn.assessment.logsearch.constants.BaseConstants;
import mdn.assessment.logsearch.util.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogParser {
    private final Logger logger = LoggerFactory.getLogger(LogParser.class);
    private final String LOG_REGEX = "([A-Z]{4,5}\\s?:) (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}) (.*)";

    /**
     * Parses a list of logs from the given file.
     *
     * @param fileName
     * @return list of logs
     */
    public List<Log> parseLogs(String fileName) {
        List<Log> logs = new ArrayList<>();
        try(
                FileInputStream stream = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            String strLine;
            Log previousLog = null;

            while((strLine = reader.readLine()) != null) {

                var currentLog = parseLog(strLine);

                if(currentLog == null && previousLog != null) {
                    previousLog.setMessage(previousLog.getMessage() + strLine);
                } else {
                    previousLog = currentLog;
                    logs.add(currentLog);
                }
            }
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        return logs;
    }

    /**
     * Parse a log from a line.
     * Returns null if the pattern doesn't matches.
     *
     * @param record
     * @return a Log
     */
    private Log parseLog(String record){
        Log log = new Log();

        final Pattern pattern = Pattern.compile(LOG_REGEX, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(record);

        if (matcher.find( )) {
            String level = matcher.group(1).replaceAll("\\s?:", "");
            String time = matcher.group(2);
            String message = matcher.group(3);

            LocalDateTime dateTime = BaseUtil.parseStringToDateTime(time);

            log.setLevel(level);
            log.setTime(dateTime);
            log.setMessage(message);

        } else {
            return null;
        }

        return log;
    }
}
