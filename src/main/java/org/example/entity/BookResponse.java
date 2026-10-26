package org.example.entity;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private List<String> authorNames;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorNames = book.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }
}