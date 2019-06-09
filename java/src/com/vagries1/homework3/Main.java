/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework3;

// import static java.lang.Math.abs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Main entry point for homework3.
 *
 * @author Vincent Agriesti
 */
public class Main {

    /** Log4j logger object instance for this class. */
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /**
     * Calculate the product of two integers.
     *
     * @param leftOperand The first operand of the product.
     * @param rightOperand The second operand of the product.
     * @return the product of the two operands.
     */
    public int product(int leftOperand, int rightOperand) {
        int result = 0;

        LOGGER.printf(Level.DEBUG, "Calculated %d * %d is ", leftOperand, rightOperand);
        // We use multiplyExact to catch potential overflows. (This requires JRE >= 1.8)
        result = Math.multiplyExact(leftOperand, rightOperand);
        LOGGER.printf(Level.DEBUG, "%d\n", result);

        return result;
    }

    /** Prints to STDOUT the brief usage documentation from CLI. */
    public static void usage() {
        System.out.printf("Usage: Main <leftOperand> <rightOperand>\n\n");
        System.out.printf("leftOperand and rightOperand must be a decimal integer\n");
        System.out.printf("  between -2147483648 and 2147483647. Please note that\n");
        System.out.printf("  for correct operation, the product of the leftOperand\n");
        System.out.printf("  and rightOperand should also fall between the minimum\n");
        System.out.printf("  and maximum values mentioned.\n");
    }

    /**
     * Entry point method from command line.
     *
     * <p>Currently this method will accept two integer arguments in decimal format. They may be
     * positive or negative between Integer.MAX_VALUE (2^31 - 1) and Integer.MIN_VALUE (-2^31).
     *
     * <p>If an environment variable ENABLE_LOGGING has been set to the value 1, the log4j logging
     * will be enabled.
     *
     * <p>Note: There is currently little to no input validation on the input arguments.
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

        Main obj = new Main();
        try {
            int arg1 = Integer.parseInt(args[0]);
            int arg2 = Integer.parseInt(args[1]);
            int result = obj.product(arg1, arg2);
            if (result < 0) {
                System.out.printf("(%d)\n", Math.abs(result));
            } else {
                System.out.printf("%d\n", result);
            }
        } catch (NumberFormatException e) {
            System.err.printf("Error: Invalid number format. (%s)\n\n", e.getMessage());
            Main.usage();
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Error: Too few arguments.\n\n");
            Main.usage();
            return;
        } catch (ArithmeticException e) {
            System.err.printf("Error: Integer overflow detected. (%s)\n\n", e.getMessage());
            Main.usage();
            return;
        }
    }
}
