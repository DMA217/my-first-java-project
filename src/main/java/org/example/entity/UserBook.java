package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_book")
@Data
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    public UserBook() {}

    public UserBook(User user, Book book) {
        this.user = user;
        this.book = book;
        this.issueDate = LocalDateTime.now();
    }
}