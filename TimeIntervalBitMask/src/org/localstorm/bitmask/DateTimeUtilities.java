/*
 * Created on 22.05.2008
 */

package org.localstorm.bitmask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


/**
 * Incapsulates all common operations with <code>Date</code> objects.
 * Contains only <code>static</code> methods.  
 */
public final class DateTimeUtilities
{
    public static final TimeZone UTC_TIMEZONE                  = TimeZone.getTimeZone("UTC");
    public static final TimeZone LOCAL_TIMEZONE                = TimeZone.getDefault();
    public static final TimeZone FIRST_AREA_TIMEZONE           = TimeZone.getTimeZone("Europe/Moscow");
    public static final TimeZone SECOND_AREA_TIMEZONE          = TimeZone.getTimeZone("Asia/Krasnoyarsk");
    public static final String   MONTHS_TIMESTAMP_FORMAT       = "yyyyMM";
    public static final String   DAYS_TIMESTAMP_FORMAT         = "yyyyMMdd";
    public static final String   MINUTES_TIMESTAMP_FORMAT      = "yyyyMMddHHmm";
    public static final String   SECONDS_TIMESTAMP_FORMAT      = "yyyyMMddHHmmss";
    public static final String   MILLISECONDS_TIMESTAMP_FORMAT = "yyyyMMddHHmmss,SSS";
    public static final String   HOURS_AND_MINUTES_TIME_FORMAT = "HHmm";
    public static final String   DEFAULT_TIMESTAMP_FORMAT      = DateTimeUtilities.SECONDS_TIMESTAMP_FORMAT;

    public static final String   HUMAN_DATE_FORMAT             = "dd.MM.yyyy";
    public static final String   HUMAN_TIME_FORMAT             = "HH:mm";
    public static final String   HUMAN_TIMESTAMP_FORMAT        = DateTimeUtilities.HUMAN_TIME_FORMAT + " "
                                                                 + DateTimeUtilities.HUMAN_DATE_FORMAT;

    private static final long    DAY_MSEC                      = 24 * 3600 * 1000;

    private DateTimeUtilities()
    {
    }

    public static Date addDays(Date initialDate,
                               int dayCount)
    {
        if (dayCount == 0)
        {
            return initialDate;
        }
        final Date ret = new Date();
        ret.setTime(initialDate.getTime() + dayCount * DateTimeUtilities.DAY_MSEC);
        return ret;
    }

    public static int dayCountBetween(Date startDate,
                                      Date endDate)
    {
        if (startDate == null)
        {
            throw new NullPointerException("Start date cannot be null");
        }
        if (endDate == null)
        {
            throw new NullPointerException("End date cannot be null");
        }
        if (startDate.after(endDate))
        {
            throw new IllegalArgumentException("End date must be after start date");
        }
        final long msec = endDate.getTime() - startDate.getTime();
        final long days = msec / DateTimeUtilities.DAY_MSEC + ((msec % DateTimeUtilities.DAY_MSEC > 0) ? 1 : 0);
        if (days > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("Days difference is too big - cannot fit value of " + days + " into integer");
        }
        return (int) days;
    }

    public static TimeZone getTimeZone(String timezoneId)
    {
        // There may be real-time check, if specified timezoneId exists,
        //     because TimeZone.getTimeZone() do not return null,
        //     but return GMT timezone, if specified ID is not found,
        //     and this may be source of errors.
        return TimeZone.getTimeZone(timezoneId);
    }

    public static String timestampToString(final Date date)
    {
        return DateTimeUtilities.timestampToString(date, DateTimeUtilities.UTC_TIMEZONE);
    }

    public static String timestampToString(final Date date,
                                           final String format)
    {
        return DateTimeUtilities.timestampToString(date, DateTimeUtilities.UTC_TIMEZONE, format);
    }

    public static String timestampToString(final Date date,
                                           final TimeZone timezone)
    {
        return DateTimeUtilities.timestampToString(date, timezone, DateTimeUtilities.DEFAULT_TIMESTAMP_FORMAT);
    }

    public static String timestampToString(final Date date,
                                           final TimeZone timezone,
                                           final String format)
    {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setCalendar(Calendar.getInstance(timezone));
        return simpleDateFormat.format(date);
    }


    public static DateFormat getDateFormat(String formatStr,
                                           TimeZone timeZone)
    {
        final SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setTimeZone(timeZone);
        return format;
    }


    public static Date parseDate(final String dateStr)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, DateTimeUtilities.UTC_TIMEZONE);
    }

    public static Date parseDate(final String dateStr,
                                 final String format)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, DateTimeUtilities.UTC_TIMEZONE, format);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, timezone, DateTimeUtilities.DEFAULT_TIMESTAMP_FORMAT);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 boolean isSummer)
        throws ParseException
    {
        final Date date = DateTimeUtilities.parseDate(dateStr, timezone);
        return DateTimeUtilities.updateDateWithSummerFlag(date, timezone, isSummer);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 final String format)
        throws ParseException
    {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setCalendar(Calendar.getInstance(timezone));
        return simpleDateFormat.parse(dateStr);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 final String format,
                                 boolean isSummer)
        throws ParseException
    {
        final Date date = DateTimeUtilities.parseDate(dateStr, timezone, format);
        return DateTimeUtilities.updateDateWithSummerFlag(date, timezone, isSummer);
    }

    private static Date updateDateWithSummerFlag(Date date,
                                                 TimeZone timezone,
                                                 boolean isSummer)
    {
        if (timezone.useDaylightTime())
        {
            if (timezone.inDaylightTime(date) && !isSummer)
            {
                // Must correct summer time to winter time
                date.setTime(date.getTime() - timezone.getDSTSavings());
            } else if (!timezone.inDaylightTime(date) && isSummer)
            {
                // Must correct winter time to summer time
                date.setTime(date.getTime() + timezone.getDSTSavings());
            }
        }
        return date;
    }

    public static TimeZone getRUTimeZoneByUTCOffset(final int utcOffsetSecondsToEast)
    {
        final String timeZoneId = "UTC" + (utcOffsetSecondsToEast > 0 ? "+" : "") + utcOffsetSecondsToEast
                                  + "s_TIMEZONE_WITH_RU_DST_SCHEDULE";
        return new SimpleTimeZone(utcOffsetSecondsToEast * 1000, timeZoneId, Calendar.MARCH, -1, Calendar.SUNDAY, 7200000,
            SimpleTimeZone.STANDARD_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 7200000, SimpleTimeZone.STANDARD_TIME, 3600000);
    }

    public static Calendar lastMidnightCalendar(TimeZone timezone)
    {
        if (timezone == null)
        {
            throw new NullPointerException("timezone is null");
        }

        final Calendar calendar = Calendar.getInstance(timezone);
        return DateTimeUtilities.toDayBegin(calendar);
    }

    public static Date lastMidnight(TimeZone timezone)
    {
        return DateTimeUtilities.lastMidnightCalendar(timezone).getTime();
    }

    public static Calendar lastLocalMidnightCalendar()
    {
        return DateTimeUtilities.lastMidnightCalendar(TimeZone.getDefault());
    }

    public static Date toLastMidnightUtc(Date d)
    {
        return new Date((d.getTime() / DateTimeUtilities.DAY_MSEC) * DateTimeUtilities.DAY_MSEC);
    }

    public static Date toNextMidnightUtc(Date d)
    {
        return new Date((d.getTime() / DateTimeUtilities.DAY_MSEC + 1) * DateTimeUtilities.DAY_MSEC);
    }

    public static Date lastLocalMidnight()
    {
        return DateTimeUtilities.lastLocalMidnightCalendar().getTime();
    }

    public static Calendar lastUTCMidnightCalendar()
    {
        return DateTimeUtilities.lastMidnightCalendar(DateTimeUtilities.UTC_TIMEZONE);
    }

    public static Date lastUTCMidnight()
    {
        return DateTimeUtilities.lastUTCMidnightCalendar().getTime();
    }

    public static Date yearStartDateUtc(int year)
    {
        return DateTimeUtilities.yearStartDate(DateTimeUtilities.UTC_TIMEZONE, year);
    }

    public static Date monthStartDateUtc(int year,
                                         int month)
    {
        return DateTimeUtilities.monthStartDate(DateTimeUtilities.UTC_TIMEZONE, year, month);
    }

    public static Date yearStartDate(TimeZone tz,
                                     int year)
    {
        return DateTimeUtilities.monthStartDate(tz, year, 0);
    }

    public static Date monthStartDate(TimeZone tz,
                                      int year,
                                      int month)
    {
        return DateTimeUtilities.monthStartCalendar(tz, year, month).getTime();
    }

    public static Calendar yearStartCalendar(TimeZone tz,
                                             int year)
    {
        return DateTimeUtilities.monthStartCalendar(tz, year, 1);
    }

    public static Calendar monthStartCalendar(TimeZone tz,
                                              int year,
                                              int month)
    {
        if (tz == null)
        {
            throw new NullPointerException("timezone is null");
        }
        return DateTimeUtilities.constructDayCalendar(year, month, 1, tz);
    }

    /**
     * Returns minimal date.
     * @param date1 Date.
     * @param date2 Date.
     * @return Minimal date.
     * @throws NullPointerException If <code>(date1 == null) || (date2 == null)</code>.
     */
    public static Date getMinDate(Date date1,
                                  Date date2)
    {
        if (date1 == null)
        {
            throw new NullPointerException("date1 parameter is null");
        }

        if (date2 == null)
        {
            throw new NullPointerException("date2 parameter is null");
        }

        return date1.after(date2) ? date2 : date1;
    }

    /**
     * Returns maximal date.
     * @param date1 Date.
     * @param date2 Date.
     * @return Maximal date.
     * @throws NullPointerException If <code>(date1 == null) || (date2 == null)</code>.
     */
    public static Date getMaxDate(Date date1,
                                  Date date2)
    {
        if (date1 == null)
        {
            throw new NullPointerException("date1 parameter is null");
        }

        if (date2 == null)
        {
            throw new NullPointerException("date2 parameter is null");
        }

        return date1.after(date2) ? date1 : date2;
    }

    public static void setLastMidnight(Calendar calendar)
    {
        DateTimeUtilities.toDayBegin(calendar);
    }

    /**
     * Returns calendar with local time zone.
     * @return Calendar with local time zone.
     */
    public static Calendar getLocalCalendar()
    {
        return Calendar.getInstance(DateTimeUtilities.LOCAL_TIMEZONE);
    }

    /**
     * Returns calendar with UTC time zone.
     * @return Calendar with UTC time zone.
     */
    public static Calendar getUTCCalendar()
    {
        return Calendar.getInstance(DateTimeUtilities.UTC_TIMEZONE);
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Date}.
     * @return {@link java.sql.Date} object.
     */
    public static java.sql.Date getSqlDate(Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Time}.
     * @return {@link java.sql.Time} object.
     */
    public static java.sql.Time getSqlTime(Date date)
    {
        return new java.sql.Time(date.getTime());
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Timestamp}.
     * @return {@link java.sql.Timestamp} object.
     */
    public static java.sql.Timestamp getSqlTimestamp(Date date)
    {
        return new java.sql.Timestamp(date.getTime());
    }


    private static Calendar getNthDayBeginCalendar(Date date,
                                                   TimeZone timeZone,
                                                   int dayOffset)
    {
        final Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        if (dayOffset != 0)
        {
            calendar.setLenient(true);
            calendar.add(Calendar.DAY_OF_YEAR, dayOffset);
        }
        return DateTimeUtilities.toDayBegin(calendar);
    }

    private static Calendar toDayBegin(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date getDayBeginForDate(Date date,
                                          TimeZone timeZone)
    {
        return DateTimeUtilities.getNthDayBeginCalendar(date, timeZone, 0).getTime();
    }

    public static Date getUtcDayBeginForDate(Date date,
                                             TimeZone timeZone)
    {
        final Calendar c = DateTimeUtilities.getNthDayBeginCalendar(date, timeZone, 0);
        return DateTimeUtilities.getDayBeginCalendar(c, DateTimeUtilities.UTC_TIMEZONE).getTime();
    }

    public static Date getUtcNextDayBeginForDate(Date date,
                                                 TimeZone timeZone)
    {
        final Calendar c = DateTimeUtilities.getNthDayBeginCalendar(date, timeZone, 1);
        return DateTimeUtilities.getDayBeginCalendar(c, DateTimeUtilities.UTC_TIMEZONE).getTime();
    }

    public static Date getMonthBeginForDate(Date date,
                                            TimeZone timeZone)
    {
        final Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(DateTimeUtilities.getDayBeginForDate(date, timeZone));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private static Calendar constructDayCalendar(int year,
                                                 int month,
                                                 int day,
                                                 TimeZone tz)
    {
        final Calendar c = Calendar.getInstance(tz);
        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("Month must be value between 1 and 12");
        }
        c.setLenient(false);
        c.set(year, month - 1, day);
        return DateTimeUtilities.toDayBegin(c);
    }

    private static Calendar getDayBeginCalendar(Calendar src,
                                                TimeZone dstTz)
    {
        return DateTimeUtilities.constructDayCalendar(src.get(Calendar.YEAR), src.get(Calendar.MONTH) + 1, src.get(Calendar.DAY_OF_MONTH),
            dstTz);
    }

    public static Date constructDayDate(int year,
                                        int month,
                                        int day,
                                        TimeZone tz)
    {
        return DateTimeUtilities.constructDayCalendar(year, month, day, tz).getTime();
    }
}
