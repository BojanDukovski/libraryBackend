package lab.library.service;

import lab.library.model.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> save(String name, String surname, Long countryId);
    void deleteById(Long id);
    Optional<Author> findById(Long id);
}
