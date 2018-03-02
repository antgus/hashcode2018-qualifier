package com.antgus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Use of a BufferedReader speeds up Scanner
 */
public class FastScanner {

    public BufferedReader br;
    public StringTokenizer st;

    public FastScanner(InputStreamReader in) {
        br = new BufferedReader(in);
    }

    public String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public int[] nextIntArray(int size) {
        int[] array = new int[size];
        for(int i=0; i < size; i++) {
            array[i] = nextInt();
        }
        return array;
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
