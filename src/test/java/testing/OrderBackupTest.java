package testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderBackupTest {
    private static   OrderBackup orderBackup;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @Test
    void backupOrderWithOneMEal() throws IOException {
        //given
        Meal meal = new Meal(15,"Pizza");
        Order order = new Order();
        order.addMealToOrder(meal);

        //when
        orderBackup.backupOrder(order);

        //then
        System.out.println("Zapisano zamowienie do pliku");
    }


    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }


}