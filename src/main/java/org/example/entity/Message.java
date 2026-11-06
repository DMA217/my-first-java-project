package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderName;
    private String text;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(String senderName, String text) {
        this.senderName = senderName;
        this.text = text;
        this.timestamp = LocalDateTime.now();
    }
}