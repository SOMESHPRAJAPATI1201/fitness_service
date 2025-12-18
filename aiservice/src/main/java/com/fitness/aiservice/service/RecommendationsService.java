package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendations;
import com.fitness.aiservice.repository.RecommendationsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationsService {

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    public List<Recommendations> getRecommendationsByUserId(String userId) {
        return recommendationsRepository.findByUserId(userId);
    }

    public Recommendations getRecommendationsByActivityId(String activityId) {
        return recommendationsRepository.findByActivityId(activityId);
    }
}
