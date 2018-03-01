package com.coderus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
        final String filename = "small.in";
        FileOutputStream out = null;
        FileInputStream in = null;
        File file;

        try {
            file = new File(filename +".out");
            in = new FileInputStream(file);
            //TODO: READ THE F'ING FILE

            in.close();
            if (!file.exists()) {
                file.createNewFile();
            }

            //TODO: SOLVE PROBLEM





            //TODO: WRITE OUTPUT TO FILE
            out = new FileOutputStream(file);
            out.write(" ".getBytes());
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