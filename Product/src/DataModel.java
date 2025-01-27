import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataModel implements Serializable {
    private List<Ingredient> ingredients;
    private List<Recipe> recipes;
    private List<MealPlan> mealPlans;
    private List<WeeklyPlan> weeklyPlans;

    private int ingredientIdCounter = 1;
    private int recipeIdCounter = 1;
    private int mealPlanIdCounter = 1;
    private int weeklyPlanIdCounter = 1;


    public DataModel() {
        this.ingredients = new ArrayList<>();
        this.recipes = new ArrayList<>();
        this.mealPlans = new ArrayList<>();
        this.weeklyPlans = new ArrayList<>();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public List<WeeklyPlan> getWeeklyPlans() {
        return weeklyPlans;
    }

    public void setWeeklyPlans(List<WeeklyPlan> weeklyPlans) {
        this.weeklyPlans = weeklyPlans;
    }

    public int getNextIngredientId() {
        return ingredientIdCounter++;
    }

    public int getNextRecipeId() {
        return recipeIdCounter++;
    }

    public int getNextMealPlanId() {
        return mealPlanIdCounter++;
    }

    public int getNextWeeklyPlanId() {
        return weeklyPlanIdCounter++;
    }
}
