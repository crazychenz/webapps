/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

public abstract class Aircraft extends Ship implements Contact {

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

    public Aircraft() {
        super();
        setAltitude(MEAN_ALTITUDE);
    }

    public Aircraft(String name, String type, int length, int speed, int altitude) {
        super(name, type, length, speed);
        setAltitude(altitude);
    }

    public int getAltitude() {
        return altitude;
    }

    /**
     * Set the altitude (in meters) of the Aircraft.
     *
     * @param altitude must be between MIN_ALTITUDE and MAX_ALTITUDE
     * @throws ArrayIndexOutOfBoundsException
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

    public String toString() {
        String fmt = "\"aircraft\":{%s, \"altitude\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getAltitude());
        return repr;
    }
}
