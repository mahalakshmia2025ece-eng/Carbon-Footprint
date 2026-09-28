package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.ActivityLog;
import com.carbontrack.carbontrack.service.ActivityLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    // POST - Create Activity
    @PostMapping
    public ResponseEntity<ActivityLog> createActivity(
            @RequestBody ActivityLog activityLog) {

        return ResponseEntity.ok(
                activityLogService.createActivity(activityLog)
        );
    }

    // GET - Get All Activities
    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllActivities() {

        return ResponseEntity.ok(
                activityLogService.getAllActivities()
        );
    }

    // GET - Get Activity By ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityById(
            @PathVariable Long id) {

        return activityLogService.getActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Get Activities By Household ID
    @GetMapping("/household/{householdId}")
    public ResponseEntity<List<ActivityLog>> getActivitiesByHouseholdId(
            @PathVariable Long householdId) {

        return ResponseEntity.ok(
                activityLogService.getActivitiesByHouseholdId(householdId)
        );
    }

    // PUT - Update Activity
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivity(
            @PathVariable Long id,
            @RequestBody ActivityLog activityLog) {

        return ResponseEntity.ok(
                activityLogService.updateActivity(id, activityLog)
        );
    }

    // DELETE - Delete Activity
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivity(
            @PathVariable Long id) {

        activityLogService.deleteActivity(id);

        return ResponseEntity.ok(
                "Activity deleted successfully"
        );
    }
}