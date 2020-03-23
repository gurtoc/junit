package testing.account;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository  {
    @Override
    public List<Account> getAllAccounts() {
        Address address = new Address("Kolejowa","86/15");
        Account account = new Account(address);

        Account account1 = new Account();

        Address address1 = new Address("Wakacujna","3a");
        Account account2 = new Account(address1);

        return Arrays.asList(account,account1,account2);
    }
}
