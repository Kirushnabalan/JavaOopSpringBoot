package com.example.finaljava.Model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String message) {
        System.out.println(formatMessage("Ticket", message));
    }


    public static void error(String message) {
        System.err.println(formatMessage("ERROR", message));
    }

    private static String formatMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        return String.format("[%s] [%s]: %s", timestamp, level, message);
    }
}

