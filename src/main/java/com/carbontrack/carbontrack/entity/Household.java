package com.carbontrack.carbontrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "households")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String householdName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int members;

    @Column(nullable = false)
    private Long userId;

    public Household() {
    }

    public Household(String householdName, String address, int members, Long userId) {
        this.householdName = householdName;
        this.address = address;
        this.members = members;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}