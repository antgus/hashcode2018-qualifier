package com.antgus;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        List<String> files = Arrays.asList("a_example.in", "b_should_be_easy.in", "c_no_hurry.in", "d_metropolis.in", "e_high_bonus.in");
        String inputFolder = "/Users/agus/projects/hashcode2018qual/input/";
        String outputFolder = "/Users/agus/projects/hashcode2018qual/output/";

        long totalScore = 0;
        try {
            for(String f: files) {
                totalScore += solve(inputFolder, outputFolder, f).score;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Total score= " + totalScore);
    }

    public static Solution solve(String inputFolder, String outputFolder, String filename) throws java.io.FileNotFoundException {
        String inFile = inputFolder + filename;
        String outFile = outputFolder + filename + ".out";
        RawInput input = InputReader.readInput(inFile);
        //System.out.println(input);
        System.out.println(filename + ": num vehicles= " + input.numVehicles + "; num rides= " + input.rides.size());

        Solver solver = new Solver(input);
        Solution solution = solver.solve();

        solution.toString();

        System.out.println(filename + ", score= " + solution.score);

        try (PrintWriter out = new PrintWriter(outFile)) {
            out.println(solution.toString());
        }
        return solution;
    }
}
