package com.pichicha.reto.app.api.utils;

import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {
        // Empty constructor.
    }

    public static Date getWithFirstTimeOfDay(Date date, String zoneId) {
        return Date.from(date.toInstant()
                .atZone(ZoneId.of(zoneId))
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .toInstant());
    }

    public static Date getWithLastTimeOfDay(Date date, String zoneId) {
        return Date.from(date.toInstant()
                .atZone(ZoneId.of(zoneId))
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .toInstant());
    }
}
