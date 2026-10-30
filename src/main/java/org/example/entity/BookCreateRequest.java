package org.example.entity;

import lombok.Data;
import java.util.List;

@Data
public class BookCreateRequest {
    private String title;
    private Integer pages;
    private List<AuthorDto> authors;

    @Data
    public static class AuthorDto {
        private String name;
    }
}