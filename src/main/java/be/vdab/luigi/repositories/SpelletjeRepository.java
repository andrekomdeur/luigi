package be.vdab.luigi.repositories;
import be.vdab.luigi.domain.Spelletje;

import java.util.List;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
public interface SpelletjeRepository {
    long create(Spelletje spelletje);
    void update(Spelletje spelletje);
    void delete(long id);
    List<Spelletje> findAll();
    Optional<Spelletje> findById(long id);
    long findAantalSpelletjes();
}
