package testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void newAccountShouldBeNotActivated(){
        Account account = new Account();

        assertFalse(account.isActive());
    }

    @Test
    public void newAccountShouldBeActivated(){
        Account account = new Account();

        assertFalse(account.isActive());
        account.activate();
        assertTrue(account.isActive());
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefDeliveryAdressSet(){
        //given
        Account account = new Account();

        //when
        Address address = account.getDefaultDeliveryAdress();

        //then
        assertNull(address);
    }

    @Test
    void newlyCreatedAccountShouldHaveDeliveryAdressSet(){
        //given
        Address address = new Address("Kolejowa","86");
        Account account = new Account();
        account.setDefaultDeliveryAdress(address);

        //when
        Address defaultAdress = account.getDefaultDeliveryAdress();

        //then
        assertNotNull(defaultAdress);




    }

}