package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Recommendations;
import com.fitness.aiservice.service.RecommendationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationsService recommendationsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendations>> getRecommendationsByUserId(@PathVariable  String userId) {
        return ResponseEntity.ok(recommendationsService.getRecommendationsByUserId(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendations> getRecommendationsByActivityId(@PathVariable  String activityId) {
        return ResponseEntity.ok(recommendationsService.getRecommendationsByActivityId(activityId));
    }
}
