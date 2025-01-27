import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame{
    private JButton ingredientsButton;
    private JPanel panel1;
    private JButton mealPlansButton;
    private JButton recipesButton;
    private JButton weeklyPlansButton;

    public HomeScreen(Main mainApp) {
        initializeUI();
        setupButtons(mainApp);
    }

    private void initializeUI() {
        setTitle("Home Screen");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void setupButtons(Main mainApp) {
        int width = 200;
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imgresources/ingredient.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        ingredientsButton.setIcon(scaledIcon);
        ingredientsButton.setText("<html><div style='text-align:center; font-size:24px; font-weight:bold;'>Ingredients</div></html>");
        ingredientsButton.setHorizontalTextPosition(JButton.CENTER);
        ingredientsButton.setVerticalTextPosition(JButton.BOTTOM);

        ImageIcon originalIcon2 = new ImageIcon(getClass().getResource("/imgresources/mealplan.png"));
        Image scaledImage2 = originalIcon2.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        mealPlansButton.setIcon(scaledIcon2);
        mealPlansButton.setText("<html><div style='text-align:center; font-size:24px; font-weight:bold;'>Meal Plans</div></html>");
        mealPlansButton.setHorizontalTextPosition(JButton.CENTER);
        mealPlansButton.setVerticalTextPosition(JButton.BOTTOM);

        ImageIcon originalIcon3 = new ImageIcon(getClass().getResource("/imgresources/recipe.png"));
        Image scaledImage3 = originalIcon3.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        recipesButton.setIcon(scaledIcon3);
        recipesButton.setText("<html><div style='text-align:center; font-size:24px; font-weight:bold;'>Recipes</div></html>");
        recipesButton.setHorizontalTextPosition(JButton.CENTER);
        recipesButton.setVerticalTextPosition(JButton.BOTTOM);

        ImageIcon originalIcon4 = new ImageIcon(getClass().getResource("/imgresources/weeklyplan.png"));
        Image scaledImage4 = originalIcon4.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);
        weeklyPlansButton.setIcon(scaledIcon4);
        weeklyPlansButton.setText("<html><div style='text-align:center; font-size:24px; font-weight:bold;'>Weekly Plans</div></html>");
        weeklyPlansButton.setHorizontalTextPosition(JButton.CENTER);
        weeklyPlansButton.setVerticalTextPosition(JButton.BOTTOM);

        ingredientsButton.addActionListener(e -> mainApp.showIngredientScreen());
        mealPlansButton.addActionListener(e -> mainApp.showMealPlanScreen());
        recipesButton.addActionListener(e -> mainApp.showRecipeScreen());
        weeklyPlansButton.addActionListener(e -> mainApp.showWeeklyPlanScreen());
    }
}
