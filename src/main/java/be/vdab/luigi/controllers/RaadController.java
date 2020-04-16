package be.vdab.luigi.controllers;
import be.vdab.luigi.forms.RadenForm;
import be.vdab.luigi.sessions.RaadDePizza;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
/**
 * @Author Andre Komdeur
 */
@Controller
@RequestMapping("raaddepizza")
public class RaadController {
    private RaadDePizza raadDePizza;

    public RaadController(RaadDePizza raadDePizza) {
        this.raadDePizza = raadDePizza;
    }
    @GetMapping("show")
    public ModelAndView raadDePizza(@Valid RadenForm form, Errors errors, @CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("raaddepizza","raadDePizza", raadDePizza)
                .addObject("RadenForm",new RadenForm(null))
                .addObject("kleur", kleur);
    }
    @PostMapping("nieuwspel")
    public String nieuwSpel() {
        raadDePizza.reset();
        return "redirect:/raaddepizza/show";
    }
    @PostMapping(value = "raden")
    public ModelAndView raden(@Valid RadenForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("raaddepizza","raadDePizza", raadDePizza);
        }
        raadDePizza.raden(form.getLetter());
        return new ModelAndView("redirect:/raaddepizza/show");
    }
}
