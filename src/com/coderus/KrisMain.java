package com.coderus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class KrisMain {
    public static void main(String[] args) {
        final int R, C, F, N, B, T;

        final String filename = "small.in";
        BufferedWriter out = null;
        BufferedReader in = null;
        File file;
        final ArrayList<Ride> rides = new ArrayList<Ride>();
        try {
            file = new File(filename +".out");
            in = new BufferedReader(new FileReader(file));
            //TODO: READ THE FUN FILE!
            String line = null;
            line = in.readLine();
            //Tokenize first line
            /*
            R = # rows
            C = # cols
            F = # total vehicles
            N = # rides/travels to make
            B = Bonus per ride ot start on time
            T = number of total steps in simulation
            */
            String[] tokens = line.split(" ");
            R = Integer.parseInt(tokens[0]);
            C = Integer.parseInt(tokens[1]);
            F = Integer.parseInt(tokens[2]);
            N = Integer.parseInt(tokens[3]);
            B = Integer.parseInt(tokens[4]);
            T = Integer.parseInt(tokens[5]);
            do {
                line = in.readLine();
                //It's any of the non-first lines
                final Ride r = new Ride();
                /*
                    ● a – the row of the start intersection 0 ( ≤ a < R)
                    ● b – the column of the start intersection 0( ≤ b < C)
                    ● x – the row of the finish intersection 0( ≤ x < R)
                    ● y – the column of the finish intersection 0( ≤ y < C)
                    ● s – the earliest start (0 ≤ s < T) )
                    ● f – the latest finish (0 ≤ f ≤ T), (f ≥ s + | − a + | − b|)
                 */
                tokens = line.split(" ");
                r.startX = Integer.parseInt(tokens[0]);
                r.startY = Integer.parseInt(tokens[1]);
                r.endX = Integer.parseInt(tokens[2]);
                r.endY = Integer.parseInt(tokens[3]);
                r.startStep = Integer.parseInt(tokens[4]);
                r.latestFinish = Integer.parseInt(tokens[5]);
                r.updatePoints();
                r.updateLatestStartTime();
                rides.add(r);
            } while(line != null);
            tokens = null;
            in.close();
            if (!file.exists()) {
                file.createNewFile();
            }

            //Create all vehicles we have
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            for (int i = 0; i < F; i++)
            {
                final Vehicle v = new Vehicle();
                vehicles.add(v);
            }

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
            // Assign cars to rides
            Collections.sort(rides);
            for (int rideNbr = 0; rideNbr < N; rideNbr++)
            {
                final Ride ride = rides.get(rideNbr);
                for (Vehicle v : vehicles)
                {

                }

            }




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