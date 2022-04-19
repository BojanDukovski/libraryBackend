package lab.library.controller;

import lab.library.exceptions.AuthorNotFoundException;
import lab.library.exceptions.BookNotFoundException;
import lab.library.model.Author;
import lab.library.model.Book;
import lab.library.model.Category;
import lab.library.service.AuthorService;
import lab.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookRestController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookRestController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        return this.bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestParam String name,
                                        @RequestParam String availableCopies,
                                        @RequestParam String category,
                                        @RequestParam String author,
                                        @RequestParam boolean isTaken) {
        Author a = this.authorService.findById(Long.valueOf(author)).orElseThrow(AuthorNotFoundException::new);
        Book book = new Book(name, Category.valueOf(category), a, Integer.valueOf(availableCopies), false);
        return this.bookService.save(book)
                .map(b -> ResponseEntity.ok().body(b))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id,
                                         @RequestParam String name,
                                         @RequestParam String availableCopies,
                                         @RequestParam String category,
                                         @RequestParam Long author,
                                         @RequestParam boolean isTaken) {
        Book bookToEdit = this.bookService.findById(id).orElseThrow(BookNotFoundException::new);
        bookToEdit.setMarkAsTaken(isTaken);
        bookToEdit.setName(name);
        bookToEdit.setCategory(Category.valueOf(category));
        bookToEdit.setAvailableCopies(Integer.valueOf(availableCopies));
        bookToEdit.setAuthor(this.authorService.findById(Long.valueOf(author)).orElseThrow(AuthorNotFoundException::new));
        return this.bookService.edit((Long) id, bookToEdit)
                .map(b -> ResponseEntity.ok().body(b))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        if (this.bookService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/order/{id}")
    public Integer orderBook(@PathVariable Long id) {
        Book book = this.bookService.findById(id).orElseThrow(BookNotFoundException::new);
        book.setAvailableCopies(book.getAvailableCopies()-1);
        this.bookService.save(book);
        return book.getAvailableCopies();
    }
}
