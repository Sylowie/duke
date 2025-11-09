package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting date and datetime strings
 * Output Formats
 * Date: {@code MMM d yyyy} (e.g., {@code Dec 2 2020})
 * DateTime: {@code MMM d yyyy, h:mma} (e.g., {@code Dec 2 2020, 6pm})
 * Time: {@code h:mma} (e.g., {@code 6pm})
 */
public final class DateTime {
    private DateTime() {
    }

    private static final DateTimeFormatter INPUT_ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_SLASH_DATE = DateTimeFormatter.ofPattern("d/M/yyyy");

    private static final DateTimeFormatter INPUT_ISO_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_SLASH_DATETIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static final DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter OUTPUT_TIME_12H = DateTimeFormatter.ofPattern("h:mma");

    /**
     * Parses a string representation of a date into a LocalDate object
     * Accepts both ISO date format (yyyy-MM-dd) and
     * slash-separated date format (d/M/yyyy)
     * Throws an IllegalArgumentException if input string is an
     * invalid format
     * 
     * @param s the string representation of the date to parse
     * @return the parsed LocalDate object
     * @throws IllegalArgumentException if the input string is in an invalid format
     */
    public static LocalDate parseDate(String s) {
        s = s.trim();
        try {
            return LocalDate.parse(s, INPUT_ISO_DATE);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(s, INPUT_SLASH_DATE);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException(
                        "Invalid date format. Use yyyy-MM-dd (e.g. 2020-12-02) or d/M/yyyy (e.g. 2/12/2020)");
            }
        }
    }

    /**
     * Parses a string representation of a datetime into a LocalDateTime object
     * Accepts both ISO date time format (yyyy-MM-dd HHmm) and
     * slash-separated date time format (d/M/yyyy HHmm)
     * Throws an IllegalArgumentException if the input string is in an
     * invalid format
     * 
     * @param s the string representation of the datetime to parse
     * @return the parsed LocalDateTime object
     * @throws IllegalArgumentException if the input string is in an invalid format
     */
    public static LocalDateTime parseDateTime(String s) {
        s = s.trim();
        try {
            return LocalDateTime.parse(s, INPUT_ISO_DATETIME);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDateTime.parse(s, INPUT_SLASH_DATETIME);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException(
                        "Invalid datetime format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
            }
        }
    }

    /**
     * Returns a string representation of the given date in the format "MMM d yyyy"
     * 
     * @param d the date to format
     * @return a string representation of the date
     */
    public static String outputDate(LocalDate d) {
        return d.format(OUTPUT_DATE);
    }

    /**
     * Returns a string representation of the given datetime in the format "MMM d
     * yyyy, h:mma"
     * 
     * @param dt the datetime to format
     * @return a string representation of the datetime
     */
    public static String outputDateTime(LocalDateTime dt) {
        String date = dt.format(OUTPUT_DATE);
        String time = dt.format(OUTPUT_TIME_12H).replace(":00", "").toLowerCase();
        return date + ", " + time;
    }

    /**
     * Returns a string representation of the given datetime's time in the format
     * "h:mma".
     * If the minutes are zero, only the hour is returned.
     * The returned string is in lowercase.
     * 
     * @param dt the datetime to format
     * @return a string representation of the datetime's time
     */
    public static String outputTime(LocalDateTime dt) {
        return dt.format(OUTPUT_TIME_12H).replace(":00", "").toLowerCase();
    }

    /**
     * Returns a string representation of the given raw datetime in the format "MMM
     * d yyyy, h:mma".
     * If parsing fails, the raw string is returned instead.
     * 
     * @param raw the raw datetime string to format
     * @return a string representation of the datetime, or the raw string if parsing
     *         fails
     */
    public static String formatRawDateTimeSafe(String raw) {
        try {
            return outputDateTime(parseDateTime(raw));
        } catch (IllegalArgumentException ex) {
            return raw; // fallback to raw if parsing fails
        }
    }
}
