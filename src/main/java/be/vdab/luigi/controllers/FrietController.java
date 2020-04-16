package be.vdab.luigi.controllers;
import be.vdab.luigi.sessions.ZoekDeFriet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * @Author Andre Komdeur
 */
@Controller
@RequestMapping("zoekdefriet")
public class FrietController {
    private ZoekDeFriet zoekDeFriet;

    public FrietController(ZoekDeFriet zoekDeFriet) {
        this.zoekDeFriet = zoekDeFriet;
    }
    @GetMapping("show")
    public ModelAndView zoekDeFriet(@CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("zoekdefriet","zoekDeFriet", zoekDeFriet)
                .addObject("kleur", kleur);
    }
    @PostMapping("nieuwspel")
    public String nieuwSpel() {
        zoekDeFriet.reset();
        return "redirect:/zoekdefriet/show";
    }
    @PostMapping("opendeur")
    public String openDeur(int index) {
        zoekDeFriet.openDeur(index);
        return "redirect:/zoekdefriet/show";
    }
}
