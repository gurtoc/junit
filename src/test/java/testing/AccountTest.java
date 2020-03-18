package testing;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void newAccountShouldBeNotActivated(){
        //given
        Account account = new Account();

        //then
        assertFalse(account.isActive());

        assertThat(account.isActive(),equalTo(false));
        assertThat(account.isActive(),is(false));
    }

    @Test
    public void newAccountShouldBeActivated(){
        //given
        Account account = new Account();

        assertFalse(account.isActive());
        account.activate();
        assertTrue(account.isActive());

        assertThat(account.isActive(),equalTo(true));
        assertThat(account.isActive(),is(equalTo(true)));
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefDeliveryAdressSet(){
        //given
        Account account = new Account();

        //when
        Address address = account.getDefaultDeliveryAdress();

        //then
        assertNull(address);
        assertThat(address,nullValue());

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
        assertThat(defaultAdress,is(notNullValue()));
    }

}