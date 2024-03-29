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
import java.io.PrintStream;
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

    protected static final String DEFAULT_CONFIG =
            "/com/vagries1/homework5/bindings/bhcConfigDefault.xml";

    /** Prints to STDOUT the brief usage documentation from CLI. */
    public static void showUsage() {
        PrintStream out = System.out;
        out.println("Usage: java -jar en60581.jar [OPTIONS...]");
        out.println("BHC Hiking Calculator.\n");
        out.println("Arguments:");
        out.println("   --help     This help message.");
        out.println("   --config   Path to bhcConfig.xml");
        out.println("\nVerbosity Arguments:");
        out.println("   --enable-logging <path>  Enable version logging.");
        out.println("");
    }

    protected static void handleLoggerControl(String[] args) {
        // Assume no logs
        boolean enableLogging = false;

        // Environment variable logger enable/disable code.
        if (System.getenv().containsKey("ENABLE_LOGGING")) {
            if (System.getenv().get("ENABLE_LOGGING").compareTo("1") == 0) {
                enableLogging = true;
            }
        }

        // Command line logger enable/disable code.
        // Note: CLI takes priority over environment variable.
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--enable-logging")) {
                enableLogging = true;
                break;
            }
        }

        if (enableLogging == false) {
            Configurator.setRootLevel(Level.OFF);
        }
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

        handleLoggerControl(args);

        // Check for user provided configuration
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--help")) {
                showUsage();
                return;
            }
        }

        // Check for user provided configuration
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--config")) {
                configFile = args[i + 1];
                break;
            }
        }

        logger.info("Starting main entry point.");

        try {
            BhcConfig config;
            BhcEstimator estimator = null;

            if (configFile != null) {
                config = BhcConfig.unmarshallFile(new File(configFile));
            } else {
                InputStream in;
                in = new Main().getClass().getResourceAsStream(DEFAULT_CONFIG);
                config = BhcConfig.unmarshallStream(in);
            }

            if (config == null) {
                System.out.println("ERROR: Failed to load bhcConfig.xml data.");
                return;
            }

            try {
                estimator = new BhcEstimator(config);
            } catch (Exception e) {
                String msg = "ERROR: Failed to load BhcEstimator. (" + e.getMessage() + ")";
                System.out.println(msg);
                logger.error(msg);
                logger.debug("", e);
                return;
            }

            Gooey.deferredGui(estimator);
        } catch (Exception e) {
            // Catch all
            String msg = "ERROR: " + e.getMessage();
            System.out.println(msg);
            logger.error(msg);
            logger.debug("", e);
        }
    }
}
