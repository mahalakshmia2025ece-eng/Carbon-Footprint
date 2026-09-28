package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.Participation;
import com.carbontrack.carbontrack.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationService(
            ParticipationRepository participationRepository) {

        this.participationRepository = participationRepository;
    }

    // CREATE - Join Challenge
    public Participation createParticipation(
            Participation participation) {

        if (participation.getProgressPercentage() < 0 ||
                participation.getProgressPercentage() > 100) {

            throw new RuntimeException(
                    "Progress percentage must be between 0 and 100"
            );
        }

        if (participation.getStatus() == null ||
                participation.getStatus().isBlank()) {

            participation.setStatus("JOINED");
        }

        return participationRepository.save(participation);
    }

    // READ - Get all participations
    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    // READ - Get one participation
    public Optional<Participation> getParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    // READ - Get participations by household
    public List<Participation> getParticipationsByHouseholdId(
            Long householdId) {

        return participationRepository.findByHouseholdId(householdId);
    }

    // READ - Get participations by challenge
    public List<Participation> getParticipationsByChallengeId(
            Long challengeId) {

        return participationRepository.findByChallengeId(challengeId);
    }

    // UPDATE - Track progress
    public Participation updateParticipation(
            Long id,
            Participation updatedParticipation) {

        Participation existingParticipation =
                participationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found"
                                ));

        if (updatedParticipation.getProgressPercentage() < 0 ||
                updatedParticipation.getProgressPercentage() > 100) {

            throw new RuntimeException(
                    "Progress percentage must be between 0 and 100"
            );
        }

        existingParticipation.setHouseholdId(
                updatedParticipation.getHouseholdId()
        );

        existingParticipation.setChallengeId(
                updatedParticipation.getChallengeId()
        );

        existingParticipation.setProgressPercentage(
                updatedParticipation.getProgressPercentage()
        );

        existingParticipation.setStatus(
                updatedParticipation.getStatus()
        );

        return participationRepository.save(existingParticipation);
    }

    // DELETE - Leave Challenge
    public void deleteParticipation(Long id) {

        if (!participationRepository.existsById(id)) {
            throw new RuntimeException(
                    "Participation not found"
            );
        }

        participationRepository.deleteById(id);
    }
}