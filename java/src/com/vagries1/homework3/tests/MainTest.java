/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework3.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework3.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

public class MainTest {

    private static final Logger logger = LogManager.getLogger(MainTest.class);

    static {
        // Boilerplate logger enable/disable code.
        if (!System.getenv().containsKey("ENABLE_LOGGING")) {
            Configurator.setRootLevel(Level.OFF);
        } else if (System.getenv().get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }
    }

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
