package lab.library.service.impl;

import lab.library.exceptions.AuthorNotFoundException;
import lab.library.exceptions.BookNotFoundException;
import lab.library.model.Author;
import lab.library.model.Book;
import lab.library.model.Category;
import lab.library.repository.AuthorRepo;
import lab.library.repository.BookRepo;
import lab.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepository;
    private final AuthorRepo authorRepository;

    public BookServiceImpl(BookRepo bookRepository, AuthorRepo authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(String name, Category category, Long authorId,
                               Integer availableCopies, boolean isTaken) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        Book book = new Book(name, category, author, availableCopies, isTaken);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> save(Book book) {
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public void deleteById(Long bookId) {
        this.bookRepository.deleteById(bookId);
    }

    @Override
    public Optional<Book> edit(Long id, Book book) {
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> markAsTaken(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setMarkAsTaken(true);
        return Optional.of(book);
    }
}
