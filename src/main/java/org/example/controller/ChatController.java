package org.example.controller;

import org.example.entity.Message;
import org.example.entity.MessageDto;
import org.example.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/send")
    public String sendMessage(
            @RequestParam String ip,
            @RequestParam String senderName,
            @RequestParam String text) {
        chatService.sendMessageToRemote(ip, senderName, text);
        return "Сообщение отправлено на " + ip;
    }

    @PostMapping("/receive")
    public String receiveMessage(@RequestBody MessageDto dto) {
        chatService.receiveMessage(dto);
        return "Сообщение принято от " + dto.getSenderName();
    }

    @GetMapping("/all")
    public List<Message> readAllMessages() {
        return chatService.getAllMessages();
    }

    @GetMapping("/from")
    public List<Message> readAllMessagesFrom(@RequestParam String senderName) {
        return chatService.getMessagesFrom(senderName);
    }
}