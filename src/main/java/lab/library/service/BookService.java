package lab.library.service;

import lab.library.model.Author;
import lab.library.model.Book;
import lab.library.model.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> save(String name, Category category, Long authorId, Integer availableCopies, boolean isTaken);
    Optional<Book> save(Book book);
    void deleteById(Long id);
    Optional<Book> edit(Long id, Book book);
    Optional<Book> markAsTaken(Long id);
}
