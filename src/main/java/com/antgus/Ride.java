package com.antgus;

import java.util.List;

public final class Ride {
    private final int id;
    private final Point start;
    private final Point end;
    private final long tStart;
    private final long tEnd;
    private final long minStartTime;
    private final long dtDuration;
    private List<Ride> closestRides;

    public Ride(int id, Point start, Point end, long tStart, long tEnd) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.dtDuration = start.distTo(end);
        this.minStartTime = tEnd - dtDuration;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }

    public long getStartTime() {
        return tStart;
    }

    public long getEndTime() {
        return tEnd;
    }

    public long getMinStartTime() {
        return minStartTime;
    }

    public long getDuration() {
        return dtDuration;
    }

    public void setClosestRides(List<Ride> rides) {
        this.closestRides = rides;
    }

    public List<Ride> getClosestRides() {
        return this.closestRides;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "start=" + start +
                ", end=" + end +
                ", tStart=" + tStart +
                ", tEnd=" + tEnd +
                '}';
    }
}
