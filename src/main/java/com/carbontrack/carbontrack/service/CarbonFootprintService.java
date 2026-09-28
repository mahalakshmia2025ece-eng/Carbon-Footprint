package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.ActivityLog;
import com.carbontrack.carbontrack.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarbonFootprintService {

    private final ActivityLogRepository activityLogRepository;

    public CarbonFootprintService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public double calculateActivityEmission(ActivityLog activity) {

        double emissionFactor;

        switch (activity.getActivityType().toUpperCase()) {

            case "ELECTRICITY":
                emissionFactor = 0.82;
                break;

            case "CAR":
                emissionFactor = 0.21;
                break;

            case "BUS":
                emissionFactor = 0.08;
                break;

            case "BIKE":
                emissionFactor = 0.10;
                break;

            case "WALKING":
                emissionFactor = 0.0;
                break;

            case "WASTE":
                emissionFactor = 0.50;
                break;

            default:
                throw new RuntimeException(
                        "Invalid activity type"
                );
        }

        return activity.getQuantity() * emissionFactor;
    }

    public double calculateHouseholdFootprint(Long householdId) {

        List<ActivityLog> activities =
                activityLogRepository.findByHouseholdId(householdId);

        double totalEmission = 0;

        for (ActivityLog activity : activities) {
            totalEmission += calculateActivityEmission(activity);
        }

        return totalEmission;
    }
}