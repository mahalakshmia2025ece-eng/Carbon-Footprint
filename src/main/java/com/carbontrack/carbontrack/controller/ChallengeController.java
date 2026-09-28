package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.Challenge;
import com.carbontrack.carbontrack.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    // POST - Create Challenge
    @PostMapping
    public ResponseEntity<Challenge> createChallenge(
            @RequestBody Challenge challenge) {

        return ResponseEntity.ok(
                challengeService.createChallenge(challenge)
        );
    }

    // GET - Get All Challenges
    @GetMapping
    public ResponseEntity<List<Challenge>> getAllChallenges() {

        return ResponseEntity.ok(
                challengeService.getAllChallenges()
        );
    }

    // GET - Get Challenge By ID
    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallengeById(
            @PathVariable Long id) {

        return challengeService.getChallengeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT - Update Challenge
    @PutMapping("/{id}")
    public ResponseEntity<Challenge> updateChallenge(
            @PathVariable Long id,
            @RequestBody Challenge challenge) {

        return ResponseEntity.ok(
                challengeService.updateChallenge(id, challenge)
        );
    }

    // DELETE - Delete Challenge
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChallenge(
            @PathVariable Long id) {

        challengeService.deleteChallenge(id);

        return ResponseEntity.ok(
                "Challenge deleted successfully"
        );
    }
}