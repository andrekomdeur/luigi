package be.vdab.luigi.services;
import be.vdab.luigi.domain.Spelletje;
import be.vdab.luigi.repositories.SpelletjeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
@Service
@Transactional
public class DefaultSpelletjeService implements SpelletjeService {
    private final SpelletjeRepository spelletjeRepository;
    DefaultSpelletjeService(SpelletjeRepository spelletjeRepository) {
        this.spelletjeRepository = spelletjeRepository;
    }
    @Override
    public long create(Spelletje spelletje) {
        return spelletjeRepository.create(spelletje);
    }
    @Override
    public void update(Spelletje spelletje) {
        spelletjeRepository.update(spelletje);
    }
    @Override
    public void delete(long id) {
        spelletjeRepository.delete(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Spelletje> findAll() {
        return spelletjeRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Spelletje> findById(long id) {
        return spelletjeRepository.findById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public long findAantalSpelletjes() {
        return spelletjeRepository.findAantalSpelletjes();
    }
}