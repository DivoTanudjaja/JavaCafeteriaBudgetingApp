import java.io.Serializable;

public class MealPlan implements Serializable {
    private int id;
    private String name;
    private Recipe mainDish;
    private Recipe sideDish;
    private Recipe dessert;


    public MealPlan(int id, String name, Recipe mainDish, Recipe sideDish, Recipe dessert) {
        this.id = id;
        this.name = name;
        this.mainDish = mainDish;
        this.sideDish = sideDish;
        this.dessert = dessert;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMainDish(Recipe mainDish) {
        this.mainDish = mainDish;
    }

    public Recipe getMainDish() {
        return mainDish;
    }

    public void setSideDish(Recipe sideDish) {
        this.sideDish = sideDish;
    }

    public Recipe getSideDish() {
        return sideDish;
    }

    public void setDessert(Recipe dessert) {
        this.dessert = dessert;
    }

    public Recipe getDessert() {
        return dessert;
    }
}