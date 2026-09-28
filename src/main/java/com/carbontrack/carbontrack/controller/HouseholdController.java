package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.Household;
import com.carbontrack.carbontrack.service.HouseholdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
@CrossOrigin
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    // POST - Create Household
    @PostMapping
    public ResponseEntity<Household> createHousehold(
            @RequestBody Household household) {

        return ResponseEntity.ok(
                householdService.createHousehold(household)
        );
    }

    // GET - Get All Households
    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {

        return ResponseEntity.ok(
                householdService.getAllHouseholds()
        );
    }

    // GET - Get Household By ID
    @GetMapping("/{id}")
    public ResponseEntity<Household> getHouseholdById(
            @PathVariable Long id) {

        return householdService.getHouseholdById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Get Households By User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Household>> getHouseholdsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                householdService.getHouseholdsByUserId(userId)
        );
    }

    // PUT - Update Household
    @PutMapping("/{id}")
    public ResponseEntity<Household> updateHousehold(
            @PathVariable Long id,
            @RequestBody Household household) {

        return ResponseEntity.ok(
                householdService.updateHousehold(id, household)
        );
    }

    // DELETE - Delete Household
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHousehold(
            @PathVariable Long id) {

        householdService.deleteHousehold(id);

        return ResponseEntity.ok(
                "Household deleted successfully"
        );
    }
}