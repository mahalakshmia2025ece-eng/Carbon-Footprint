package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.Challenge;
import com.carbontrack.carbontrack.repository.ChallengeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    // CREATE
    public Challenge createChallenge(Challenge challenge) {

        if (challenge.getTargetReduction() <= 0 ||
                challenge.getTargetReduction() > 100) {

            throw new RuntimeException(
                    "Target reduction must be between 1 and 100 percent"
            );
        }

        return challengeRepository.save(challenge);
    }

    // READ - All challenges
    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    // READ - One challenge
    public Optional<Challenge> getChallengeById(Long id) {
        return challengeRepository.findById(id);
    }

    // UPDATE
    public Challenge updateChallenge(
            Long id,
            Challenge updatedChallenge) {

        Challenge existingChallenge =
                challengeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Challenge not found"
                                ));

        if (updatedChallenge.getTargetReduction() <= 0 ||
                updatedChallenge.getTargetReduction() > 100) {

            throw new RuntimeException(
                    "Target reduction must be between 1 and 100 percent"
            );
        }

        existingChallenge.setChallengeName(
                updatedChallenge.getChallengeName()
        );

        existingChallenge.setDescription(
                updatedChallenge.getDescription()
        );

        existingChallenge.setTargetReduction(
                updatedChallenge.getTargetReduction()
        );

        existingChallenge.setStartDate(
                updatedChallenge.getStartDate()
        );

        existingChallenge.setEndDate(
                updatedChallenge.getEndDate()
        );

        return challengeRepository.save(existingChallenge);
    }

    // DELETE
    public void deleteChallenge(Long id) {

        if (!challengeRepository.existsById(id)) {
            throw new RuntimeException("Challenge not found");
        }

        challengeRepository.deleteById(id);
    }
}

