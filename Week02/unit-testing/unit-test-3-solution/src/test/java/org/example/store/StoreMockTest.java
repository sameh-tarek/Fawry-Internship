package org.example.store;

import org.example.account.AccountManager;
import org.example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class StoreMockTest {



    @Test
    void buyProductShouldWithdrawAmountFromCustomerAndDecreaseStock() {
        // Arrange
        Product product = new Product();
        product.setQuantity(4);
        product.setPrice(50);

        Customer customer = new Customer();

        AccountManager accountManager = mock(AccountManager.class);
        when(accountManager.withdraw(any(), anyInt())).thenReturn("success");

        Store store = new StoreImpl(accountManager);

        // Act
        store.buy(product, customer);

        // Assert
        Assertions.assertEquals(3, product.getQuantity());
    }

    @Test
    void buyProductShouldGivePaymentError() {
        // Arrange
        Product product = new Product();
        product.setQuantity(4);
        product.setPrice(50);

        Customer customer = new Customer();

        AccountManager accountManager = mock(AccountManager.class);
        when(accountManager.withdraw(any(), anyInt())).thenReturn("failed");

        Store store = new StoreImpl(accountManager);

        // Act
        try {
            store.buy(product, customer);
            Assertions.fail("Should throw exception");
        } catch (RuntimeException ex) {
            Assertions.assertEquals("Payment failure: failed", ex.getMessage());
        }

        // Assert
        Assertions.assertEquals(4, product.getQuantity());
    }


}
