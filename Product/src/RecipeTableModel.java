import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel {
    private final List<Recipe> displayedRecipes;
    private final String[] columnNames = {"ID", "Name", "Type", "Servings"};

    public RecipeTableModel() {
        displayedRecipes = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return displayedRecipes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recipe recipe = displayedRecipes.get(rowIndex);
        switch (columnIndex) {
            case 0: return recipe.getId();
            case 1: return recipe.getName();
            case 2: return recipe.getType();
            case 3: return recipe.getServings();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Recipe getRecipeAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < displayedRecipes.size()) {
            return displayedRecipes.get(rowIndex);
        } else {
            return null;
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        this.displayedRecipes.clear();
        this.displayedRecipes.addAll(recipes);
        fireTableDataChanged();
    }
}
