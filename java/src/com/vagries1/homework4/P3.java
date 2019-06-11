/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

/** P3 object that has engines. */
public class P3 extends Aircraft {
    /** Number of engines on P3. */
    private int numberEngines;

    /** Minimal number of engines on P3. */
    public final int MIN_ENGINES = 0;

    /** Default constructor. Number of engines set to MIN_ENGINES (0). */
    public P3() {
        super();
        setNumberEngines(MIN_ENGINES);
    }

    /**
     * Explicit constructor of P3 object.
     *
     * @param name Name of P3 as a String. Must not be empty or null.
     * @param type Type of P3 as a String. Must not be empty or null.
     * @param length Length of P3 from end to end in meters. Must be between MIN_LENGTH and
     *     MAX_LENGTH.
     * @param speed The speed of P3 in meters per second. Must be between MIN_SPEED and MAX_SPEED.
     * @param altitude The altitude of the Aircraft in meters. Must be between MIN_ALTITUDE and
     *     MAX_ALTITUDE.
     * @param engines The number of engines currently on P3.
     */
    public P3(String name, String type, int length, int speed, int altitude, int engines) {
        super(name, type, length, speed, altitude);
        setNumberEngines(engines);
    }

    /**
     * Fetch the number of engines currently on P3.
     *
     * @return The number of engines as int
     */
    public int getNumberEngines() {
        return numberEngines;
    }

    /**
     * Set the number of engines on P3.
     *
     * @param engines The number of engines to set on P3 as int. Must be between MIN_MISSILES and
     *     Integer.MAX_VALUE.
     */
    public void setNumberEngines(int engines) {
        if (engines < MIN_ENGINES) {
            throw new ArrayIndexOutOfBoundsException("engine count negative");
        }
        this.numberEngines = engines;
    }

    /** String representation of the P3 object (in JSON format). */
    public String toString() {
        String fmt = "\"p3\":{%s, \"engines\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberEngines());
        return repr;
    }
}
