package com.antgus;

import java.util.List;

public class RawInput {
    public int numVehicles;
    public List<Ride> rides;
    public long timeBonus;
    public long numSteps;

    @Override
    public String toString() {
        return "RawInput{" +
                "numVehicles=" + numVehicles +
                ", rides=" + rides +
                ", timeBonus=" + timeBonus +
                ", numSteps=" + numSteps +
                '}';
    }
}
