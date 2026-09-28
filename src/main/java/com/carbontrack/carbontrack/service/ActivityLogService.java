package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.ActivityLog;
import com.carbontrack.carbontrack.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    // CREATE
    public ActivityLog createActivity(ActivityLog activityLog) {

        if (activityLog.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        return activityLogRepository.save(activityLog);
    }

    // READ - All activities
    public List<ActivityLog> getAllActivities() {
        return activityLogRepository.findAll();
    }

    // READ - One activity
    public Optional<ActivityLog> getActivityById(Long id) {
        return activityLogRepository.findById(id);
    }

    // READ - Activities by household
    public List<ActivityLog> getActivitiesByHouseholdId(Long householdId) {
        return activityLogRepository.findByHouseholdId(householdId);
    }

    // UPDATE
    public ActivityLog updateActivity(Long id, ActivityLog updatedActivity) {

        ActivityLog existingActivity = activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        if (updatedActivity.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        existingActivity.setHouseholdId(
                updatedActivity.getHouseholdId()
        );

        existingActivity.setActivityType(
                updatedActivity.getActivityType()
        );

        existingActivity.setQuantity(
                updatedActivity.getQuantity()
        );

        existingActivity.setActivityDate(
                updatedActivity.getActivityDate()
        );

        return activityLogRepository.save(existingActivity);
    }

    // DELETE
    public void deleteActivity(Long id) {

        if (!activityLogRepository.existsById(id)) {
            throw new RuntimeException("Activity not found");
        }

        activityLogRepository.deleteById(id);
    }
}