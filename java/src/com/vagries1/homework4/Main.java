/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

import java.util.ArrayList;
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

    /** Main action of this application to satisfy the requirements of Homework #4. */
    public void doStuff() {
        ArrayList<Destroyer> destroyers;
        ArrayList<Submarine> submarines;
        ArrayList<P3> p3s;
        ArrayList<Ship> ships;
        ArrayList<Contact> contacts;

        // 1. Create 2 Destroyers
        // 4. Make a collection of Destroyers
        destroyers = new ArrayList<Destroyer>();
        destroyers.add(new Destroyer());
        destroyers.add(new Destroyer());

        // 2. Create 2 Submarines
        // 5. Make a collection of Submarines
        submarines = new ArrayList<Submarine>();
        submarines.add(new Submarine());
        submarines.add(new Submarine());

        // Also, in your test class, you should exercise your execption
        // handling case for the submarine by setting the number of
        // torpedoes in one of your submarine classes to the string "Foo".
        System.out.println("Attempting to set Submarine.numberTorpedos to \"Foo\".");
        try {
            Submarine foo = new Submarine();
            foo.setNumberTorpedos("Foo");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 3. Create 2 P3s
        p3s = new ArrayList<P3>();
        p3s.add(new P3());
        p3s.add(new P3());

        // 6. Make a collection that hols all Ships
        ships = new ArrayList<Ship>();
        for (Ship ship : destroyers) {
            ships.add(ship);
        }
        for (Ship ship : submarines) {
            ships.add(ship);
        }

        // 7. Make a collection that holds all Contacts
        contacts = new ArrayList<Contact>();
        for (Contact contact : ships) {
            contacts.add(contact);
        }
        for (Contact contact : p3s) {
            contacts.add(contact);
        }

        System.out.println("\nAll contacts' toString() output:");
        for (int i = 0; i < contacts.size(); ++i) {
            System.out.println((i + 1) + ". " + contacts.get(i).toString());
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

        // Boilerplate logger enable/disable code.
        if (!System.getenv().containsKey("ENABLE_LOGGING")) {
            Configurator.setRootLevel(Level.OFF);
        } else if (System.getenv().get("ENABLE_LOGGING").compareTo("1") != 0) {
            Configurator.setRootLevel(Level.OFF);
        }

        logger.info("Starting main entry point.");

        try {
            new Main().doStuff();
        } catch (Exception e) {
            System.out.println("Error: You did something wrong. Maybe read the javadocs? ;).");
        }
    }
}
