package com.fitness.aiservice.service;


import com.fitness.aiservice.config.RabbitMqConfig;
import com.fitness.aiservice.dto.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    @RabbitListener(queues = "activity.queue")
    public void processActivityMessage(Activity activity) {
        log.info("Message received: {}", activity.getId());
    }
}
