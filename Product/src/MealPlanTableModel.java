import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class MealPlanTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Name", "Main Dish", "Side Dish", "Dessert"};
    private final List<MealPlan> displayedMealPlans;

    public MealPlanTableModel(List<MealPlan> mealPlans) {
        this.displayedMealPlans = new ArrayList<>(mealPlans);
    }

    @Override
    public int getRowCount() {
        return displayedMealPlans == null ? 0 : displayedMealPlans.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            return null;
        }

        MealPlan mealPlan = displayedMealPlans.get(rowIndex);
        switch (columnIndex) {
            case 0: return mealPlan.getId();
            case 1: return mealPlan.getName();
            case 2: return mealPlan.getMainDish() != null ? mealPlan.getMainDish().getName() : "";
            case 3: return mealPlan.getSideDish() != null ? mealPlan.getSideDish().getName() : "";
            case 4: return mealPlan.getDessert() != null ? mealPlan.getDessert().getName() : "";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public MealPlan getMealPlanAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < displayedMealPlans.size()) {
            return displayedMealPlans.get(rowIndex);
        }
        return null;
    }

    public void setDisplayedMealPlans(List<MealPlan> newMealPlans) {
        this.displayedMealPlans.clear();
        this.displayedMealPlans.addAll(newMealPlans);
        fireTableDataChanged();
    }
}
