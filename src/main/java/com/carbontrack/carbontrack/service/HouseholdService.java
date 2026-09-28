package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.Household;
import com.carbontrack.carbontrack.repository.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseholdService {

    private final HouseholdRepository householdRepository;

    public HouseholdService(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    // CREATE
    public Household createHousehold(Household household) {

        if (household.getMembers() <= 0) {
            throw new RuntimeException("Number of members must be greater than zero");
        }

        return householdRepository.save(household);
    }

    // READ - All households
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    // READ - One household
    public Optional<Household> getHouseholdById(Long id) {
        return householdRepository.findById(id);
    }

    // READ - Households by User
    public List<Household> getHouseholdsByUserId(Long userId) {
        return householdRepository.findByUserId(userId);
    }

    // UPDATE
    public Household updateHousehold(Long id, Household updatedHousehold) {

        Household existingHousehold = householdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        if (updatedHousehold.getMembers() <= 0) {
            throw new RuntimeException("Number of members must be greater than zero");
        }

        existingHousehold.setHouseholdName(
                updatedHousehold.getHouseholdName()
        );

        existingHousehold.setAddress(
                updatedHousehold.getAddress()
        );

        existingHousehold.setMembers(
                updatedHousehold.getMembers()
        );

        existingHousehold.setUserId(
                updatedHousehold.getUserId()
        );

        return householdRepository.save(existingHousehold);
    }

    // DELETE
    public void deleteHousehold(Long id) {

        if (!householdRepository.existsById(id)) {
            throw new RuntimeException("Household not found");
        }

        householdRepository.deleteById(id);
    }
}