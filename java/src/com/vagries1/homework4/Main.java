/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Main entry point for homework4.
 *
 * @author Vincent Agriesti
 */
public class Main {

    /** Log4j logger object instance for this class. */
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /** Prints to STDOUT the brief usage documentation from CLI. */
    public static void usage() {
        System.out.printf("Usage: Main\n\n");
    }

    /**
     * Entry point method from command line.
     *
     * <p>If an environment variable ENABLE_LOGGING has been set to the value 1, the log4j logging
     * will be enabled.
     *
     * @param args Array of command line arguments.
     */
    public static void main(String[] args) {

        // Boilerplate logger enable/disable code.
        if (!System.getenv().containsKey("ENABLE_LOGGING")) {
            Configurator.setRootLevel(Level.OFF);
        } else if (System.getenv().get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }

        LOGGER.info("Starting main entry point.");

        // TODO: Do stuff
    }
}
