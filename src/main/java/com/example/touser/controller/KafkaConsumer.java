package com.example.touser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "test")
    public void consume(String message) throws JsonProcessingException {
        Map<String, String> messageMap = new ObjectMapper().readValue(message, Map.class);
        String body = messageMap.get("body");
        String userId = messageMap.get("user-id");
        messagingTemplate.convertAndSendToUser(userId, "/topic/messages", body);
    }
}

