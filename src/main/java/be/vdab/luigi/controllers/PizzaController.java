package be.vdab.luigi.controllers;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.forms.VanTotPrijsForm;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
@Controller
@RequestMapping("pizzas")
class PizzaController {
    private final EuroService euroService;
    private final PizzaService pizzaService;

    PizzaController(EuroService euroService, PizzaService pizzaService) {
        this.euroService = euroService;
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ModelAndView pizzas(@CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id, @CookieValue(name = "kleur", required = false) String kleur) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        ModelAndView modelAndView = new ModelAndView("pizza");
        pizzaService.findById(id).ifPresent(pizza -> {
            modelAndView.addObject(pizza);
            modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
        });
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    @GetMapping("prijzen")
    public ModelAndView prijzen(@CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView(
                "prijzen",
                "prijzen",
                pizzaService.findUniekePrijzen());
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(
            @PathVariable BigDecimal prijs,
            @CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("prijzen", "pizzas", pizzaService.findByPrijs(prijs))
                .addObject("prijzen", pizzaService.findUniekePrijzen())
                .addObject("kleur", kleur);
    }

    @GetMapping("vantotprijs/form")
    public ModelAndView vanTotPrijsForm(@CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("vantotprijs")
                .addObject(new VanTotPrijsForm(null, null))
                .addObject("kleur", kleur);
    }

    @GetMapping("vantotprijs")
    public ModelAndView vanTotPrijs(@Valid VanTotPrijsForm form, Errors errors, @CookieValue(name = "kleur", required = false) String kleur) {
        if (errors.hasErrors()) {
            return new ModelAndView("vantotprijs");
        }
        return new ModelAndView("vantotprijs", "pizzas",
                pizzaService.findByPrijsBetween(form.getVan(), form.getTot()))
                .addObject("kleur", kleur);
    }
    @GetMapping("toevoegen/form")
    public ModelAndView toevoegenForm( @CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("toevoegen")
                .addObject(new Pizza(0,"",null,false))
                .addObject("kleur", kleur);
    }
    @PostMapping
    public String toevoegen(@Valid Pizza pizza, Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return "toevoegen";
        }
        long id = pizzaService.create(pizza);
        redirect.addAttribute("toegevoegd", id);
        return "redirect:/pizzas";
/*    public ModelAndView toevoegen(@Valid Pizza pizza, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("toevoegen");
        }
        pizzaService.create(pizza);
        return new ModelAndView("redirect:/pizzas", "pizzas", pizzaService.findAll());
*/  }
}
