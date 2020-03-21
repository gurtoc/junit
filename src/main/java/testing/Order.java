package testing;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Meal> mealList = new ArrayList<>();

    public void addMealToOrder(Meal meal) {
        this.mealList.add(meal);
    }

    public void removeMealFromOrder(Meal meal) {
        this.mealList.remove(meal);
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void cancel() {
        this.mealList.clear();
    }

    @Override
    public String toString() {
        return "Order{" +
                "mealList=" + mealList +
                '}';
    }


    int totalPrice() {

        int sum = this.mealList.stream().mapToInt(meal -> meal.getPrice()).sum();

        if (sum < 0) {
            throw new IllegalStateException("Price limit exceeded");
        } else {
            return sum;
        }
    }
}
