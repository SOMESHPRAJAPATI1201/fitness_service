package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityServices;

    @PostMapping("/add")
    public ResponseEntity<ActivityResponse> addActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityServices.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String UserId){
        return ResponseEntity.ok(activityServices.getUserActivities(UserId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String userId){
        return ResponseEntity.ok(activityServices.getActivityById(userId));
    }

}
