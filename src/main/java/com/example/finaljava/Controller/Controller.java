package com.example.finaljava.Controller;

import com.example.finaljava.Model.Configuration;
import com.example.finaljava.Service.SimulationService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


    // Fetch all configurations saved in the system
    @GetMapping("/show")
    public List<Configuration> getAllConfigs() {
        return simulationService.loadAllConfigurations();  // Fetch configurations from JSON file
    }

    @GetMapping("/logs")
    public List<Map<String, Object>> getLogs() {
        return simulationService.getLogs();
    }

}
