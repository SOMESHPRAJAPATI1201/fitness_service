package com.fitness.aiservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Document(collection = "recommendations")
@Data
@Builder
public class Recommendations {

    @Id
    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private String recommendation;
    private List<String> Improvements;
    private List<String> Suggestions;
    private List<String> Safety;
    @CreatedDate
    private LocalDateTime createdAt;
}
