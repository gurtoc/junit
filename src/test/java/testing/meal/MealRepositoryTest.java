package testing.meal;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.meal.Meal;
import testing.meal.MealRepository;


import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MealRepositoryTest {

    MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {
        //given
        Meal meal = new Meal(25, "Sushi");

        //when
        mealRepository.add(meal);

        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {
        //given
        Meal meal = new Meal(3, "Frytki");

        //when
        mealRepository.delete(meal);

        //then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));

    }

    @Test
    void shouldBeAbleToFindMealByExactName() {
        //given
        Meal meal = new Meal(25, "Frytki");
        Meal meal2 = new Meal(25, "Fryt");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("Frytki", true);

        //then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByStartingLetter() {
        //given
        Meal meal = new Meal(25, "Frytki");
        Meal meal2 = new Meal(25, "Fryt");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("F", false);

        //then
        assertThat(result.size(), is(2));
    }

    @Test
    void shouldBeAbleToFindMealByPrice() {
        //given
        Meal meal = new Meal(25, "Frytki");
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.findByPrice(25);

        //then
        assertThat(result.size(), is(1));
    }
}
