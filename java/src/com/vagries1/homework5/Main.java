/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.gooey.Gooey;
import java.io.File;
import java.io.InputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Main entry point for homework5.
 *
 * @author Vincent Agriesti
 */
public class Main {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static final String DEFAULT_CONFIG =
            "/com/vagries1/homework5/bindings/bhcConfigDefault.xml";

    /** Prints to STDOUT the brief usage documentation from CLI. */
    public static void usage() {
        System.out.printf("Usage: Main [--config path/to/bhcConfig.xml]\n\n");
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

        String configFile = null;

        // Boilerplate logger enable/disable code.
        if (!System.getenv().containsKey("ENABLE_LOGGING")) {
            Configurator.setRootLevel(Level.OFF);
        } else if (System.getenv().get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }

        logger.info("Starting main entry point.");

        // Check for user provided configuration
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--config")) {
                configFile = args[i + 1];
                break;
            }
        }

        try {
            BhcConfig config;
            BhcEstimator estimator;

            if (configFile != null) {
                config = BhcConfig.unmarshallFile(new File(configFile));
            } else {
                InputStream in;
                in = new Main().getClass().getResourceAsStream(DEFAULT_CONFIG);
                config = BhcConfig.unmarshallStream(in);
            }

            estimator = new BhcEstimator(config);
            Gooey.deferredGui(estimator);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: You did something wrong. Maybe read the javadocs? ;).");
        }
    }
}
