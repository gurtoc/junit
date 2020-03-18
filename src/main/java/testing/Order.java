package testing;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Meal> mealList = new ArrayList<>();

    public void addMealToOrder(Meal meal){
        this.mealList.add(meal);
    }

    public void removeMealFromOrder(Meal meal){
        this.mealList.remove(meal);
    }

    public List<Meal> getMealList() {
        return mealList;
    }
}
