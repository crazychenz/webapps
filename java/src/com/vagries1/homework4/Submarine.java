package com.vagries1.homework4;

public class Submarine extends Ship {
    private int numberTorpedos;

    public int getNumberTorpedos() {
        return numberTorpedos;
    }

    public void setNumberTorpedos(int torpedos) {
        this.numberTorpedos = torpedos;
    }

    public void setNumberTorpedos(String torpedos) {
        this.numberTorpedos = Integer.parseInt(torpedos);
    }
}
