import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Enumeration;
import javax.swing.plaf.FontUIResource;

public class Main {
    private JFrame mainFrame;
    private HomeScreen homeScreen;
    private IngredientScreen ingredientScreen;
    private RecipeScreen recipeScreen;
    private MealPlanScreen mealPlanScreen;
    private WeeklyPlanScreen weeklyPlanScreen;

    private DataModel dataModel;

    public Main() {
        dataModel = new DataModel();
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Cafeteria Budgeting System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        homeScreen = new HomeScreen(this);
        ingredientScreen = new IngredientScreen(this);
        recipeScreen = new RecipeScreen(this);
        mealPlanScreen = new MealPlanScreen(this);
        weeklyPlanScreen = new WeeklyPlanScreen(this);

        mainFrame.add(homeScreen.getContentPane());

        mainFrame.setLocationRelativeTo(null);

        for(Frame frame : JFrame.getFrames()) {
            if(frame != mainFrame) {
                frame.dispose();
            }
        }
    }

    public void showHomeScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(homeScreen.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }


    public void showIngredientScreen(){
        ingredientScreen.clearFields();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(ingredientScreen.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showRecipeScreen(){
        recipeScreen.clearFields();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(recipeScreen.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showMealPlanScreen(){
        mealPlanScreen.clearFields();
        refreshMealPlanScreen();
        mealPlanScreen.initializeComboBoxes();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(mealPlanScreen.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showWeeklyPlanScreen(){
        weeklyPlanScreen.clearFields();
        refreshWeeklyPlanScreen();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(weeklyPlanScreen.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void addIngredient(Ingredient ingredient) {
        dataModel.getIngredients().add(ingredient);
        ingredientScreen.refreshTable(this);
        saveData();
    }

    public void deleteIngredient(Ingredient ingredient) {
        dataModel.getIngredients().remove(ingredient);
        ingredientScreen.refreshTable(this);
        saveData();
    }

    public List<Ingredient> getIngredients() {
        return dataModel.getIngredients();
    }


    public void updateIngredient(Ingredient updatedIngredient) {
        for (int i = 0; i < dataModel.getIngredients().size(); i++) {
            if (dataModel.getIngredients().get(i).getId() == updatedIngredient.getId()) {
                dataModel.getIngredients().set(i, updatedIngredient);
                break;
            }
        }
        ingredientScreen.refreshTable(this);
        saveData();
    }

    public void addRecipe(Recipe recipe) {
        dataModel.getRecipes().add(recipe);
        recipeScreen.refreshTable(this);
        saveData();
    }
    public void deleteRecipe(Recipe recipe) {
        dataModel.getRecipes().remove(recipe);
        recipeScreen.refreshTable(this);
        saveData();
    }

    public List<Recipe> getRecipes() {
        return dataModel.getRecipes();
    }

    public void updateRecipe(Recipe updatedRecipe) {
        for (int i = 0; i < dataModel.getRecipes().size(); i++) {
            if (dataModel.getRecipes().get(i).getId() == updatedRecipe.getId()) {
                dataModel.getRecipes().set(i, updatedRecipe);
                break;
            }
        }
        recipeScreen.refreshTable(this);
        saveData();
    }

    public void addMealPlan(MealPlan mealPlan) {
        dataModel.getMealPlans().add(mealPlan);
        mealPlanScreen.refreshTable(this);
        saveData();
    }

    public void deleteMealPlan(MealPlan mealPlan) {
        dataModel.getMealPlans().remove(mealPlan);
        mealPlanScreen.refreshTable(this);
        saveData();
    }

    public List<MealPlan> getMealPlans() {
        return dataModel.getMealPlans();
    }

    public void refreshMealPlanScreen() {
        mealPlanScreen.initializeComboBoxes();
    }

    public void addWeeklyPlan(WeeklyPlan weeklyPlan) {
        dataModel.getWeeklyPlans().add(weeklyPlan);
        weeklyPlanScreen.refreshTable(this);
        saveData();
    }

    public void updateWeeklyPlan(WeeklyPlan updatedWeeklyPlan) {
        for (int i = 0; i < dataModel.getWeeklyPlans().size(); i++) {
            if (dataModel.getWeeklyPlans().get(i).getId() == updatedWeeklyPlan.getId()) {
                dataModel.getWeeklyPlans().set(i, updatedWeeklyPlan);
                break;
            }
        }
        weeklyPlanScreen.refreshTable(this);
        saveData();
    }

    public void deleteWeeklyPlan(WeeklyPlan weeklyPlan) {
        dataModel.getWeeklyPlans().remove(weeklyPlan);
        weeklyPlanScreen.refreshTable(this);
        saveData();
    }

    public List<WeeklyPlan> getWeeklyPlans() {
        return dataModel.getWeeklyPlans();
    }

    public void refreshWeeklyPlanScreen() {
        weeklyPlanScreen.initializeComboBoxes();
    }

    public Ingredient createIngredient(String name, int cost, String unit) {
        int id = dataModel.getNextIngredientId();
        return new Ingredient(id, name, cost, unit);
    }

    public Recipe createRecipe(String name, String type, int servings, List<Ingredient> ingredients, List<Double> quantities, Main mainApp) {
        int id = dataModel.getNextRecipeId();
        return new Recipe(id, name, type, servings, ingredients, quantities, mainApp);
    }

    public MealPlan createMealPlan(String name, Recipe mainDish, Recipe sideDish, Recipe dessert) {
        int id = dataModel.getNextMealPlanId();
        return new MealPlan(id, name, mainDish, sideDish, dessert);
    }

    public WeeklyPlan createWeeklyPlan(String name, Recipe[][] weeklyRecipes, boolean[] daysEnabled) {
        int id = dataModel.getNextWeeklyPlanId();
        return new WeeklyPlan(id, name, weeklyRecipes, daysEnabled);
    }

    public void saveData() {
        try (FileOutputStream fileOut = new FileOutputStream("cafeteria_data.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(dataModel);
        } catch (IOException i) {
            System.err.println("Error saving data: " + i.getMessage());
            i.printStackTrace();
        }
    }

    public void loadData() {
        try (FileInputStream fileIn = new FileInputStream("cafeteria_data.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            dataModel = (DataModel) in.readObject();
            for (Recipe recipe : dataModel.getRecipes()) {
                recipe.setMainApp(this);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found, will create a new one.");
            dataModel = new DataModel();
            saveData();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            dataModel = new DataModel();
        }
    }

    public void start() {
        mainFrame.setVisible(true);
        loadData();
        ingredientScreen.refreshTable(this);
        recipeScreen.refreshTable(this);
        mealPlanScreen.refreshTable(this);
        weeklyPlanScreen.refreshTable(this);
        refreshMealPlanScreen();
        refreshWeeklyPlanScreen();
    }

    private static void setUIFont(FontUIResource resource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, resource);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 20));

        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.start();
        });
    }
}