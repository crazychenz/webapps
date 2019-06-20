/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework3.Main;
import org.junit.Test;

public class MainTest {

    @Test
    public void productTests() {
        Main main = new Main();

        // Product of 2 positives
        assertEquals(18, main.product(3, 6));
        // Product of left negative and right positive
        assertEquals(-18, main.product(-3, 6));
        // Product of left positive and right negative
        assertEquals(-18, main.product(3, -6));
        // Product of 2 negatives
        assertEquals(18, main.product(-3, -6));
    }

    @Test
    public void intToStringTests() {
        Main main = new Main();

        // Negative representation
        assertEquals("(54)", main.intToString(-54));
        // Positive representation
        assertEquals("543", main.intToString(543));
        // Zero representation
        assertEquals("0", main.intToString(0));
    }

    @Test
    public void productStringFromArgvTests() {

        String[] arg_set1 = {"-3", "6"};
        String[] arg_set2 = {"-3", "-6"};
        String[] arg_set3 = {"3", "6"};
        // 1 Negative and 1 positive
        assertEquals("(18)", Main.productStringFromArgv(arg_set1));
        // 2 negatives
        assertEquals("18", Main.productStringFromArgv(arg_set2));
        // 2 positives
        assertEquals("18", Main.productStringFromArgv(arg_set3));
    }

    @Test
    public void exceptionTests() {
        Main main = new Main();

        // Check for positive overflow
        try {
            main.product(2147483647, 2147483647);
            fail();
        } catch (ArithmeticException e) {
            assertEquals("integer overflow", e.getMessage());
        }

        // Check for negative overflow
        try {
            main.product(-2147483648, -2147483648);
            fail();
        } catch (ArithmeticException e) {
            assertEquals("integer overflow", e.getMessage());
        }

        // Check for underflow
        try {
            main.product(-2147483648, 2147483647);
            fail();
        } catch (ArithmeticException e) {
            assertEquals("integer overflow", e.getMessage());
        }
    }
}
