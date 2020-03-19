package testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @ParameterizedTest
    @CsvSource({"Kolejowa, 55", "Polna, 12", "Fabryczna, 22"})
    void addressesShouldNotBeEmptyAndHaveProperNames(String street, int number){
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(number, greaterThanOrEqualTo(12));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adresses.csv")
    void addressesFromFileShouldNotBeEmptyAndHaveProperNames(String street, int number){
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(number, greaterThanOrEqualTo(12));
    }


}