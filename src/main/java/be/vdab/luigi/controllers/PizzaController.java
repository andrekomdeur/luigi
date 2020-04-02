package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.services.EuroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pizzas")
class PizzaController {
    private final EuroService euroService;
    private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4.5), false)};

    PizzaController(EuroService euroService) {
        this.euroService = euroService;
    }

    @GetMapping
    public ModelAndView pizzas(@CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView("pizzas", "pizzas", pizzas);
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id, @CookieValue(name = "kleur", required = false) String kleur) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        ModelAndView modelAndView = new ModelAndView("pizza");
        Arrays.stream(pizzas)
                .filter(pizza -> pizza.getId() == id)
                .findFirst()
                .ifPresent(pizza -> {
                    modelAndView.addObject("pizza",pizza);
                    try {
                        modelAndView.addObject(
                                "inDollar", euroService.naarDollar(pizza.getPrijs()));
                    } catch (KoersClientException ex) {
                        logger.error("Kan dollar koers niet lezen", ex);
                    }
                });
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    private List<BigDecimal> uniekePrijzen() {
        return Arrays.stream(pizzas)
                .map(pizza -> pizza.getPrijs())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("prijzen")
    public ModelAndView prijzen(@CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView("prijzen", "prijzen", uniekePrijzen());
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }

    private List<Pizza> pizzasMetPrijs(BigDecimal prijs) {
        return Arrays.stream(pizzas)
                .filter(pizza -> pizza.getPrijs().compareTo(prijs) == 0)
                .collect(Collectors.toList());
    }

    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(
            @PathVariable BigDecimal prijs,
            @CookieValue(name = "kleur", required = false) String kleur) {
        return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs))
                .addObject("prijzen", uniekePrijzen())
                .addObject("kleur", kleur);
    }
}