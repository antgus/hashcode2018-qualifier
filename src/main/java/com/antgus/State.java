package com.antgus;

import java.util.*;

public class State {
    public final long timeBonus;
    public long score;
    public List<Vehicle> vehicles;
    public List<Ride> freeRides;
    Set<Ride> isTaken = new HashSet<>();

    public State(RawInput input) {
        this.timeBonus = input.timeBonus;
        this.score = 0;
        this.vehicles = new ArrayList<>(input.numVehicles);
        Point origin = Point.of(0,0);
        for(int i=0; i < input.numVehicles; i++) {
            vehicles.add(new Vehicle(origin, 0));
        }
        this.freeRides = new ArrayList<>(input.rides);
    }

    public void addRideToVehicle(Ride r, Vehicle v) {
        freeRides.remove(r);
        isTaken.add(r);
        v.addRide(r);
        this.score += r.getDuration();
        if (v.lastRideHasBonus) {
            this.score += timeBonus;
        }
    }

    public long getScore() {
        return score;
    }

    public Solution asSolution() {
        return new Solution(vehicles, score);
    }

    public boolean isTaken(Ride r) {
        return isTaken.contains(r);
    }
}
