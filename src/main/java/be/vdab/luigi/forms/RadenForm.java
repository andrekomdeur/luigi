package be.vdab.luigi.forms;
import javax.validation.constraints.NotNull;
/**
 * @Author Andre Komdeur
 */
public class RadenForm {
    @NotNull
    private final Character letter;
    public RadenForm(@NotNull Character letter) {
        this.letter = letter;
    }

    public Character getLetter() {
        return letter;
    }
}
