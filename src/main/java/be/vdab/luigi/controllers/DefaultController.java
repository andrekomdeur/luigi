package be.vdab.luigi.controllers;
import be.vdab.luigi.sessions.Identificatie;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
/**
 * @Author Andre Komdeur
 */
@ControllerAdvice
public class DefaultController {
    private final Identificatie emailid;
    DefaultController(Identificatie emailid) {
        this.emailid = emailid;
    }
    @ModelAttribute
    public void extraDataToevoegenAanModel(ModelAndView model) {
        model.addObject("emailid", emailid.getEmailAdres());
    }
}
