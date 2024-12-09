package com.example.finaljava.Controller;

import com.example.finaljava.Entity.DetailsEntity;
import com.example.finaljava.Model.Configuration;
import com.example.finaljava.Service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/simulation")
@CrossOrigin
public class Controller {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/start")
    public String startSimulation(@RequestBody Configuration config) {
        simulationService.startSimulation(config);
        return "Simulation started!";
    }

    @PostMapping("/save")
    public String saveConfiguration(@RequestBody Configuration config) {
        simulationService.saveConfiguration(config);
        return "Details saved!";
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped!";
    }

    // Assuming you meant to stop the simulation in 'stopSimulation' rather than calling an undefined 'endSimulation'.
    @PostMapping("/end")
    public String endSimulation() {
        simulationService.stopSimulation();  // Using stopSimulation() instead of endSimulation()
        return "Simulation ended and resources cleaned!";
    }

    // Fetch all configurations saved in the system
    @GetMapping("/show")
    public List<Configuration> getAllConfigs() {
        return simulationService.getAllConfigs(); // Returns list of configurations
    }
    // Fetch configuration details by ID
//    @GetMapping("/show/{id}")
//    public DetailsEntity getConfigById(@PathVariable int id) {
//        Optional<DetailsEntity> config = simulationService.loadConfigurationById(id);
//        return config.orElse(null);  // If the configuration is not found, return null
//    }
}
