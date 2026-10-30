package org.example.controller;

import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.exception.InvalidBookDataException;
import org.example.repository.BookRepository;
import org.example.repository.UserRepository;
import org.example.service.ApiService;
import org.springframework.web.bind.annotation.*;
import org.example.entity.BookCreateRequest;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/library/add")
    public String addBookPost(@RequestBody BookCreateRequest request) {
        String title = request.getTitle();
        Integer pages = request.getPages() != null ? request.getPages() : 0;

        String authorName = "Неизвестный автор";
        if (request.getAuthors() != null && !request.getAuthors().isEmpty()) {
            authorName = request.getAuthors().get(0).getName();
        }

        if (title == null || title.trim().isEmpty() || authorName.trim().isEmpty()) {
            throw new InvalidBookDataException("Название книги и автор обязательны!");
        }

        Author author = new Author(authorName);
        Book book = new Book(title);
        book.setPages(pages);

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
        User user = userRepository.findAll().stream()
                .filter(u -> u.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElseGet(() -> userRepository.save(new User(userName)));

        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
        Book book = foundBooks.isEmpty() ? bookRepository.save(new Book(bookTitle)) : foundBooks.get(0);

        return "Книга '" + book.getTitle() + "' выдана пользователю " + userName;
    }

    @GetMapping("/library/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query);
    }

    @DeleteMapping("/library/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            throw new InvalidBookDataException("Книга с ID " + id + " не найдена!");
        }
        bookRepository.deleteById(id);
        return "Книга с ID " + id + " успешно удалена!";
    }
}