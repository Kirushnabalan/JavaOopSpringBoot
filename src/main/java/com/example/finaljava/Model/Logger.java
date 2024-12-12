package com.example.finaljava.Model;

// Import statements for file handling, date-time formatting, concurrent collections, and JSON processing.
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Logger {

    // Formatter for generating timestamps in a consistent format.
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ANSI escape codes for colored console output.
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";

    // Path to the JSON file where logs will be stored.
    private static final String LOG_FILE_PATH = "logs.json";

    // Gson instance for serializing and deserializing log data.
    private static final Gson gson = new Gson();

    // Thread-safe list to store log entries in memory.
    private static final List<LogEntry> logList = new CopyOnWriteArrayList<>();

    // Static block to load existing logs from the file at application startup.
    static {
        loadLogs();
    }

    public static void info(String message) {
        log("INFO", message);
        System.out.println(CYAN + formatMessage("INFO", message) + RESET);
    }

    public static void error(String message) {
        log("ERROR", message);
        System.err.println(RED + formatMessage("ERROR", message) + RESET);
    }


    public static void warn(String message) {
        log("WARNING", message);
        System.out.println(YELLOW + formatMessage("WARNING", message) + RESET);
    }


    public static void newMessage(String message) {
        log("MESSAGE", message);
        System.out.println(GREEN + formatMessage("MESSAGE", message) + RESET);
    }


    private static String formatMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        return String.format("[%s] [%s]: %s", timestamp, level, message);
    }


    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        LogEntry logEntry = new LogEntry(timestamp, level, message);

        // Add the log entry to the list and save it to the file.
        logList.add(logEntry);
        saveLogs();
    }


    private static void saveLogs() {
        try (FileWriter file = new FileWriter(LOG_FILE_PATH)) {
            gson.toJson(logList, file);
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if there's an error.
        }
    }
    private static void loadLogs() {
        try (FileReader reader = new FileReader(LOG_FILE_PATH)) {
            List<LogEntry> loadedLogs = gson.fromJson(reader, new TypeToken<List<LogEntry>>() {}.getType());
            if (loadedLogs != null) {
                logList.addAll(loadedLogs); // Add previously saved logs to the current list.
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if there's an error.
        }
    }

    // Inner class to represent a log entry.
    private static class LogEntry {
        private final String timestamp; // The time the log was created.
        private final String level;     // The severity level of the log.
        private final String message;   // The message content.


        public LogEntry(String timestamp, String level, String message) {
            this.timestamp = timestamp;
            this.level = level;
            this.message = message;
        }

        // Getters for the log entry fields.
        public String getTimestamp() {
            return timestamp;
        }

        public String getLevel() {
            return level;
        }

        public String getMessage() {
            return message;
        }
    }
}
