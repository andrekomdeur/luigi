package be.vdab.luigi.sessions;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.services.PizzaService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @Author Andre Komdeur
 */
@Component
@SessionScope
public class RaadDePizza  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pogingen;
    private StringBuilder woord;
    private String teRadenPizza;
    private PizzaService pizzaService;
    private List<Pizza> pizzas;
    public RaadDePizza(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
        pizzas = pizzaService.findAll();
        reset();
    }
    public void reset() {
        long indexPizzas = ThreadLocalRandom.current().nextLong(pizzaService.findAantalPizzas());
        teRadenPizza = pizzas.get(Math.toIntExact(indexPizzas)).getNaam().toLowerCase();
        pogingen = 0;

        woord = new StringBuilder(teRadenPizza.length());
        for (int i = 0; i < teRadenPizza.length() ; i++) {
            woord.append(".");
        }
    }
    public void raden(Character letter){
        int letterIndex = teRadenPizza.indexOf(letter);
        if (letterIndex == -1){
            pogingen++;
        } else {
            do {
                woord.setCharAt(letterIndex,letter);
                letterIndex = teRadenPizza.indexOf(letter,letterIndex + 1);
            } while (letterIndex != -1);
        }
    }

    public int getPogingen() {
        return pogingen;
    }

    public String getWoord() {
        return woord.toString();
    }

    public String getTeRadenPizza() {
        return teRadenPizza;
    }

    public PizzaService getPizzaService() {
        return pizzaService;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
    public boolean isGewonnen(){
        return woord.indexOf(".") == -1;
    }
    public boolean isVerloren(){
        return pogingen == 10;
    }
}
