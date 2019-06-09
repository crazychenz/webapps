/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

public class Submarine extends Ship {
    private int numberTorpedos;

    public final int MIN_TORPEDOS = 0;

    public Submarine() {
        super();
        setNumberTorpedos(MIN_TORPEDOS);
    }

    public Submarine(String name, String type, int length, int speed, int torpedos) {
        super(name, type, length, speed);
        setNumberTorpedos(torpedos);
    }

    public int getNumberTorpedos() {
        return numberTorpedos;
    }

    public void setNumberTorpedos(int torpedos) {
        if (torpedos < MIN_TORPEDOS) {
            throw new ArrayIndexOutOfBoundsException("torpedo count negative");
        }
        this.numberTorpedos = torpedos;
    }

    public void setNumberTorpedos(String torpedos) {
        int numberTorpedos = Integer.parseInt(torpedos);
        setNumberTorpedos(numberTorpedos);
    }

    public String toString() {
        String fmt = "\"submarine\":{%s, \"torpedos\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberTorpedos());
        return repr;
    }
}
