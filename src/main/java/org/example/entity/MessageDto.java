package org.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDto {
    private String senderName;
    private String text;
    private LocalDateTime timestamp;
}