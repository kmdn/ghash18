package com.coderus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class KrisMain {
    public static void main(String[] args) {
        final int R, C, F, N, B, T;

        final String filename = "./resources/a_example.in";
        BufferedWriter out = null;
        BufferedReader in = null;
        File file;
        final ArrayList<Ride> rides = new ArrayList<Ride>();
        try {
            file = new File(filename);
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
                if (line == null) break;
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
                r.updateLatestStartTimes();
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
                for (Vehicle vehicle : vehicles)
                {
                    // Need to check if vehicle can be there on time for the start
                    // Done as follows:
                    // For every vehicle, compute if it is available at the time
                    // it can reach the starting point
                    // aka. ride's start time - cost of vehicle getting there

                    //Shouldn't be necessary to check for range of times b/c we have a sorted list of rides
                    final int costToStart = computeCostToStartOfRide(vehicle, ride);
                    final int shouldLeaveSoft = ride.startStep - costToStart;
                    final int shouldLeaveHard = ride.latestStartTime - costToStart;
                    if (vehicle.isAvailable(shouldLeaveSoft))
                    {
                        //Add ride to vehicle
                        vehicle.rides.add(ride);
                        //Note when it will be available after this ride
                        vehicle.whenAvailable = shouldLeaveSoft + costToStart + ride.cost;
                        vehicle.posX = ride.endX;
                        vehicle.posY = ride.endY;
                        break;
                    }
                    else if (vehicle.isAvailable(shouldLeaveHard))
                    {
                        // Define when exactly we want to leave
                        // B/c now we have a larger range when to go
                        vehicle.whenAvailable += costToStart + ride.cost;
                    }
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

    public static int computeCostToStartOfRide(final Vehicle vehicle, final Ride ride)
    {
        //Get the distance from current pos to wherever ride's start is
        return distance(vehicle.posX, vehicle.posY, ride.startX, ride.startY);
    }

    public static int distance(Vehicle v, int x, int y)
    {
        return distance(v.posX, v.posY, x, y);
    }

    public static int distance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private int computePoints(Vehicle vehicle, Ride ride){
        int distance = manhattanDistance(vehicle.posX, vehicle.posY,ride.startX, ride.startY); //distance = time to get there
        int startStep = distance + vehicle.whenAvailable;
        if (startStep<ride.startStep){
            startStep = ride.startStep;
        }
        int finishStep = startStep + distance;

        if (finishStep<ride.latestFinish) {
            if (startStep == ride.startStep) {
                return ride.points + 2;
            } else {
                return ride.points;
            }
        } else {
            return 0;
        }

    }

    private int manhattanDistance(int xA, int yA, int xB, int yB){
        return Math.abs(xA-xB) + Math.abs(yA-yB);
    }
}