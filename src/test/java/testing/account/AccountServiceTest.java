package testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        //given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository =  mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));

    }

    @Test
    void getNoActiveAccounts() {
        //given
       // List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository =  mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getAllAccounts()).willReturn(Arrays.asList());

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(0));

    }


    public List<Account> prepareAccountData() {
        Address address = new Address("Kolejowa","86/15");
        Account account = new Account(address);

        Account account1 = new Account();

        Address address1 = new Address("Wakacujna","3a");
        Account account2 = new Account(address1);

        return Arrays.asList(account,account1,account2);
    }

    @Test
    void getAccountsByName() {
        //given
        // List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository =  mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getByName("dawid")).willReturn(Collections.singletonList("nowak"));

        //when
        List<String> accountName = accountService.findByName("dawid");

        //then
        assertThat(accountName, contains("nowak"));

    }



}
