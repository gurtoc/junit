package testing.meal;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import testing.extensions.IAExceptionIgnoreExtension;
import testing.meal.Meal;
import testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class MealTest {

    @Spy
    private Meal mealSpy;

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(15);

        //when
        int discounterPrice = meal.getDiscountedPrice(2);

        //then
        assertEquals(13, discounterPrice);
        assertThat(discounterPrice, equalTo(13));

    }

    @Test
    void referenceToTheSameObjectShouldBeEqual() {
        //given
        Meal meal = new Meal(15);
        Meal meal1 = meal;

        //then
        assertSame(meal, meal1);
        assertThat(meal, sameInstance(meal1));
    }

    @Test
    void referenceToTheSameObjectShouldNotBeEqual() {
        //given
        Meal meal = new Meal(20);
        Meal meal1 = new Meal(11);

        //then
        assertNotSame(meal, meal1);
        assertThat(meal, not(sameInstance(meal1)));
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal1 = new Meal(10, "Pizza");

        //then
        assertEquals(meal, meal1);
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanPrice() {
        //given
        Meal meal = new Meal(20, "Shoarma");

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(50));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 8, 18})
    void mealPriceShouldBeLowerThanTwenty(int price) {
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealWithNameAndPrice")
    void burgersShouldHaveCorrectNameandPrices(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, lessThan(20));
    }

    private static Stream<Arguments> createMealWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("CheesHamburger", 15)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNameShouldEndWithCake(String name) {
        assertThat(name, endsWith("Cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeList = Arrays.asList("FruitCake", "CarrotCake", "CupCake");
        return cakeList.stream();
    }

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 8, 9})
    void mealPriceShouldBeLowerThan10(int price) {
        if (price > 5) {
            throw new IllegalArgumentException();
        }

        assertThat(price, lessThan(20));
    }


    @Tag("fries")
    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10, 2, "Hamburger"));
        order.addMealToOrder(new Meal(7, 4, "Fries"));
        order.addMealToOrder(new Meal(22, 3, "Pizza"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for(int i = 0; i < order.getMealList().size(); i++) {

            int price = order.getMealList().get(i).getPrice();
            int quantity = order.getMealList().get(i).getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(67));
            };

            String name = "Test name: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);
        }

        return dynamicTests;
    }

    @Test
    void testMealSumPrice() {

        //given
        Meal meal = mock(Meal.class);

        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);

        given(meal.sumPrice()).willCallRealMethod();

        //when
        int result = meal.sumPrice();

        //then
        assertThat(result, equalTo(45));

    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void testMealSumPriceWithSpy() {

        //given
        given(mealSpy.getPrice()).willReturn(15);
        given(mealSpy.getQuantity()).willReturn(3);

        //when
        int result = mealSpy.sumPrice();

        //then
        then(mealSpy).should().getPrice();
        then(mealSpy).should().getQuantity();
        assertThat(result, equalTo(45));

    }


    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }
}