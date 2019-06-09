package com.vagries1.homework4;

public abstract class Aircraft extends Ship implements Contact {

    private int altitude;

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
