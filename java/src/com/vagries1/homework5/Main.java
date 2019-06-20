/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.vagries1.homework5.bindings.BhcConfig;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/** Main entry point for homework4. */
public class Main {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /** Prints to STDOUT the brief usage documentation from CLI. */
    public static void usage() {
        System.out.printf("Usage: Main\n\n");
    }

    public static BhcConfig unmarshallConfig(File xmlFile) {
        JAXBContext jaxbContext;
        BhcConfig config = null;

        try {
            jaxbContext = JAXBContext.newInstance(BhcConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            config = (BhcConfig) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return config;
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

        logger.info("Starting main entry point.");

        try {
            BhcConfig config;
            config = Main.unmarshallConfig(new File("bhcConfig.xml"));
            Gooey.deferredGui(config);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: You did something wrong. Maybe read the javadocs? ;).");
        }
    }
}
