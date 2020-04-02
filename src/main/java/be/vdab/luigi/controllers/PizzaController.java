package be.vdab.luigi.controllers;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@RequestMapping("pizzas")
class PizzaController {
    private final EuroService euroService;

    private final PizzaService pizzaService;

    PizzaController(EuroService euroService,PizzaService pizzaService) {
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
            modelAndView.addObject("inDollar",euroService.naarDollar(pizza.getPrijs()));
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
}
