package lab.library.service;

import lab.library.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();
    Optional<Country> findById(Long id);
    Optional<Country> save(Country country);
    void deleteById(Long id);
}
