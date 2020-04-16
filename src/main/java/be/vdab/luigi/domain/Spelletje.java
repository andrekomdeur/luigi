package be.vdab.luigi.domain;
import javax.validation.constraints.NotBlank;
/**
 * @Author Andre Komdeur
 */
public class Spelletje {
    private final long id;
    @NotBlank
    private final String naam;
    private final String lowNaam;

    public Spelletje(long id, @NotBlank String naam) {
        this.id = id;
        this.naam = naam;
        this.lowNaam = naam.toLowerCase().replace(" ","");
    }

    public String getLowNaam() {
        return lowNaam;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam.toLowerCase();
    }
}
