package lab.library.service.impl;

import lab.library.exceptions.CountryNotFoundException;
import lab.library.model.Author;
import lab.library.model.Country;
import lab.library.repository.AuthorRepo;
import lab.library.repository.CountryRepo;
import lab.library.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepository;
    private final CountryRepo countryRepository;

    public AuthorServiceImpl(AuthorRepo authorRepository, CountryRepo countryRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> save(String name, String surname, Long countryId) {
        Country country = this.countryRepository.findById(countryId).orElseThrow(CountryNotFoundException::new);
        Author author = new Author(name, surname, country);
        this.authorRepository.save(author);
        return Optional.of(author);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }
}
