package org.example.controller;

import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.BookResponse;
import org.example.entity.User;
import org.example.entity.UserBook;
import org.example.exception.InvalidBookDataException;
import org.example.repository.BookRepository;
import org.example.repository.UserBookRepository;
import org.example.repository.UserRepository;
import org.example.service.ApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;

    public ApiController(ApiService apiService,
                         BookRepository bookRepository,
                         UserRepository userRepository,
                         UserBookRepository userBookRepository) {
        this.apiService = apiService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
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
    public String addBookPost(@RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String authorName = payload.get("authorName");

        if (title == null || title.trim().isEmpty() || authorName == null || authorName.trim().isEmpty()) {
            throw new InvalidBookDataException("Название книги и автор обязательны!");
        }

        Author author = new Author(authorName);
        Book book = new Book(title);

        book.getAuthors().add(author);
        bookRepository.save(book);

        return "Книга '" + book.getTitle() + "' автора " + authorName + " успешно добавлена!";
    }

    @GetMapping("/library/all")
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/library/issue")
    public String issueBook(@RequestParam String userName, @RequestParam String bookTitle) {
        // 1. Ищем пользователя по имени, если нет - создаем нового
        User user = userRepository.findAll().stream()
                .filter(u -> u.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElseGet(() -> userRepository.save(new User(userName)));

        // 2. Ищем книгу по названию, если нет - создаем новую
        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
        Book book = foundBooks.isEmpty() ? bookRepository.save(new Book(bookTitle)) : foundBooks.get(0);

        // 3. Создаем запись о выдаче (с автоматической датой)
        UserBook userBook = new UserBook(user, book);
        userBookRepository.save(userBook);

        return "Книга '" + book.getTitle() + "' выдана пользователю " + userName + " (Дата выдачи: " + userBook.getIssueDate() + ")";
    }

    @GetMapping("/library/search")
    public List<BookResponse> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
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