package testing;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice(){
        //given
        Meal meal = new Meal(15);

        //when
        int discounterPrice = meal.getDiscountedPrice(2);

        //then
        assertEquals(13,discounterPrice);
        assertThat(discounterPrice,equalTo(13));

    }

    @Test
    void referenceToTheSameObjectShouldBeEqual(){
        //given
        Meal meal = new Meal(15);
        Meal meal1 = meal;

        //then
        assertSame(meal,meal1);
        assertThat(meal,sameInstance(meal1));
    }

    @Test
    void referenceToTheSameObjectShouldNotBeEqual(){
        //given
        Meal meal = new Meal(20);
        Meal meal1 = new Meal(11);

        //then
        assertNotSame(meal,meal1);
        assertThat(meal,not(sameInstance(meal1)));
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame(){
        //given
        Meal meal = new Meal(10,"Pizza");
        Meal meal1 = new Meal(10, "Pizza");

        //then
        assertEquals(meal,meal1);
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanPrice(){
        //given
        Meal meal = new Meal(20, "Shoarma");

        //when
        //then
        assertThrows(IllegalArgumentException.class, ()->meal.getDiscountedPrice(50));

    }
}