package org.example.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class AccountManagerTest {

    private AccountManager accountManager;
    private Customer customer;

    @BeforeEach
    public void setUp(){
        accountManager = new AccountManagerImpl();
        customer = new Customer();
    }

    @Test
    public void depositShouldAddAmountToBalance(){
        // Arrange
        int amount = 50;
        customer.setBalance(50);

        // Act
        accountManager.deposit(customer, amount);

        // Assert
        assertThat(customer.getBalance()).isEqualTo(100);
    }

    @Test
    public void withdrawShouldSucceedIfPositiveBalance() {
        // Arrange
        int amount = 50;
        customer.setBalance(80);

        // Act
        String result = accountManager.withdraw(customer, amount);

        assertThat(result).isEqualTo("success");
        assertThat(customer.getBalance()).isEqualTo(30);
    }

    @Test
    public void withdrawShouldFailIfInsufficientBalanceAndNoCreditAllowed(){
        // Arrange
        int amount = 50;
        customer.setBalance(40);
        customer.setCreditAllowed(false);

        // Act
        String result = accountManager.withdraw(customer, amount);

        //Assert
        assertThat(result).isEqualTo("insufficient account balance");
        assertThat(customer.getBalance()).isEqualTo(40);
    }

    @Test
    public void withdrawShouldFailIfNegativeBalanceGreaterThanMaxCreditAndNotVipAndCreditAllowed(){
        // Arrange
        int amount = 6000;
        customer.setBalance(4000);
        customer.setVip(false);
        customer.setCreditAllowed(true);

        // Act
        String result = accountManager.withdraw(customer, amount);

        // Assert
        assertThat(result).isEqualTo("maximum credit exceeded");
        assertThat(customer.getBalance()).isEqualTo(4000);
    }

    @Test
    public void withdrawShouldSucceedIfNegativeBalanceGreaterThanMaxCreditAndVipAndCreditAllowed(){
        // Arrange
        int amount = 6000;
        customer.setBalance(4000);
        customer.setVip(true);
        customer.setCreditAllowed(true);

        // Act
        String result = accountManager.withdraw(customer, amount);

        // Assert
        assertThat(result).isEqualTo("success");
        assertThat(customer.getBalance()).isEqualTo(-2000);
    }

    @Test
    public void withdrawShouldSucceedIfNegativeBalanceLessThanMaxCreditAndVipAndCreditAllowed(){
        // Arrange
        int amount = 6000;
        customer.setBalance(5500);
        customer.setCreditAllowed(true);
        customer.setVip(true);

        // Act
        String result = accountManager.withdraw(customer, amount);

        // Assert
        assertThat(result).isEqualTo("success");
        assertThat(customer.getBalance()).isEqualTo(-500);
    }

}
