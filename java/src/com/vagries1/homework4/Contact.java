/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

/** An interface for Aircraft and Ships and all subclasses therein. */
public interface Contact {

    /**
     * Fetch the length attribute (in meters).
     *
     * @return length in meters.
     */
    public int getLength();

    /**
     * Set the length attribute (in meters).
     *
     * @param length The value must be zero or positive. (I would have just made this an unsigned
     *     attribute, but alas the requirements specified to use an int. Meh.)
     */
    public void setLength(int length);

    /**
     * Get the speed of object (in meters per second).
     *
     * @return Speed in meters per second as int.
     */
    public int getSpeed();

    /**
     * Set the speed of object (in meters per second).
     *
     * <p>Note: The value must be zero or positive. (I would have just made this an unsigned
     * attribute, but alas the assignment told me to use an int. Meh.)
     *
     * @param speed in meters per second
     */
    public void setSpeed(int speed);

    /**
     * Set the speed of object (in meters per second).
     *
     * <p>Note: The String argument must express the speed as a decimal value written in Arabic
     * numerals. The value must also be zero or positive.
     *
     * @param speed in meters per second
     */
    public void setSpeed(String speed);

    /**
     * Fetch the name of the object.
     *
     * @return name of object as String.
     */
    public String getName();

    /**
     * Set the name of the object.
     *
     * @param name as a String object.
     */
    public void setName(String name);

    /**
     * Fetch the type of object.
     *
     * @return type of object as String.
     */
    public String getType();

    /**
     * Set the type of object.
     *
     * @param type of object as a String.
     */
    public void setType(String type);
}
