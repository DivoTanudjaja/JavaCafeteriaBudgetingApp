import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class IngredientScreen extends JFrame{
    private JPanel ingredientPanel;
    private JPanel editorPanel;
    private JTable ingredientTable;
    private JTextField costField;
    private JTextField nameField;
    private JRadioButton perLiterRadioButton;
    private JRadioButton perPieceRadioButton;
    private JRadioButton perKgRadioButton;
    private JButton updateIngredientButton;
    private JButton saveAsNewIngredientButton;
    private JButton deleteIngredientButton;
    private JButton homeButton;
    private JPanel topMargin;
    private JLabel ingredientsLabel;
    private IngredientTableModel tableModel;
    private Main mainApp;

    public IngredientScreen(Main mainApp) {
        this.mainApp = mainApp;
        initializeUI();
        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);

        topMargin.setLayout(new BorderLayout(0, 0));

        homeButton.setPreferredSize(new Dimension(50, 50));
        topMargin.add(homeButton, BorderLayout.WEST);

        ingredientsLabel.setText("<html><div style='text-align:center; color:white; font-size:30px; font-weight:bold;'>Ingredients</div></html>");
        ingredientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topMargin.add(ingredientsLabel, BorderLayout.CENTER);

        tableModel = new IngredientTableModel();
        ingredientTable.setModel(tableModel);

        nameField.setText("");
        costField.setText("");
        perKgRadioButton.setSelected(false);
        perLiterRadioButton.setSelected(false);
        perPieceRadioButton.setSelected(false);

        ingredientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = ingredientTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    Ingredient selectedIngredient = tableModel.getIngredientAt(row);
                    updateFields(selectedIngredient);
                }
            }
        });

        saveAsNewIngredientButton.addActionListener(e -> {
            String validationError = validateInput();
            if (!validationError.isEmpty()) {
                JOptionPane.showMessageDialog(this, validationError, "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Ingredient newIngredient = createIngredientFromInput();
                mainApp.addIngredient(newIngredient);
                JOptionPane.showMessageDialog(this, "Ingredient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshTable(mainApp);
            }
        });

        updateIngredientButton.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if (selectedRow != -1) {
                String validationError = validateInput();
                if (!validationError.isEmpty()) {
                    JOptionPane.showMessageDialog(this, validationError, "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Ingredient ingredient = tableModel.getIngredientAt(selectedRow);
                    String name = nameField.getText();
                    int price = Integer.parseInt(costField.getText());
                    String unit = getSelectedUnit();
                    ingredient.setName(name);
                    ingredient.setCost(price);
                    ingredient.setUnit(unit);
                    mainApp.updateIngredient(ingredient);
                    JOptionPane.showMessageDialog(this, "Ingredient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(mainApp);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an ingredient to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteIngredientButton.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if (selectedRow >= 0) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this ingredient?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    Ingredient ingredient = tableModel.getIngredientAt(selectedRow);
                    mainApp.deleteIngredient(ingredient);
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Ingredient deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an ingredient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        homeButton.addActionListener(e -> {
            mainApp.showHomeScreen();
        });

    }

    private void updateFields(Ingredient ingredient) {
        nameField.setText(ingredient.getName());
        costField.setText(String.valueOf(ingredient.getCost()));
        setUnitRadioButton(ingredient.getUnit());
    }

    public void clearFields(){
        nameField.setText("");
        costField.setText("");
        perKgRadioButton.setSelected(false);
        perLiterRadioButton.setSelected(false);
        perPieceRadioButton.setSelected(false);
        ingredientTable.clearSelection();
    }

    private void setUnitRadioButton(String unit) {
        switch (unit) {
            case "kg": perKgRadioButton.setSelected(true); break;
            case "liters": perLiterRadioButton.setSelected(true); break;
            case "pieces": perPieceRadioButton.setSelected(true); break;
        }
    }

    private Ingredient createIngredientFromInput() {
        String name = nameField.getText();
        int price = Integer.parseInt(costField.getText());
        String unit = getSelectedUnit();
        return mainApp.createIngredient(name, price, unit);
    }

    private String getSelectedUnit() {
        if (perKgRadioButton.isSelected()) {
            return "kg";
        } else if (perLiterRadioButton.isSelected()) {
            return "liter";
        } else if (perPieceRadioButton.isSelected()){
            return "piece";
        }else{
            return "";
        }
    }

    private String validateInput() {
        String name = nameField.getText().trim();
        String costStr = costField.getText().trim();
        if (name.isEmpty()) {
            return "Name cannot be empty.";
        }
        try {
            double cost = Double.parseDouble(costStr);
            if (cost < 0) {
                return "Cost cannot be negative.";
            }
        } catch (NumberFormatException e) {
            return "Cost must be a valid number.";
        }
        if (getSelectedUnit().isEmpty()) {
            return "You must select a unit.";
        }
        return "";
    }

    public void refreshTable(Main mainApp) {
        tableModel.setIngredients(mainApp.getIngredients());
    }

    private void initializeUI() {
        setTitle("Ingredient Editor");
        setContentPane(ingredientPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
