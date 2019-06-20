/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

/** A Submarine that has torpedos. */
public class Submarine extends Ship {
    /** Number of torpedos on Submarine. */
    private int numberTorpedos;

    /** Minimal number of torpedos on Submarine. */
    public final int MIN_TORPEDOS = 0;

    /** Default constructor. Number of torpedos set to MIN_TORPEDOS (0). */
    public Submarine() {
        super();
        setNumberTorpedos(MIN_TORPEDOS);
    }

    /**
     * Explicit constructor of Submarine object.
     *
     * @param name Name of Submarine as a String. Must not be empty or null.
     * @param type Type of Submarine as a String. Must not be empty or null.
     * @param length Length of Submarine from end to end in meters. Must be between MIN_LENGTH and
     *     MAX_LENGTH.
     * @param speed The speed of Submarine in meters per second. Must be between MIN_SPEED and
     *     MAX_SPEED.
     * @param torpedos The number of torpedos currently on Submarine.
     */
    public Submarine(String name, String type, int length, int speed, int torpedos) {
        super(name, type, length, speed);
        setNumberTorpedos(torpedos);
    }

    /**
     * Fetch the number of torpedos currently on Submarine.
     *
     * @return The number of torpedos as int
     */
    public int getNumberTorpedos() {
        return numberTorpedos;
    }

    /**
     * Set the number of torpedos on Submarine.
     *
     * @param torpedos The number of missiles to set on Submarine as int. Must be between
     *     MIN_TORPEDOS and Integer.MAX_VALUE.
     */
    public void setNumberTorpedos(int torpedos) {
        if (torpedos < MIN_TORPEDOS) {
            throw new ArrayIndexOutOfBoundsException("torpedo count negative");
        }
        this.numberTorpedos = torpedos;
    }

    /**
     * Set the number of torpedos on Submarine.
     *
     * @param torpedos The number of torpedos to set on Submarine as String. Must be arabic numerals
     *     with radix 10.
     */
    public void setNumberTorpedos(String torpedos) {
        int numberTorpedos = Integer.parseInt(torpedos);
        setNumberTorpedos(numberTorpedos);
    }

    /** String representation of the Submarine object (in JSON format). */
    public String toString() {
        String fmt = "\"submarine\":{%s, \"torpedos\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberTorpedos());
        return repr;
    }
}
