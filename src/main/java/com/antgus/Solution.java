package com.antgus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    List<List<Integer>> rideIds = new ArrayList<>();
    public long score;

    public Solution(List<Vehicle> vehicles, long score) {
        this.score = score;
        for(Vehicle v: vehicles) {
            rideIds.add(v.getRides().stream().map(Ride::getId).collect(Collectors.toList()));
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for(List<Integer> rides: rideIds) {
            buf.append(rides.size()).append(" "); // first is the number of rides
            rides.forEach(id -> buf.append(id).append(" "));
            buf.append("\n");
        }
        return buf.toString();
    }
}
