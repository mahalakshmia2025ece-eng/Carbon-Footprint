package com.carbontrack.carbontrack.repository;

import com.carbontrack.carbontrack.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

    List<Household> findByUserId(Long userId);
}