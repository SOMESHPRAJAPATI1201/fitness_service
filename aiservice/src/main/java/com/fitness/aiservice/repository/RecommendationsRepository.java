package com.fitness.aiservice.repository;

import com.fitness.aiservice.model.Recommendations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationsRepository extends MongoRepository<Recommendations, String> {
    List<Recommendations> findByUserId(String userId);
    Recommendations findByActivityId(String activityId);
}
