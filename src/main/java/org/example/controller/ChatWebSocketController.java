package org.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Map<String, String> sendMessage(Map<String, String> message) {
        message.put("timestamp", LocalDateTime.now().toString());
        return message;
    }
}