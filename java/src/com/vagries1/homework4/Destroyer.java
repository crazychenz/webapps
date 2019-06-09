package com.vagries1.homework4;

public class Destroyer extends Ship {
    private int numberMissiles;

    public final int MIN_MISSILES = 0;

    public Destroyer() {
        super();
        setNumberMissiles(MIN_MISSILES);
    }

    public Destroyer(String name, String type, int length, int speed, int missiles) {
        super(name, type, length, speed);
        setNumberMissiles(missiles);
    }

    public int getNumberMissiles() {
        return this.numberMissiles;
    }

    public void setNumberMissiles(String count) {
        int missiles = Integer.parseInt(count);
        setNumberMissiles(missiles);
    }

    public void setNumberMissiles(int count) throws ArrayIndexOutOfBoundsException {
        if (count < MIN_MISSILES) {
            throw new ArrayIndexOutOfBoundsException("missiles count negative");
        }
        this.numberMissiles = count;
    }

    public String toString() {
        String fmt = "\"destroyer\":{%s, \"missiles\":\"%d\"}";
        String repr = String.format(fmt, super.toString(), getNumberMissiles());
        return repr;
    }
}
