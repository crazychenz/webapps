/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework3.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework3.Main;
import org.junit.Test;

public class MainTest {

    @Test
    public void productTests() {
        Main main = new Main();

        assertEquals(18, main.product(3, 6));
        assertEquals(-18, main.product(-3, 6));
        assertEquals(-18, main.product(3, -6));
        assertEquals(18, main.product(-3, -6));
    }

    @Test(expected = ArithmeticException.class)
    public void productPositiveOverflowTest() {
        Main main = new Main();
        main.product(2147483647, 2147483647);
    }

    @Test(expected = ArithmeticException.class)
    public void productNegativeOverflowTest() {
        Main main = new Main();
        main.product(-2147483648, -2147483648);
    }

    @Test(expected = ArithmeticException.class)
    public void productUnderflowTest() {
        Main main = new Main();
        main.product(-2147483648, 2147483647);
    }
}
