/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework3;

import static java.lang.Math.abs;

import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {

    private static final Logger LOGGER = 
        LogManager.getLogger(Main.class);

    public int product(int arg1, int arg2) {
        int result = 0;

        result = arg1 * arg2;
        LOGGER.printf(
            Level.DEBUG, 
            "Calculated %d * %d is %d\n", 
            arg1, 
            arg2, 
            result);

        return result;
    }

    public static void main(String[] args) {

        Map<String, String> env = System.getenv();
        if (!env.containsKey("ENABLE_LOGGING") || 
            env.get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }

        LOGGER.info("Starting main entry point.");

        Main obj = new Main();
        int arg1 = Integer.parseInt(args[0]);
        int arg2 = Integer.parseInt(args[1]);
        int result = obj.product(arg1, arg2);
        if (result < 0) {
            System.out.printf("(%d)\n", abs(result));
        } else {
            System.out.printf("%d\n", result);
        }
    }
}
