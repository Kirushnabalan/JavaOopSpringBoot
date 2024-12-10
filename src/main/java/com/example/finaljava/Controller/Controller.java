package com.example.finaljava.Controller;

import com.example.finaljava.Model.Configuration;
import com.example.finaljava.Service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/simulation")
@CrossOrigin
public class Controller {

    @Autowired
    private SimulationService simulationService;

    // Start the simulation using the provided configuration
    @PostMapping("/start")
    public String startSimulation(@RequestBody Configuration config) {
        simulationService.startSimulation(config);
        return "Simulation started!";
    }

    // Save the configuration
    @PostMapping("/save")
    public String saveConfiguration(@RequestBody Configuration config) throws IOException {
        simulationService.saveConfiguration(config);
        return "Details saved!";
    }

    // Stop the simulation
    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped!";
    }

    // End the simulation (same as stopping it in this case)
    @PostMapping("/end")
    public String endSimulation() {
        simulationService.stopSimulation();  // Reuse stopSimulation for ending
        return "Simulation ended and resources cleaned!";
    }

    // Fetch all configurations saved in the system
    @GetMapping("/show")
    public List<Configuration> getAllConfigs() {
        return simulationService.loadAllConfigurations();  // Fetch configurations from JSON file
    }

    // Fetch a specific configuration by ID
    @GetMapping("/show/{id}")
    public Configuration getConfigById(@PathVariable int id) {
        Configuration config = simulationService.loadConfigurationById(id);
        if (config != null) {
            return config;
        } else {
            throw new RuntimeException("Configuration with ID " + id + " not found.");
        }
    }
}
