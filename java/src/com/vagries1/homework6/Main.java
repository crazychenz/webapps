/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework6;

import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.gooey.Gooey;
import java.io.File;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main entry point for homework5.
 *
 * @author Vincent Agriesti
 */
public class Main extends com.vagries1.homework5.Main {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Main.class);

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
            BhcEstimatorRemote estimator = null;

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
                estimator = new BhcEstimatorRemote(config);
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
