package com.example.finaljava.LogsFileforThredSave;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogsSave {
    private static final List<String> events = new ArrayList<>();

    public static void log(String event) {
        synchronized (events) {
            events.add(event);
        }
    }

    public static void writeToJsonFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
