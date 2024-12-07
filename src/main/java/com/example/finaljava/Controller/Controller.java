package com.example.finaljava.Controller;

import com.example.finaljava.Entity.DetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/simulation")
@CrossOrigin
public class Controller {

    @Autowired
    private com.example.finaljava.Service.SimulationService simulationService;

    @PostMapping("/start")
    public String startSimulation(@RequestBody DetailsEntity config) {
        simulationService.startSimulation(config);
        return "Simulation started!";
    }

    @PostMapping("/save")
    public String saveConfiguration(@RequestBody DetailsEntity config) {
        simulationService.saveConfiguration(config);
        return "save Details!";
    }

    @GetMapping("/show")
    public List<DetailsEntity> getAllConfigs() {
        // Fetch all configurations from the service and return them
        return simulationService.getAllConfigs();
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped!";
    }
    @GetMapping("/show/{id}")
    public DetailsEntity getConfigById(@PathVariable Long id) {
        // Fetch configuration by ID from the service
        Optional<DetailsEntity> config = simulationService.loadConfiguration(id);
        return config.orElse(null);
    }
}
