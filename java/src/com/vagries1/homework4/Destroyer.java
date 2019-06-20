/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

/** Destroyer object that has missiles. */
public class Destroyer extends Ship {
    /** Number of missiles on Destroyer. */
    private int numberMissiles;

    /** Minimal number of missiles on Destroyer. */
    public final int MIN_MISSILES = 0;

    /** Default constructor. Number of missiles set to MIN_MISSILES (0). */
    public Destroyer() {
        super();
        setNumberMissiles(MIN_MISSILES);
    }

    /**
     * Explicit constructor of Destroyer object.
     *
     * @param name Name of Destroyer as a String. Must not be empty or null.
     * @param type Type of Destroyer as a String. Must not be empty or null.
     * @param length Length of Destroyer from end to end in meters. Must be between MIN_LENGTH and
     *     MAX_LENGTH.
     * @param speed The speed of Destroyer in meters per second. Must be between MIN_SPEED and
     *     MAX_SPEED.
     * @param missiles The number of missiles currently on Destroyer.
     */
    public Destroyer(String name, String type, int length, int speed, int missiles) {
        super(name, type, length, speed);
        setNumberMissiles(missiles);
    }

    /**
     * Fetch the number of missiles currently on Destroyer.
     *
     * @return The number of missiles as int
     */
    public int getNumberMissiles() {
        return this.numberMissiles;
    }

    /**
     * Set the number of missiles on Destroyer.
     *
     * @param count The number of missiles to set on Destroyer as String. Must be arabic numerals
     *     with radix 10.
     */
    public void setNumberMissiles(String count) {
        int missiles = Integer.parseInt(count);
        setNumberMissiles(missiles);
    }

    /**
     * Set the number of missiles on Destroyer.
     *
     * @param count The number of missiles to set on Destroyer as int. Must be between MIN_MISSILES
     *     and Integer.MAX_VALUE.
     */
    public void setNumberMissiles(int count) throws ArrayIndexOutOfBoundsException {
        if (count < MIN_MISSILES) {
            throw new ArrayIndexOutOfBoundsException("missiles count negative");
        }
        this.numberMissiles = count;
    }

    /** String representation of the Destroyer object (in JSON format). */
    public String toString() {
        String fmt = "\"destroyer\":{%s, \"missiles\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberMissiles());
        return repr;
    }
}
