package com.bjss.cart;

import com.bjss.cart.exception.ShoppingCartException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rabia on 03/12/2017.
 */
public class ShoppingCartExceptionTest {
    @Test
    public void testExceptionMessage() {
        try {
            throw new ShoppingCartException("Exception Gen");
        } catch (ShoppingCartException exc) {
            assertEquals(exc.getMessage(), "Exception Gen");
        }
    }
}
