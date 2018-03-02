package com.antgus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Solver {
    RawInput input;
    List<GreedyScorer> scorers = new ArrayList<>();
    State startState;

    public Solver(RawInput input) {
        this.input = input;
        this.startState = createStartState();

        scorers.add(new SimpleScorer());
        scorers.add(new WeightedScorer(0.5,1,0.75));
        scorers.add(new WeightedScorer(0,1,0.75));
    }

    private State createStartState() {
        return new State(input);
    }

    public Solution solve() {
        State bestState = null;
        long bestScore = Long.MIN_VALUE;
        GreedyScorer bestScorer = null;

        for(GreedyScorer scorer: scorers) {
            State state = createStartState();
            // try a variety of different scorers, keep best solution found.
            greedy(state, scorer);
            if (state.getScore() > bestScore) {
                bestScore = state.getScore();
                bestState = state;
                bestScorer = scorer;
            }
        }
        System.out.println(bestScorer);
        System.out.println("Final score: " + bestState.getScore() );
        return bestState.asSolution();
    }

    class RideDist {
        Ride r;
        long d;

        public RideDist(Ride r, long d) {
            this.r = r;
            this.d = d;
        }

        @Override
        public String toString() {
            return "RideDist{" +
                    "r=" + r +
                    ", d=" + d +
                    '}';
        }
    }

    private void bubbleSort(List<RideDist> rides) {
        int i = rides.size() -1;
        while(i > 0) {
            if (rides.get(i-1).d > rides.get(i).d) {
                RideDist aux = rides.get(i);
                rides.set(i, rides.get(i-1));
                rides.set(i-1, aux);
            } else {
                break;
            }
        }
    }

    public void greedy(State state, GreedyScorer scorer) {
        while(!state.freeRides.isEmpty()) {

            double bestScore = Long.MIN_VALUE;
            Vehicle bestVehicle = null;
            Ride bestRide = null;

            // precompute distance between all rides
//            System.out.println("Precomputing pairwise distances");
//            ArrayList<RideDist> sortedRides = new ArrayList<>();
//            int maxSize = 100;
//            for (Ride r1: state.freeRides) {
//                sortedRides.clear();
//                for (Ride r2: state.freeRides) {
//                    if (r2 != r1) {
//                        long d = r1.getEnd().distTo(r2.getStart());
//                        if (sortedRides.size() >= maxSize) {
//                            if (sortedRides.get(sortedRides.size()-1).d > d) {
//                                RideDist rd = sortedRides.get(sortedRides.size()-1);
//                                rd.r = r2;
//                                rd.d = d;
//                                bubbleSort(sortedRides);
//                            }
//                        } else {
//                            sortedRides.add(new RideDist(r2, d));
//                            bubbleSort(sortedRides);
//                        }
//                    }
//                }
//                System.out.println(sortedRides);
//                r1.setClosestRides(sortedRides.stream().map(rd -> rd.r).collect(Collectors.toList()));
//            }
//            System.out.printf("Done");

//            ArrayList<Ride> closestFreeRides = new ArrayList<>(100);
            // for each vehicle
            for (Vehicle v: state.vehicles) {
                // find the ride that is closest to it and can still be visited and leads to least time wasted?
                Point p = v.getFinishPoint();
                long tCurrent = v.getFinishTime();

                for (Ride r: state.freeRides) {
                    long distFromVehicleToRide = p.distTo(r.getStart());
                    long tArrival = tCurrent + distFromVehicleToRide;
                    boolean canGetThereOnTime = tArrival <= r.getMinStartTime();
                    long bonus = tArrival <= r.getStartTime() ? state.timeBonus : 0;
                    long dtWaiting = Math.max(0, r.getStartTime() - tArrival);

                    if (canGetThereOnTime) {
                        double score = scorer.getGreedyScore(bonus, dtWaiting, distFromVehicleToRide, r.getDuration());
                        if (score > bestScore) {
                            bestScore = score;
                            bestVehicle = v;
                            bestRide = r;
                        }
                    }
                }
                if(v.finish.x == 0 && v.finish.y == 0) {
                    break; // all vehicles are the same, if we examined an empty one, we examined them all.
                }
            }

            if (bestVehicle == null) {
                System.out.println("best Vehicle is null!");
                break;
            }
            if(state.freeRides.size() % 1000 == 0) {
                System.out.println("Missing rides " + state.freeRides.size() + ". Adding ride to vehicle. ");
            }
            state.addRideToVehicle(bestRide, bestVehicle);

            //// TODO: 01/03/2018 must have another break statement if we can't handle all rides
        }
    }

    public interface GreedyScorer {
        double getGreedyScore(long bonus, long dtWaiting, long distFromVehicleToRide, long rideDuration);
    }

    public class SimpleScorer implements GreedyScorer {
        public double getGreedyScore(long bonus, long dtWaiting, long distFromVehicleToRide, long rideDuration) {
            long deltaScore = bonus + rideDuration;
            return deltaScore - dtWaiting - distFromVehicleToRide; // will prefer cases where there's less waiting.
            // this is suboptimal when there's many vehicles and driving isn't an issue, since there's no real penalty
            // for driving. Could use some heuristic to balance the importance of waiting based on how many vehicles
            // and etc are available.
        }
    }

    public class WeightedScorer implements GreedyScorer {
        private double scoreWeight;
        private double distWeight;
        private double waitWeight;

        public WeightedScorer(double scoreWeight, double distWeight, double waitWeight) {
            this.scoreWeight = scoreWeight;
            this.distWeight = distWeight;
            this.waitWeight = waitWeight;
        }

        public double getGreedyScore(long bonus, long dtWaiting, long distFromVehicleToRide, long rideDuration) {
            long deltaScore = bonus + rideDuration;
            return scoreWeight*deltaScore - waitWeight*dtWaiting - distWeight*distFromVehicleToRide;
        }

        @Override
        public String toString() {
            return "WeightedScorer{" +
                    "scoreWeight=" + scoreWeight +
                    ", distWeight=" + distWeight +
                    ", waitWeight=" + waitWeight +
                    '}';
        }
    }
}
