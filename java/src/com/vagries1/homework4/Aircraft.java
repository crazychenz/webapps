package com.vagries1.homework4;

public abstract class Aircraft implements Contact {

    private int length;
    private int speed;
    private String name;
    private String type;
    private int altitude;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpeed(String speed) {
        this.speed = Integer.parseInt(speed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    int getAltitude() {
        return altitude;
    }

    void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
