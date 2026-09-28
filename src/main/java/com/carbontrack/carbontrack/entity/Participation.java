package com.carbontrack.carbontrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "participations")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long householdId;

    @Column(nullable = false)
    private Long challengeId;

    @Column(nullable = false)
    private double progressPercentage;

    @Column(nullable = false)
    private String status;

    public Participation() {
    }

    public Participation(Long householdId, Long challengeId,
                         double progressPercentage, String status) {
        this.householdId = householdId;
        this.challengeId = challengeId;
        this.progressPercentage = progressPercentage;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}