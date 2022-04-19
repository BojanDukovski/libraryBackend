package lab.library.controller;

import lab.library.exceptions.CountryNotFoundException;
import lab.library.model.Country;
import lab.library.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "https://library-lab2-emt-front.herokuapp.com")
public class CountryRestController {
    private final CountryService countryService;

    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> findAllCountries() {
        return this.countryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable Long id) {
        return this.countryService.findById(id)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Country> saveCountry(@RequestBody Country country) {
        return this.countryService.save(country)
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //no edit for Country

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCountry(@RequestParam Long id) {
        this.countryService.deleteById(id);
        if (this.countryService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
