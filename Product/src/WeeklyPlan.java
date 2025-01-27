import java.io.Serializable;
import java.util.Date;

public class WeeklyPlan implements Serializable {
    private int id;
    private String name;
    private Recipe[][] weeklyRecipes;
    private Date dateAdded;
    private Date dateModified;
    private boolean[] daysEnabled = new boolean[5];

    public WeeklyPlan(int id, String name, Recipe[][] weeklyRecipes, boolean[] daysEnabled) {
        this.id = id;
        this.name = name;
        this.weeklyRecipes = weeklyRecipes; // Assume this is a 3x5 array
        this.daysEnabled = daysEnabled;
        this.dateAdded = new Date();
        this.dateModified = new Date();
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

    public void setWeeklyRecipes(Recipe[][] weeklyRecipes) {
        this.weeklyRecipes = weeklyRecipes;
    }

    public Recipe[][] getWeeklyRecipes() {
        return weeklyRecipes;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public boolean[] getDaysEnabled() {
        return daysEnabled;
    }

    public void setDayEnabled(int dayIndex, boolean isEnabled) {
        if (dayIndex >= 0 && dayIndex < daysEnabled.length) {
            daysEnabled[dayIndex] = isEnabled;
        }
    }

    public boolean isDayEnabled(int dayIndex) {
        if (dayIndex >= 0 && dayIndex < daysEnabled.length) {
            return daysEnabled[dayIndex];
        }
        return false;
    }
}