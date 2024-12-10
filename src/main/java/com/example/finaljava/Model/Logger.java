package com.example.finaljava.Model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Logger {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";

    private static final String LOG_FILE_PATH = "logs.json";
    private static final Gson gson = new Gson();
    private static final List<JsonObject> logList = new CopyOnWriteArrayList<>();

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
        JsonObject logEntry = new JsonObject();
        logEntry.addProperty("timestamp", timestamp);
        logEntry.addProperty("level", level);
        logEntry.addProperty("message", message);

        logList.add(logEntry);
        saveLogs();
    }

    private static void saveLogs() {
        try (FileWriter file = new FileWriter(LOG_FILE_PATH)) {
            gson.toJson(logList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadLogs() {
        try (FileReader reader = new FileReader(LOG_FILE_PATH)) {
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    logList.add(jsonArray.get(i).getAsJsonObject());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
