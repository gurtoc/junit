package testing.order;

import testing.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private OrderStatus orderStatus;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
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
