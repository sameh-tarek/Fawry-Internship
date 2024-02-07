package org.example.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountManagerTest {

    AccountManager accountManager = new AccountManagerImpl();


    @Test
    void test1() {
        // Arrange
        int amount = 50;
        Customer customer = new Customer();
        customer.setBalance(80);

        // Act
        String result = accountManager.withdraw(customer, amount);

        Assertions.assertEquals("success", result);
        Assertions.assertEquals(30, customer.getBalance());
    }

}
