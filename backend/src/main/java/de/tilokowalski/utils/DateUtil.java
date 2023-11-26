package de.tilokowalski.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    /**
     * Converts java Date objects to LocalDateTime.
     *
     * @return LocalDateTime object
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
            dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static Date convertToDate(LocalDateTime dateToConvert) {
        return java.util.Date
            .from(dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
