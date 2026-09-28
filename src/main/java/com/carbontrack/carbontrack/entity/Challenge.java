package com.carbontrack.carbontrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String challengeName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double targetReduction;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    public Challenge() {
    }

    public Challenge(String challengeName, String description,
                     double targetReduction, String startDate,
                     String endDate) {
        this.challengeName = challengeName;
        this.description = description;
        this.targetReduction = targetReduction;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetReduction() {
        return targetReduction;
    }

    public void setTargetReduction(double targetReduction) {
        this.targetReduction = targetReduction;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}