package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(
            LeaderboardService leaderboardService) {

        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>>
    getMonthlyLeaderboard() {

        return ResponseEntity.ok(
                leaderboardService.getMonthlyLeaderboard()
        );
    }
}