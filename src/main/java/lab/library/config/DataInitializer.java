package lab.library.config;

import lab.library.model.Author;
import lab.library.model.Book;
import lab.library.model.Category;
import lab.library.model.Country;
import lab.library.repository.AuthorRepo;
import lab.library.repository.BookRepo;
import lab.library.repository.CountryRepo;
import lab.library.service.AuthorService;
import lab.library.service.BookService;
import lab.library.service.CountryService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DataInitializer {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final CountryRepo countryRepo;

    public DataInitializer(AuthorRepo authorRepo, BookRepo bookRepo, CountryRepo countryRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.countryRepo = countryRepo;
    }

    @PostConstruct
    @Transactional
    public void initData() {
        Country country = new Country("Country1", "Continent1");
        Author author = new Author("Author1", "AuthorSurname", country);
        Author author1 = new Author("Author2", "AuthorSurname2", country);
        Author author2 = new Author("Author3", "AuthorSurname3", country);
        Book book = new Book("Book1", Category.BIOGRAPHY, author, 1, false);
        Book book1 = new Book("Book2", Category.DRAMA, author1, 0, false);
        Book book2 = new Book("Book3", Category.CLASSICS, author, 4, false);
        Book book3 = new Book("Book4", Category.FANTASY, author2, 10, false);
        Book book4 = new Book("Book5", Category.HISTORY, author, 0, false);
        Book book5 = new Book("Book6", Category.BIOGRAPHY, author2, 1, false);

        this.countryRepo.save(country);

        this.authorRepo.save(author);
        this.authorRepo.save(author1);
        this.authorRepo.save(author2);

        this.bookRepo.save(book);
        this.bookRepo.save(book1);
        this.bookRepo.save(book2);
        this.bookRepo.save(book3);
        this.bookRepo.save(book4);
        this.bookRepo.save(book5);

    }

}
