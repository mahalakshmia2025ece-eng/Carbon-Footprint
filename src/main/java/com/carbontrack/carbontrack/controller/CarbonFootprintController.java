package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.service.CarbonFootprintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carbon")
@CrossOrigin
public class CarbonFootprintController {

    private final CarbonFootprintService carbonFootprintService;

    public CarbonFootprintController(
            CarbonFootprintService carbonFootprintService) {

        this.carbonFootprintService = carbonFootprintService;
    }

    // GET - Calculate household carbon footprint
    @GetMapping("/household/{householdId}")
    public ResponseEntity<Double> calculateHouseholdFootprint(
            @PathVariable Long householdId) {

        double totalEmission =
                carbonFootprintService.calculateHouseholdFootprint(
                        householdId
                );

        return ResponseEntity.ok(totalEmission);
    }
}