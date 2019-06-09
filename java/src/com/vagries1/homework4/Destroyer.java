package com.vagries1.homework4;

public class Destroyer extends Ship {
    private int numberMissiles;

    public Destroyer() {
        super();
        this.numberMissiles = 0;
    }

    public int getNumberMissiles() {
        return this.numberMissiles;
    }

    public void setNumberMissiles(String count) {
        // TODO: Validate
        this.numberMissiles = Integer.parseInt(count);
    }

    public void setNumberMissiles(int count) {
        // TODO: Validate
        this.numberMissiles = count;
    }
}
