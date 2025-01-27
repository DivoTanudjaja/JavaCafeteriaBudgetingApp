import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MealPlanScreen extends JFrame {
    private JPanel panel1;
    private JTable mealPlanTable;
    private JPanel tablePanel;
    private JPanel editorPanel;
    private JTextField nameField;
    private JComboBox<String> mainDishComboBox;
    private JComboBox<String> sideDishComboBox;
    private JComboBox<String> dessertComboBox;
    private JButton deleteMealPlanButton;
    private JButton updateMealPlanButton;
    private JButton saveAsNewMealButton;
    private JButton homeButton;
    private JPanel topMargin;
    private JLabel mealPlanLabel;
    private Main mainApp;
    private MealPlanTableModel mealPlanTableModel;

    public MealPlanScreen(Main mainApp) {
        this.mainApp = mainApp;
        initializeUI();
        initializeComboBoxes();
        setupTable();

        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);

        topMargin.setLayout(new BorderLayout(0, 0));
        homeButton.setPreferredSize(new Dimension(50, 50));
        topMargin.add(homeButton, BorderLayout.WEST);

        mealPlanLabel.setText("<html><div style='text-align:center; color:white; font-size:30px; font-weight:bold;'>Meal Plans</div></html>");
        mealPlanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topMargin.add(mealPlanLabel, BorderLayout.CENTER);

        mealPlanTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = mealPlanTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    MealPlan selectedMealPlan = mealPlanTableModel.getMealPlanAt(row);
                    updateFields(selectedMealPlan);
                }
            }
        });

        saveAsNewMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNewMealPlan();
            }
        });

        updateMealPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMealPlan();
            }
        });

        deleteMealPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMealPlan();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainApp.showHomeScreen();
            }
        });
    }

    private void initializeUI() {
        setTitle("Meal Plan Management");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void initializeComboBoxes() {
        fillComboBox(mainDishComboBox, "Main Course");
        fillComboBox(sideDishComboBox, "Side Dish");
        fillComboBox(dessertComboBox, "Dessert");
    }

    private void fillComboBox(JComboBox<String> comboBox, String type) {
        comboBox.removeAllItems();
        comboBox.addItem("(no recipe selected)");
        mainApp.getRecipes().stream()
                .filter(r -> r.getType().equals(type))
                .map(Recipe::getName)
                .forEach(comboBox::addItem);
    }

    private void setupTable() {
        mealPlanTableModel = new MealPlanTableModel(mainApp.getMealPlans());
        mealPlanTable.setModel(mealPlanTableModel);
    }

    private void saveNewMealPlan() {
        String name = nameField.getText();
        String mainDishName = (String) mainDishComboBox.getSelectedItem();
        String sideDishName = (String) sideDishComboBox.getSelectedItem();
        String dessertName = (String) dessertComboBox.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a name for the meal plan.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("(no recipe selected)".equals(mainDishName)) {
            JOptionPane.showMessageDialog(this, "Please select a main dish.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("(no recipe selected)".equals(sideDishName)) {
            JOptionPane.showMessageDialog(this, "Please select a side dish.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("(no recipe selected)".equals(dessertName)) {
            JOptionPane.showMessageDialog(this, "Please select a dessert.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Recipe mainDish = findRecipeByName(mainDishName);
        Recipe sideDish = findRecipeByName(sideDishName);
        Recipe dessert = findRecipeByName(dessertName);

        MealPlan newMealPlan = mainApp.createMealPlan(name, mainDish, sideDish, dessert);
        mainApp.addMealPlan(newMealPlan);
        JOptionPane.showMessageDialog(this, "Meal plan added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
        refreshTable(mainApp);
    }

    private Recipe findRecipeByName(String name) {
        return mainApp.getRecipes().stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


    private void updateMealPlan() {
        int selectedRow = mealPlanTable.getSelectedRow();
        if (selectedRow != -1) {
            MealPlan mealPlan = mealPlanTableModel.getMealPlanAt(selectedRow);
            String name = nameField.getText();
            String mainDishName = (String) mainDishComboBox.getSelectedItem();
            String sideDishName = (String) sideDishComboBox.getSelectedItem();
            String dessertName = (String) dessertComboBox.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name for the meal plan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ("(no recipe selected)".equals(mainDishName)) {
                JOptionPane.showMessageDialog(this, "Please select a main dish.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ("(no recipe selected)".equals(sideDishName)) {
                JOptionPane.showMessageDialog(this, "Please select a side dish.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ("(no recipe selected)".equals(dessertName)) {
                JOptionPane.showMessageDialog(this, "Please select a dessert.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            mealPlan.setName(name);
            mealPlan.setMainDish(findRecipeByName(mainDishName));
            mealPlan.setSideDish(findRecipeByName(sideDishName));
            mealPlan.setDessert(findRecipeByName(dessertName));
            refreshTable(mainApp);
            clearFields();
            JOptionPane.showMessageDialog(this, "Meal plan updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a meal plan to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMealPlan() {
        int selectedRow = mealPlanTable.getSelectedRow();
        if (selectedRow != -1) {
            MealPlan mealPlanToDelete = mealPlanTableModel.getMealPlanAt(selectedRow);
            if (mealPlanToDelete != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this meal plan?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    mainApp.deleteMealPlan(mealPlanToDelete);
                    refreshTable(mainApp);
                    JOptionPane.showMessageDialog(this, "Meal plan deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting the meal plan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a meal plan to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshTable(Main mainApp) {
        mealPlanTableModel.setDisplayedMealPlans(mainApp.getMealPlans());
        mealPlanTableModel.fireTableDataChanged();
    }

    public void updateFields(MealPlan mealPlan) {
        nameField.setText(mealPlan.getName());
        setSelectedItemInComboBox(mainDishComboBox, mealPlan.getMainDish().getName());
        setSelectedItemInComboBox(sideDishComboBox, mealPlan.getSideDish().getName());
        setSelectedItemInComboBox(dessertComboBox, mealPlan.getDessert().getName());
    }

    private void setSelectedItemInComboBox(JComboBox<String> comboBox, String itemName) {
        if (comboBox.getItemCount() > 0) {
            comboBox.setSelectedItem(itemName);
        }
    }

    public void clearFields() {
        nameField.setText("");
        mainDishComboBox.setSelectedIndex(0);
        sideDishComboBox.setSelectedIndex(0);
        dessertComboBox.setSelectedIndex(0);
        mealPlanTable.clearSelection();
    }
}

