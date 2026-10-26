package org.example.controller;

import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.repository.BookRepository;
import org.example.repository.UserRepository;
import org.example.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ApiController(ApiService apiService, BookRepository bookRepository, UserRepository userRepository) {
        this.apiService = apiService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "Гость") String name) {
        return apiService.getGreeting(name);
    }

    @GetMapping("/reverse")
    public String reverse(@RequestParam String text) {
        return apiService.processText(text);
    }

    @GetMapping("/library/add")
    public String addBook(
            @RequestParam(defaultValue = "Неизвестная книга") String title,
            @RequestParam(defaultValue = "Неизвестный автор") String authorName) {

        Author author = new Author(authorName);
        Book book = new Book(title);

        book.getAuthors().add(author);
        bookRepository.save(book);

        return "Книга '" + book.getTitle() + "' автора " + authorName + " успешно добавлена!";
    }

    @GetMapping("/library/all")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/library/issue")
    public String issueBook(@RequestParam String userName, @RequestParam String bookTitle) {
        User user = new User(userName);
        Book book = new Book(bookTitle);

        user.getBooks().add(book);
        userRepository.save(user);

        return "Книга '" + bookTitle + "' успешно выдана пользователю " + userName;
    }

    @GetMapping("/library/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query);
    }
}