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


    // ==============================
    // CREATE HOUSEHOLD
    // ==============================

    public Household createHousehold(Household household) {

        if (household.getMembers() <= 0) {

            throw new RuntimeException(
                    "Number of members must be greater than zero"
            );
        }


        // Check whether this user already has a household

        List<Household> existingHouseholds =
                householdRepository.findByUserId(
                        household.getUserId()
                );


        if (!existingHouseholds.isEmpty()) {

            throw new RuntimeException(
                    "This user already has a household. Please update the existing household."
            );
        }


        return householdRepository.save(household);
    }


    // ==============================
    // GET ALL HOUSEHOLDS
    // ==============================

    public List<Household> getAllHouseholds() {

        return householdRepository.findAll();

    }


    // ==============================
    // GET HOUSEHOLD BY ID
    // ==============================

    public Optional<Household> getHouseholdById(Long id) {

        return householdRepository.findById(id);

    }


    // ==============================
    // GET HOUSEHOLDS BY USER ID
    // ==============================

    public List<Household> getHouseholdsByUserId(Long userId) {

        return householdRepository.findByUserId(userId);

    }


    // ==============================
    // UPDATE HOUSEHOLD
    // ==============================

    public Household updateHousehold(
            Long id,
            Household updatedHousehold) {


        Household existingHousehold =
                householdRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Household not found"
                                )
                        );


        if (updatedHousehold.getMembers() <= 0) {

            throw new RuntimeException(
                    "Number of members must be greater than zero"
            );

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


        // Keep the original user ID

        existingHousehold.setUserId(
                existingHousehold.getUserId()
        );


        return householdRepository.save(
                existingHousehold
        );

    }


    // ==============================
    // DELETE HOUSEHOLD
    // ==============================

    public void deleteHousehold(Long id) {

        if (!householdRepository.existsById(id)) {

            throw new RuntimeException(
                    "Household not found"
            );

        }


        householdRepository.deleteById(id);

    }

}