package org.example;

import jakarta.persistence.*; // Очень важно: именно jakarta, а не javax!
import java.time.LocalDateTime;

@Entity
@Table(name = "request_logs")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String inputParam;
    private LocalDateTime timestamp;

    public RequestLog() {
    }

    public RequestLog(String endpoint, String inputParam) {
        this.endpoint = endpoint;
        this.inputParam = inputParam;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры и сеттеры (обязательно сгенерируйте их через Alt+Insert / Cmd+N)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getInputParam() { return inputParam; }
    public void setInputParam(String inputParam) { this.inputParam = inputParam; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}