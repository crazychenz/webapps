/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

public abstract class Ship implements Contact {

    private int length;
    private int speed;
    private String name;
    private String type;

    /**
     * The largest ship ever constructed is the Knock Nevis and was roughly 459m long. Giving a bit
     * of margin from this acheivement, the maximum number of meters we'll allow our Ship object to
     * be is 1000 meters.
     */
    public final int MAX_LENGTH = 1000;

    /** Something that doesn't exist can be zero meters long. */
    public final int MIN_LENGTH = 0;

    /**
     * The speed of light and the maximum speed for all matter is 299792458 meters per second. We'll
     * allow for a bit more to keep things round.
     */
    public final int MAX_SPEED = 300000000;

    /** If you make like a tree, you'll be moving 0 meters per second. */
    public final int MIN_SPEED = 0;

    /** Default constructor. */
    public Ship() {
        length = MIN_LENGTH;
        speed = MIN_SPEED;
        name = null;
        type = null;
    }

    /**
     * Explicit constructor.
     *
     * @param name Name of the Ship as an arbitrary String.
     * @param type Type of the Ship as an arbitrary String.
     * @param speed Speed of the Ship (in meters per second).
     * @param length Length of the Ship (in meters).
     */
    public Ship(String name, String type, int length, int speed) {
        setName(name);
        setType(type);
        setLength(length);
        setSpeed(speed);
    }

    /** Fetch the length (in meters) of the Ship. */
    public int getLength() {
        return length;
    }

    /**
     * Set the length of a ship.
     *
     * @param length Length of the Ship (in meters). Must be between MIN_LENGTH and MAX_LENGTH.
     */
    public void setLength(int length) throws ArrayIndexOutOfBoundsException {
        if (length < MIN_LENGTH) {
            throw new ArrayIndexOutOfBoundsException("length negative");
        }
        if (length > MAX_LENGTH) {
            throw new ArrayIndexOutOfBoundsException("length too high");
        }
        this.length = length;
    }

    /** Fetch the speed (in meters per second) of the Ship. */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the Ship.
     *
     * @param speed is the Speed of the ship in meters per second. Values must be between MAX_SPEED
     *     and MIN_SPEED.
     */
    public void setSpeed(int speed) throws ArrayIndexOutOfBoundsException {
        if (speed < MIN_SPEED) {
            throw new ArrayIndexOutOfBoundsException("speed negative");
        }
        if (speed > MAX_SPEED) {
            throw new ArrayIndexOutOfBoundsException("speed too high");
        }
        this.speed = speed;
    }

    /**
     * Set the speed of the Ship.
     *
     * @param speedStr is the Speed of the ship in meters per second provided as a string. Values
     *     must be between MAX_SPEED and MIN_SPEED. The String must provides the speed as an integer
     *     expressed in decimal with arabic numerals.
     */
    public void setSpeed(String speedStr) throws ArrayIndexOutOfBoundsException {
        int speed = Integer.parseInt(speedStr);
        setSpeed(speed);
    }

    /** Fetch the name of the Ship. */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the ship.
     *
     * @param name Name of Ship as an arbitrary String. String must not be null and must be more
     *     than 0 length.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        } else if (name.length() < 1) {
            throw new IllegalArgumentException("name is empty");
        }
        this.name = name;
    }

    /** Get the type of the Ship. */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the ship.
     *
     * @param type Type of the Ship as an arbitrary String. String must not be null and must be more
     *     than 0 length.
     */
    public void setType(String type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("name is null");
        } else if (type.length() < 1) {
            throw new IllegalArgumentException("name is empty");
        }
        this.type = type;
    }

    public String toString() {
        String name = this.name;
        String type = this.type;
        String repr;
        String fmt;

        if (this.name == null) {
            name = "null";
        }
        if (this.type == null) {
            type = "null";
        }

        fmt = "\"ship\":{\"name\":\"%s\", \"type\":\"%s\", \"speed\":\"%d\", \"length\":\"%d\"}";
        repr = String.format(fmt, name, type, this.speed, this.length);
        return repr;
    }
}
