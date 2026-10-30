package org.example.controller;

import org.example.entity.Author;
import org.example.entity.AuthorBookDto;
import org.example.entity.Book;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crud/books")
public class CrudController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public CrudController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setPages(bookDetails.getPages());
                    return ResponseEntity.ok(bookRepository.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/more-than-100-pages")
    public List<Book> getBooksWithMoreThan100Pages() {
        return bookRepository.findByPagesGreaterThan(100);
    }

    @GetMapping("/more-than-pages")
    public List<Book> getBooksWithMoreThanXPages(@RequestParam int x) {
        return bookRepository.findByPagesGreaterThan(x);
    }

    @GetMapping("/by-author")
    public List<AuthorBookDto> getBooksByAuthorSurname(@RequestParam String surname) {
        List<Author> authors = authorRepository.findAll().stream()
                .filter(a -> a.getName().toLowerCase().contains(surname.toLowerCase()))
                .collect(Collectors.toList());

        return authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> new AuthorBookDto(book.getTitle(), book.getPages()))
                .distinct()
                .collect(Collectors.toList());
    }
}