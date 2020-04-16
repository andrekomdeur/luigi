package be.vdab.luigi.controllers;
import be.vdab.luigi.sessions.Identificatie;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
/**
 * @Author Andre Komdeur
 */
@ControllerAdvice
public class DefaultController {
    private final Identificatie identificatie;

    DefaultController(Identificatie identificatie) {
        this.identificatie = identificatie;
    }

    @ModelAttribute
    public void extraDataToevoegenAanModel(Model model) {
        String email = identificatie.getEmailAdres();
        if (email != null) {
            model.addAttribute(email);
        }
    }
}
