/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4;

public class P3 extends Aircraft {
    private int numberEngines;

    public final int MIN_ENGINES = 0;

    public P3() {
        super();
        setNumberEngines(MIN_ENGINES);
    }

    public P3(String name, String type, int length, int speed, int altitude, int engines) {
        super(name, type, length, speed, altitude);
        setNumberEngines(engines);
    }

    public int getNumberEngines() {
        return numberEngines;
    }

    public void setNumberEngines(int engines) {
        if (engines < MIN_ENGINES) {
            throw new ArrayIndexOutOfBoundsException("engine count negative");
        }
        this.numberEngines = engines;
    }

    public String toString() {
        String fmt = "\"p3\":{%s, \"engines\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberEngines());
        return repr;
    }
}
