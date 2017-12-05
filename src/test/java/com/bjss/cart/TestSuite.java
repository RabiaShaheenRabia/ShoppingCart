package com.bjss.cart;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Rabia on 02/12/2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShoppingCartTest.class, //test case 1
        ShoppingCartExceptionTest.class
})
public class TestSuite {
}