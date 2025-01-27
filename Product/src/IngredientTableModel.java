import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class IngredientTableModel extends AbstractTableModel {
    private final List<Ingredient> ingredients;
    private final String[] columnNames = {"ID", "Name", "Cost (Rp)", "Unit"};

    public IngredientTableModel() {
        ingredients = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return ingredients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ingredient ingredient = ingredients.get(rowIndex);
        switch (columnIndex) {
            case 0: return ingredient.getId();
            case 1: return ingredient.getName();
            case 2: return ingredient.getCost();
            case 3: return "per " + ingredient.getUnit();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Ingredient getIngredientAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < ingredients.size()) {
            return ingredients.get(rowIndex);
        } else {
            return null;
        }
    }

    public void setIngredients(List<Ingredient> newIngredients) {
        ingredients.clear();
        ingredients.addAll(newIngredients);
        fireTableDataChanged();
    }
}
