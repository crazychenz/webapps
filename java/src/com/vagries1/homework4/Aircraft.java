/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

/** Aircraft object that has altitude. */
public abstract class Aircraft extends Ship implements Contact {

    /** Altitude of Aircraft in meters. */
    private int altitude;

    /**
     * The highest point that contains 'air' for an 'Aircraft' is the exosphere of the Earth. Beyond
     * this point is solarwind. This point is roughly 10,000 km, therefore the maximum altitude is
     * 10,000,000 meters.
     */
    public final int MAX_ALTITUDE = 10000000;

    /** Sea level has an altitude of 0. */
    public final int MEAN_ALTITUDE = 0;

    /**
     * Lowest place on earth is 420 meters below seal level. Therefore, with a bit of margin for
     * error, the altitude should never be less than -1000 meters.
     */
    public final int MIN_ALTITUDE = -1000;

    /** Default constructor of Aircract. Defaults altitude to MEAN_ALTITUDE (0) */
    public Aircraft() {
        super();
        setAltitude(MEAN_ALTITUDE);
    }

    /**
     * Explicit constructor of Aircraft.
     *
     * @param name Name of Aircraft as a String. Must not be empty or null.
     * @param type Type of Aircraft as a String. Must not be empty or null.
     * @param length Length of Aircraft from end to end in meters. Must be between MIN_LENGTH and
     *     MAX_LENGTH.
     * @param speed The speed of Aircraft in meters per second. Must be between MIN_SPEED and
     *     MAX_SPEED.
     * @param altitude The altitude of the Aircraft in meters. Must be between MIN_ALTITUDE and
     *     MAX_ALTITUDE.
     */
    public Aircraft(String name, String type, int length, int speed, int altitude) {
        super(name, type, length, speed);
        setAltitude(altitude);
    }

    /**
     * Fetch the altitude of object in meters.
     *
     * @return altitude of object in meters as int.
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * Set the altitude (in meters) of the Aircraft.
     *
     * @param altitude must be between MIN_ALTITUDE and MAX_ALTITUDE
     */
    public void setAltitude(int altitude) throws ArrayIndexOutOfBoundsException {
        if (altitude < MIN_ALTITUDE) {
            throw new ArrayIndexOutOfBoundsException("altitude too low");
        }
        if (altitude > MAX_ALTITUDE) {
            throw new ArrayIndexOutOfBoundsException("altitude too high");
        }

        this.altitude = altitude;
    }

    /** String representation of Aircraft object (in JSON format). */
    public String toString() {
        String fmt = "\"aircraft\":{%s, \"altitude\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getAltitude());
        return repr;
    }
}
