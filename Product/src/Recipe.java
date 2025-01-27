import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private String type;
    private int servings;
    private List<Ingredient> ingredients;
    private List<Double> quantities;
    private transient Main mainApp;

    public Recipe(int id, String name, String type, int servings, List<Ingredient> ingredients, List<Double> quantities, Main mainApp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.servings = servings;
        this.ingredients = ingredients;
        this.quantities = quantities;
        this.mainApp = mainApp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
    public List<Double> getQuantities() { return quantities; }
    public void setQuantities(List<Double> quantities) { this.quantities = quantities; }

    public double getCostPerServing() {
        double cost = 0;
        List<Ingredient> ingredients = getIngredients();
        List<Double> quantities = getQuantities();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            double quantity = quantities.get(i);
            cost += ingredient.getCost() * quantity;
        }
        return cost / servings;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", name=" + name + ", type=" + type + ", servings=" + servings + ", ingredients="
                + ingredients + ", quantities=" + quantities +"]";
    }
}
