package com.example.finaljava.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    public void saveConfiguration(String jsonData) {
        try (FileWriter writeData = new FileWriter("Configuration.json", true)) { // 'true' enables append mode
            writeData.write(jsonData + System.lineSeparator()); // Add a newline after each entry
            System.out.println("Configuration saved to Configuration.json");
        } catch (IOException e) {
            System.err.println("An error occurred while saving the configuration: " + e.getMessage());
        }
    }

    public String loadConfiguration() {
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("Configuration.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line).append(System.lineSeparator());
            }
            System.out.println("Configuration loaded from Configuration.json");
        } catch (IOException e) {
            System.err.println("An error occurred while loading the configuration: " + e.getMessage());
        }
        return jsonData.toString();
    }
}
