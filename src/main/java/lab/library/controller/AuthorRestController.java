package lab.library.controller;

import lab.library.model.Author;
import lab.library.model.Country;
import lab.library.service.AuthorService;
import lab.library.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "https://library-lab2-emt-front.herokuapp.com")
public class AuthorRestController {
    private final AuthorService authorService;
    private final CountryService countryService;

    public AuthorRestController(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @GetMapping
    public List<Author> findAllAuthors() {
        return this.authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findAuthorById(@PathVariable Long id) {
        return this.authorService.findById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        return this.authorService.save(author.getName(), author.getSurname(), author.getCountry().getId())
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //no edit for Author

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAuthor(@RequestParam Long id) {
        this.authorService.deleteById(id);
        if (this.authorService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
