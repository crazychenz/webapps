package com.vagries1.homework3.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework3.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class MainTest {

    private static final Logger logger = LogManager.getLogger(MainTest.class);

    @Test
    public void productTests() {
        Main main = new Main();

        assertEquals(18, main.product(3, 6));
        assertEquals(-18, main.product(-3, 6));
        assertEquals(-18, main.product(3, -6));
        assertEquals(18, main.product(-3, -6));
    }
}
