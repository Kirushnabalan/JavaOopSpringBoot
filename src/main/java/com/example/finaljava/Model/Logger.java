package com.example.finaljava.Model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for logging messages with timestamps.
 */
public class Logger {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs an info message with a timestamp.
     *
     * @param message the message to log.
     */
    public static void info(String message) {
        System.out.println(formatMessage("Ticket", message));
    }

    /**
     * Logs an error message with a timestamp.
     *
     * @param message the error message to log.
     */
    public static void error(String message) {
        System.err.println(formatMessage("ERROR", message));
    }

    /**
     * Formats the message with a level and timestamp.
     *
     * @param level   the log level (e.g., INFO, ERROR).
     * @param message the message to format.
     * @return the formatted log message.
     */
    private static String formatMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        return String.format("[%s] [%s]: %s", timestamp, level, message);
    }
}

