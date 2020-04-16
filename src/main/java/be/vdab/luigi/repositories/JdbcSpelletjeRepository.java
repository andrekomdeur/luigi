package be.vdab.luigi.repositories;
import be.vdab.luigi.domain.Spelletje;
import be.vdab.luigi.exceptions.SpelletjeNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
@Repository
public class JdbcSpelletjeRepository implements SpelletjeRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Spelletje> spelletjeMapper =
            (result, rowNum) -> new Spelletje(
                    result.getLong("id"),
                    result.getString("naam"));

    JdbcSpelletjeRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("spelletjes");
        insert.usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(Spelletje spelletje) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("naam", spelletje.getNaam());
        Number id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }

    @Override
    public void update(Spelletje spelletje) {
        String sql = "update spelletjes set naam=? where id=?";
        if (template.update(sql,
                spelletje.getNaam(),
                spelletje.getId()) == 0) {
            throw new SpelletjeNietGevondenException();
        }
    }

    @Override
    public void delete(long id) {
        template.update("delete from spelletjes where id=?", id);
    }

    @Override
    public List<Spelletje> findAll() {
        String sql = "select id, naam from spelletjes order by id";
        return template.query(sql, spelletjeMapper);
    }

    @Override
    public Optional<Spelletje> findById(long id) {
        try {
            String sql = "select id, naam from spelletjes where id=?";
            return Optional.of(template.queryForObject(sql, spelletjeMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public long findAantalSpelletjes() {
        return template.queryForObject("select count(*) from spelletjes", Long.class);
    }

}