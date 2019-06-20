/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** Class that facilitates JUnit tests for package. */
@RunWith(Suite.class)
@SuiteClasses({
    AircraftTest.class,
    DestroyerTest.class,
    MainTest.class,
    P3Test.class,
    ShipTest.class,
    SubmarineTest.class
})
public class TstRunner {
    static {
        // Boilerplate logger enable/disable code.
        if (!System.getenv().containsKey("ENABLE_LOGGING")) {
            Configurator.setRootLevel(Level.OFF);
        } else if (System.getenv().get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }
    }
}
