package com.carbontrack.carbontrack.repository;

import com.carbontrack.carbontrack.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findByHouseholdId(Long householdId);
}