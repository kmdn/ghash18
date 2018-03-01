package com.coderus;

import java.io.*;
import java.util.ArrayList;

public class KrisMain {
    public static void main(String[] args) {
        /*
        FIRST LINE
        R = # rows
        C = # cols
        F = # total vehicles
        N = # rides/travels to make
        B = Bonus per ride ot start on time
        T = number of total steps in simulation

        NEXT LINES
        ● a – the row of the start intersection 0 ( ≤ a < R)
        ● b – the column of the start intersection 0( ≤ b < C)
        ● x – the row of the finish intersection 0( ≤ x < R)
        ● y – the column of the finish intersection 0( ≤ y < C)
        ● s – the earliest start (0 ≤ s < T) )
        ● f – the latest finish (0 ≤ f ≤ T), (f ≥ s + | − a + | − b|)

         */
        int R, C, F, N, B, T;

        final String filename = "small.in";
        BufferedWriter out = null;
        BufferedReader in = null;
        File file;
        final ArrayList<String> lines = new ArrayList<String>();
        try {
            String line = null;
            file = new File(filename +".out");
            in = new BufferedReader(new FileReader(file));
            //TODO: READ THE F'ING FILE
            boolean firstLine = true;
            do {
                line = in.readLine();
                if (firstLine)
                {
                    //Tokenize first line

                    firstLine = false;
                }
                else
                {
                    //It's any of the non-first lines
                }
            } while(line != null);
            in.close();
            if (!file.exists()) {
                file.createNewFile();
            }

            //TODO: SOLVE PROBLEM





            //TODO: WRITE OUTPUT TO FILE
            out = new BufferedWriter(new FileWriter(file));
            out.write("THIS IS MY OUTPUT");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello Worldy -joni");
    }

    public int distance(Vehicle v, int x, int y)
    {
        return Math.abs(v.posX - x) + Math.abs(v.posY - y);
    }
}