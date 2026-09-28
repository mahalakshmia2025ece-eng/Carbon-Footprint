package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.ActivityLog;
import com.carbontrack.carbontrack.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LeaderboardService {

    private final ActivityLogRepository activityLogRepository;

    public LeaderboardService(
            ActivityLogRepository activityLogRepository) {

        this.activityLogRepository = activityLogRepository;
    }

    public List<Map<String, Object>> getMonthlyLeaderboard() {

        List<ActivityLog> activities =
                activityLogRepository.findAll();

        Map<Long, Double> currentMonth = new HashMap<>();
        Map<Long, Double> previousMonth = new HashMap<>();

        LocalDate today = LocalDate.now();

        int currentMonthNumber = today.getMonthValue();
        int currentYear = today.getYear();

        int previousMonthNumber = currentMonthNumber - 1;
        int previousYear = currentYear;

        if (previousMonthNumber == 0) {
            previousMonthNumber = 12;
            previousYear--;
        }

        for (ActivityLog activity : activities) {

            LocalDate date =
                    LocalDate.parse(activity.getActivityDate());

            double emission =
                    calculateEmission(activity);

            Long householdId =
                    activity.getHouseholdId();

            if (date.getMonthValue() == currentMonthNumber &&
                    date.getYear() == currentYear) {

                currentMonth.merge(
                        householdId,
                        emission,
                        Double::sum
                );
            }

            if (date.getMonthValue() == previousMonthNumber &&
                    date.getYear() == previousYear) {

                previousMonth.merge(
                        householdId,
                        emission,
                        Double::sum
                );
            }
        }

        List<Map<String, Object>> leaderboard =
                new ArrayList<>();

        for (Long householdId : previousMonth.keySet()) {

            double previousFootprint =
                    previousMonth.get(householdId);

            double currentFootprint =
                    currentMonth.getOrDefault(
                            householdId,
                            0.0
                    );

            if (previousFootprint <= 0) {
                continue;
            }

            double reduction =
                    ((previousFootprint - currentFootprint)
                            / previousFootprint) * 100;

            Map<String, Object> result =
                    new HashMap<>();

            result.put("householdId", householdId);
            result.put("previousMonthFootprint",
                    previousFootprint);
            result.put("currentMonthFootprint",
                    currentFootprint);
            result.put("reductionPercentage",
                    Math.round(reduction * 100.0) / 100.0);

            leaderboard.add(result);
        }

        leaderboard.sort((a, b) ->
                Double.compare(
                        (Double) b.get("reductionPercentage"),
                        (Double) a.get("reductionPercentage")
                )
        );

        return leaderboard;
    }

    private double calculateEmission(ActivityLog activity) {

        double factor;

        switch (activity.getActivityType().toUpperCase()) {

            case "ELECTRICITY":
                factor = 0.82;
                break;

            case "CAR":
                factor = 0.21;
                break;

            case "BUS":
                factor = 0.08;
                break;

            case "BIKE":
                factor = 0.10;
                break;

            case "WALKING":
                factor = 0.0;
                break;

            case "WASTE":
                factor = 0.50;
                break;

            default:
                throw new RuntimeException(
                        "Invalid activity type"
                );
        }

        return activity.getQuantity() * factor;
    }
}