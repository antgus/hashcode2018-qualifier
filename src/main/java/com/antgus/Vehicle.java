package com.antgus;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    public Point finish;
    public long tFinish;
    public List<Ride> rides = new ArrayList<>(100);
    public boolean lastRideHasBonus = false;

    public Vehicle(Point finish, long tFinish) {
        this.finish = finish;
        this.tFinish = tFinish;
    }

    public Point getFinishPoint() {
        return finish;
    }

    public long getFinishTime() {
        return tFinish;
    }

    public void addRide(Ride r) {
        long tArrival = tFinish + finish.distTo(r.getStart());
        this.rides.add(r);
        this.lastRideHasBonus = tArrival <= r.getStartTime();
        this.tFinish = Math.max(tArrival, r.getStartTime()) + r.getDuration();
        this.finish = r.getEnd();
    }

    public List<Ride> getRides() {
        return rides;
    }

}
