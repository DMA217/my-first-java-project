package org.example.service;

import org.example.entity.Message;
import org.example.entity.MessageDto;
import org.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    public ChatService(MessageRepository messageRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
    }

    public void sendMessageToRemote(String targetIp, String senderName, String text) {
        String url = "http://" + targetIp + ":8080/api/chat/receive";
        MessageDto dto = new MessageDto();
        dto.setSenderName(senderName);
        dto.setText(text);

        restTemplate.postForObject(url, dto, String.class);
    }

    public void receiveMessage(MessageDto dto) {
        Message message = new Message(dto.getSenderName(), dto.getText());
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesFrom(String senderName) {
        return messageRepository.findBySenderName(senderName);
    }
}