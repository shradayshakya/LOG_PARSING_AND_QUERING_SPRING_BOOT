package mdn.assessment.logsearch.util;

import mdn.assessment.logsearch.constants.BaseConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BaseUtil {

    /**
     * Parse string and convert to date time.
     *
     * @param dateTime
     * @return instance of LocalDateTime
     */
    public static LocalDateTime parseStringToDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BaseConstants.DATE_TIME_FORMAT);
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch(DateTimeParseException dateTimeParseException) {
            return null;
        }
    }
}
