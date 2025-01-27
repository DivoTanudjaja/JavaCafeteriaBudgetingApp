import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RecipeEditorIngredientTableModel extends AbstractTableModel {
    private List<Ingredient> displayedIngredients;
    private List<Double> displayedQuantities;
    private final String[] columnNames = {"Ingredient", "Quantity", "Unit"};

    public RecipeEditorIngredientTableModel() {
        this.displayedIngredients = new ArrayList<>();
        this.displayedQuantities = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return displayedIngredients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return displayedIngredients.get(rowIndex).getName();
            case 1:
                return displayedQuantities.get(rowIndex);
            case 2:
                return displayedIngredients.get(rowIndex).getUnit();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setIngredients(List<Ingredient> ingredients, List<Double> quantities) {
        this.displayedIngredients = new ArrayList<>(ingredients.size());
        this.displayedQuantities = new ArrayList<>(quantities.size());

        for (Ingredient ingredient : ingredients) {
            this.displayedIngredients.add(new Ingredient(ingredient.getId(), ingredient.getName(), ingredient.getCost(), ingredient.getUnit()));
        }
        this.displayedQuantities.addAll(new ArrayList<>(quantities));

        fireTableDataChanged();
    }

    public void addIngredient(Ingredient ingredient, double quantity) {
        displayedIngredients.add(new Ingredient(ingredient.getId(), ingredient.getName(), ingredient.getCost(), ingredient.getUnit()));
        displayedQuantities.add(quantity);
        fireTableDataChanged();
    }

    public void updateIngredient(int index, Ingredient ingredient, double quantity) {
        if (index >= 0 && index < displayedIngredients.size()) {
            displayedIngredients.set(index, ingredient);
            displayedQuantities.set(index, quantity);
            fireTableDataChanged();
        }
    }

    public void deleteIngredient(int index) {
        if (index >= 0 && index < displayedIngredients.size()) {
            displayedIngredients.remove(index);
            displayedQuantities.remove(index);
            fireTableDataChanged();
        }
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> copy = new ArrayList<>();
        for (Ingredient ingredient : this.displayedIngredients) {
            copy.add(new Ingredient(ingredient.getId(), ingredient.getName(), ingredient.getCost(), ingredient.getUnit()));
        }
        return copy;
    }

    public List<Double> getQuantities() {
        return new ArrayList<>(displayedQuantities);
    }

    public void clear() {
        displayedIngredients.clear();
        displayedQuantities.clear();
        fireTableDataChanged();
    }
}