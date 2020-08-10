package com.zero.midas.utils;

import com.zero.midas.exception.MidasException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{dateTimeStr}));
        }
        if (dateTimeStr.contains(".")) {
            return parseLocalDateTime(dateTimeStr, "yyyy-MM-dd HH:mm:ss.SSS");
        }
        return parseLocalDateTime(dateTimeStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{dateTimeStr}));
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr.contains("-")) {
            return parseLocalDate(dateStr, "yyyy-MM-dd");
        }
        return parseLocalDate(dateStr, "yyyyMMdd");
    }

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        if (dateStr.length() == 8) {
            pattern = "yyyyMMdd";
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseLocalTime(String time) {
        if (StringUtils.isEmpty(time)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{time}));
        }
        if (time.contains(".")) {
            return parseLocalTime(time, "HH:mm:ss.SSS");
        }
        return parseLocalTime(time, "HH:mm:ss");
    }

    public static LocalTime parseLocalTime(String time, String pattern) {
        if (StringUtils.isEmpty(time)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{time}));
        }
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseUTCToLocalDateTime(String utcTime)
            throws ParseException {
        try {
            return parseUTCToLocalDateTime(utcTime, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        } catch (ParseException e) {
        }
        return parseUTCToLocalDateTime(utcTime, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public static LocalDateTime parseUTCToLocalDateTime(String utcTime, String pattern)
            throws ParseException {
        if (StringUtils.isEmpty(utcTime)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{utcTime}));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = sdf.parse(utcTime);
        return utcDate == null ? null : toLocalDateTime(utcDate);
    }

    public static String formatTime(LocalDateTime time) {
        try {
            return formatTime(time, "yyyy-MM-dd HH:mm:ss.SSS");
        } catch (Exception e) {
        }
        return formatTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDate(LocalDate date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            throw new MidasException("需要转换的时间为空");
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();


        return instant.atZone(zoneId).toLocalDate();
    }

    public static Date toDate(LocalDate date) {
        if (date == null) {
            throw new MidasException("需要转换的时间为空");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            throw new MidasException("需要转换的时间为空");
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();


        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new MidasException("需要转换的时间为空");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static long toMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Long millisecond) {
        if (millisecond == null) {
            throw new MidasException("需要转换的时间为空");
        }
        Instant instant = Instant.ofEpochMilli(millisecond.longValue());
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime parseLocalDateTimeWithStandardParrent(String dateTimeStr) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            throw new MidasException(String.format("[{}]时间格式有误不支持", new Object[]{dateTimeStr}));
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
        return localDateTime;
    }

    public static LocalDateTime convertUnixTime(long time) {
        return toLocalDateTime(Long.valueOf(time * 1000L));
    }
}
