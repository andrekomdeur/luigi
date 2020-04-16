package be.vdab.luigi.controllers;
import be.vdab.luigi.services.SpelletjeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * @Author Andre Komdeur
 */
@Controller
@RequestMapping("spelletjes")
class SpelletjeController {
    private final SpelletjeService spelletjeService;
    SpelletjeController(SpelletjeService spelletjeService) {
        this.spelletjeService = spelletjeService;
    }

    @GetMapping
    public ModelAndView spelletjes(@CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView("spelletjes", "spelletjes", spelletjeService.findAll());
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }
    @GetMapping("{id}")
    public ModelAndView spelletje(@PathVariable long id, @CookieValue(name = "kleur", required = false) String kleur) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        ModelAndView modelAndView = new ModelAndView("spelletje");
        spelletjeService.findById(id).ifPresent(spelletje -> {
            modelAndView.addObject(spelletje);
        });
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }
}
