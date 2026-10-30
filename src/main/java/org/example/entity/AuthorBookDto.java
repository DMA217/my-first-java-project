package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorBookDto {
    private String title;
    private Integer pages;
}