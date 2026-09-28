package com.carbontrack.carbontrack.repository;

import com.carbontrack.carbontrack.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository
        extends JpaRepository<Participation, Long> {

    List<Participation> findByHouseholdId(Long householdId);

    List<Participation> findByChallengeId(Long challengeId);
}