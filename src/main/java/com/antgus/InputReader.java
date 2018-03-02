package com.antgus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    private static InputReader ourInstance = new InputReader();

    public static InputReader getInstance() {
        return ourInstance;
    }

    private InputReader() {
    }

    public static RawInput readInput(String filename) {
        FastScanner sc;
        try {
            File file = new File(filename);
            sc = new FastScanner(new InputStreamReader(new FileInputStream(file)));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        RawInput input = new RawInput();

        int numRows = sc.nextInt(); // <= 10 000
        int numCols = sc.nextInt(); // <= 10 000
        input.numVehicles = sc.nextInt(); // <= 1000
        int numRides = sc.nextInt(); // <= 10 000
        input.timeBonus = sc.nextInt(); // <= 10^9
        input.numSteps = sc.nextInt(); // <= 10^9


        List<Ride> rides = new ArrayList<>(numRides);

        for(int i=0; i < numRides; i++) {
            Point start = Point.of(sc.nextInt(), sc.nextInt());
            Point end = Point.of(sc.nextInt(), sc.nextInt());
            long tStart = sc.nextInt();
            long tEnd = sc.nextInt();
            rides.add(new Ride(i, start, end, tStart, tEnd));
        }
        input.rides = rides;
        return input;
    }
}
