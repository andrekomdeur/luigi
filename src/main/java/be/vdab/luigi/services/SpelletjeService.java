package be.vdab.luigi.services;
import be.vdab.luigi.domain.Spelletje;

import java.util.List;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
public interface SpelletjeService {
    long create(Spelletje spelletje);
    void update(Spelletje spelletje);
    void delete(long id);
    List<Spelletje> findAll();
    Optional<Spelletje> findById(long Id);
    long findAantalSpelletjes();
}
