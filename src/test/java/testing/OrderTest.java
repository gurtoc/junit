package testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("BeforeEach test");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("AfterEach test");
        order.cancel();
    }

    @Test
    void testArrayEquals() {
        //given
        int[] liczby = {1, 2, 3};
        int[] liczby2 = {1, 2, 3};

        //then
        assertArrayEquals(liczby, liczby2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        //given
        Order order = new Order();

        //then
        assertThat(order.getMealList(), empty());
        assertThat(order.getMealList().size(), equalTo(0));
        assertThat(order.getMealList(), hasSize(0));
        assertThat(order.getMealList(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(25, "Pizza");
        Meal meal1 = new Meal(10, "Kaszanka");


        //when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal1);

        //then
        assertThat(order.getMealList(), hasSize(2));
        assertThat(order.getMealList(), hasItem(meal1));
        assertThat(order.getMealList().get(0).getPrice(), equalTo(25));
    }

    @Test
    void removeMealToOrderShouldDecreaseOrderSize() {
        //given
        Meal meal1 = new Meal(10, "Kaszanka");


        //when
        order.addMealToOrder(meal1);
        order.removeMealFromOrder(meal1);

        //then
        assertThat(order.getMealList(), hasSize(0));
        assertThat(order.getMealList(), not(contains(meal1)));
    }

    @Test
    void mealsShoudlBeInCorrectOrder() {
        //given
        Meal meal = new Meal(25, "Pizza");
        Meal meal1 = new Meal(10, "Kaszanka");


        //when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal1);

        //then
        assertThat(order.getMealList(), contains(meal, meal1));
        assertThat(order.getMealList(), containsInAnyOrder(meal1, meal));
    }

    @Test
    void testIfTwoMealListsAreTheSame() {
        //given
        Meal meal = new Meal(25, "Pizza");
        Meal meal1 = new Meal(10, "Kaszanka");

        //when
        List<Meal> meals = Arrays.asList(meal, meal1);
        List<Meal> meals2 = Arrays.asList(meal, meal1);

        assertThat(meals, is(meals2));
    }

}