package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.Participation;
import com.carbontrack.carbontrack.service.ParticipationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@CrossOrigin
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(
            ParticipationService participationService) {

        this.participationService = participationService;
    }

    // POST - Join Challenge
    @PostMapping
    public ResponseEntity<Participation> createParticipation(
            @RequestBody Participation participation) {

        return ResponseEntity.ok(
                participationService.createParticipation(
                        participation
                )
        );
    }

    // GET - Get All Participations
    @GetMapping
    public ResponseEntity<List<Participation>> getAllParticipations() {

        return ResponseEntity.ok(
                participationService.getAllParticipations()
        );
    }

    // GET - Get Participation By ID
    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(
            @PathVariable Long id) {

        return participationService.getParticipationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Get Participations By Household
    @GetMapping("/household/{householdId}")
    public ResponseEntity<List<Participation>>
    getParticipationsByHouseholdId(
            @PathVariable Long householdId) {

        return ResponseEntity.ok(
                participationService
                        .getParticipationsByHouseholdId(
                                householdId
                        )
        );
    }

    // GET - Get Participations By Challenge
    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<Participation>>
    getParticipationsByChallengeId(
            @PathVariable Long challengeId) {

        return ResponseEntity.ok(
                participationService
                        .getParticipationsByChallengeId(
                                challengeId
                        )
        );
    }

    // PUT - Update Participation
    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(
            @PathVariable Long id,
            @RequestBody Participation participation) {

        return ResponseEntity.ok(
                participationService.updateParticipation(
                        id,
                        participation
                )
        );
    }

    // DELETE - Leave Challenge
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipation(
            @PathVariable Long id) {

        participationService.deleteParticipation(id);

        return ResponseEntity.ok(
                "Participation deleted successfully"
        );
    }
}