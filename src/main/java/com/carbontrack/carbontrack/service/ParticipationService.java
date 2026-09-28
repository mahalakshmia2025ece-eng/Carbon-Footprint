package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.ActivityLog;
import com.carbontrack.carbontrack.entity.Challenge;
import com.carbontrack.carbontrack.entity.Participation;
import com.carbontrack.carbontrack.repository.ActivityLogRepository;
import com.carbontrack.carbontrack.repository.ChallengeRepository;
import com.carbontrack.carbontrack.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ActivityLogRepository activityLogRepository;
    private final ChallengeRepository challengeRepository;

    public ParticipationService(
            ParticipationRepository participationRepository,
            ActivityLogRepository activityLogRepository,
            ChallengeRepository challengeRepository) {

        this.participationRepository = participationRepository;
        this.activityLogRepository = activityLogRepository;
        this.challengeRepository = challengeRepository;
    }

    public Participation createParticipation(
            Participation participation) {

        if (participation.getProgressPercentage() < 0 ||
                participation.getProgressPercentage() > 100) {

            throw new RuntimeException(
                    "Progress percentage must be between 0 and 100");
        }

        if (participation.getStatus() == null ||
                participation.getStatus().isBlank()) {

            participation.setStatus("JOINED");
        }

        return participationRepository.save(participation);
    }

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Optional<Participation> getParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    public List<Participation> getParticipationsByHouseholdId(
            Long householdId) {

        return participationRepository.findByHouseholdId(householdId);
    }

    public List<Participation> getParticipationsByChallengeId(
            Long challengeId) {

        return participationRepository.findByChallengeId(challengeId);
    }

    public Participation updateParticipation(
            Long id,
            Participation updatedParticipation) {

        Participation existingParticipation =
                participationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found"));

        if (updatedParticipation.getProgressPercentage() < 0 ||
                updatedParticipation.getProgressPercentage() > 100) {

            throw new RuntimeException(
                    "Progress percentage must be between 0 and 100");
        }

        existingParticipation.setHouseholdId(
                updatedParticipation.getHouseholdId());

        existingParticipation.setChallengeId(
                updatedParticipation.getChallengeId());

        existingParticipation.setProgressPercentage(
                updatedParticipation.getProgressPercentage());

        existingParticipation.setStatus(
                updatedParticipation.getStatus());

        return participationRepository.save(existingParticipation);
    }

    public void deleteParticipation(Long id) {

        if (!participationRepository.existsById(id)) {
            throw new RuntimeException("Participation not found");
        }

        participationRepository.deleteById(id);
    }

    public Participation trackProgress(Long participationId) {

        Participation participation =
                participationRepository.findById(participationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found"));

        Challenge challenge =
                challengeRepository.findById(
                                participation.getChallengeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Challenge not found"));

        List<ActivityLog> activities =
                activityLogRepository.findByHouseholdId(
                        participation.getHouseholdId());

        LocalDate today = LocalDate.now();

        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        int previousMonth = currentMonth - 1;
        int previousYear = currentYear;

        if (previousMonth == 0) {
            previousMonth = 12;
            previousYear--;
        }

        double currentFootprint = 0;
        double previousFootprint = 0;

        for (ActivityLog activity : activities) {

            LocalDate date =
                    LocalDate.parse(activity.getActivityDate());

            double emission =
                    calculateEmission(activity);

            if (date.getMonthValue() == currentMonth &&
                    date.getYear() == currentYear) {

                currentFootprint += emission;
            }

            if (date.getMonthValue() == previousMonth &&
                    date.getYear() == previousYear) {

                previousFootprint += emission;
            }
        }

        if (previousFootprint <= 0) {

            throw new RuntimeException(
                    "Previous month footprint is required to track progress");
        }

        double reductionPercentage =
                ((previousFootprint - currentFootprint)
                        / previousFootprint) * 100;

        double progress =
                (reductionPercentage /
                        challenge.getTargetReduction()) * 100;

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 100) {
            progress = 100;
        }

        participation.setProgressPercentage(
                Math.round(progress * 100.0) / 100.0);

        if (progress >= 100) {
            participation.setStatus("COMPLETED");
        } else {
            participation.setStatus("IN_PROGRESS");
        }

        return participationRepository.save(participation);
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
                        "Invalid activity type");
        }

        return activity.getQuantity() * factor;
    }
}