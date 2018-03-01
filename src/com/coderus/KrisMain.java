package com.coderus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KrisMain {
    public static void main(String[] args) {

        final String f1 = "./resources/a_example.in";
        final String f2 = "./resources/b_should_be_easy.in";
        final String f3 = "./resources/c_no_hurry.in";
        final String f4 = "./resources/d_metropolis.in";
        final String f5 = "./resources/e_high_bonus.in";
        final String[] files = new String[]{f1, f2, f3, f4, f5};
        int sum = 0;
        for (String f : files)
        {
            sum+=computeItForOneFile(f);
        }

        System.out.println(" ----- Total rides assigned : "+ sum +"-----");
        System.out.println("Hello Worldy -joni");
    }

    public static int computeItForOneFile(final String filename)
    {
        final int R, C, F, N, B, T;
        final File outFile = new File(filename+".out");
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
            int lineCounter = 0;
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
                r.ID = lineCounter;
                lineCounter++;
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

            //Vehicles are created on the fly
            final ArrayList<Vehicle> vehicles = new ArrayList<>();

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
                boolean noneAvailable = true;
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

                    //OLD Vehicle Assignment
                    if (vehicle.isAvailable(shouldLeaveSoft))
                    {
                        //Add ride to vehicle
                        vehicle.rides.add(ride);
                        //Note when it will be available after this ride
                        vehicle.whenAvailable = shouldLeaveSoft + costToStart + ride.cost;
                        vehicle.posX = ride.endX;
                        vehicle.posY = ride.endY;
                        noneAvailable = false;
                        break;
                    }
                    else if (vehicles.size()>=F-1 && vehicle.isAvailable(shouldLeaveHard))
                    {
                        //Add ride to vehicle
                        vehicle.rides.add(ride);
                        vehicle.posX = ride.endX;
                        vehicle.posY = ride.endY;
                        //Note when it will be available after this ride
                        // Define when exactly we want to leave
                        // B/c now we have a larger range when to go
                        vehicle.whenAvailable += costToStart + ride.cost;
                        noneAvailable = false;
                        break;
                    }
                }

                //NEW Vehicle Assignment
                if (noneAvailable && vehicles.size() < F)
                {
                    final Vehicle vehicle = new Vehicle();
                    final int costToStart = computeCostToStartOfRide(vehicle, ride);
                    if (costToStart > ride.latestStartTime)
                    {
                        //Impossible to get there on time
                        break;
                    }
                    //Add ride to vehicle
                    vehicle.rides.add(ride);
                    vehicle.posX = ride.endX;
                    vehicle.posY = ride.endY;
                    // Note when it will be available after this ride
                    // Define when exactly we want to leave
                    // B/c now we have a larger range when to go
                    vehicle.whenAvailable = ride.startStep + ride.cost;
                    noneAvailable = false;
                    vehicles.add(vehicle);
                }

            }

            //Gui's code START
            if (false) {
                final ArrayList<Vehicle> veh = new ArrayList<>();
                for (int i = 0; i < F; i++) {
                    Vehicle v = new Vehicle();
                    veh.add(v);
                }
                greedy(veh, rides);
            }
            //Gui's code END

            //TODO: WRITE OUTPUT TO FILE
            out = new BufferedWriter(new FileWriter(outFile));
            System.out.println("Vehicles used: "+vehicles.size());
            int sumRidesAssigned = 0;
            for (Vehicle v : vehicles)
            {
                sumRidesAssigned += v.rides.size();
            }
            System.out.println("Total rides assigned: "+sumRidesAssigned);
            for (Vehicle v : vehicles)
            {
                String ridesStr = "";
                for (Ride r : v.rides)
                {
                    ridesStr += " "+r.ID;
                }
                out.write(v.rides.size()+ridesStr+System.getProperty("line.separator"));
            }
            out.close();
            return sumRidesAssigned;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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

    private static int[] computePoints(Vehicle vehicle, Ride ride){
        int solution[] = new int[2];
        int distance = manhattanDistance(vehicle.posX, vehicle.posY,ride.startX, ride.startY); //distance = time to get there
        int startStep = distance + vehicle.whenAvailable;
        if (startStep<ride.startStep){
            startStep = ride.startStep;
        }
        int finishStep = startStep + distance;

        if (finishStep<ride.latestFinish) {
            if (startStep == ride.startStep) {
                solution[0]=ride.cost +2;
                solution[1]=finishStep;
            } else {
                solution[0]=ride.cost;
                solution[1]=finishStep;
            }
        } else {
            solution[0]=0;
            solution[1]=finishStep;
        }
        return solution;

    }

    private static int manhattanDistance(int xA, int yA, int xB, int yB){
        return Math.abs(xA-xB) + Math.abs(yA-yB);
    }

    private static void assignRide(ArrayList<Vehicle> vehicles, ArrayList<Ride> free_rides){
        Vehicle best_vehicle = vehicles.get(0);
        Ride best_ride = free_rides.get(0);
        int best_end_step = 0;
        int best_points = 0;
        for (Vehicle vehicle : vehicles) {
            for (Ride ride : free_rides) {
                int[] points_computation = computePoints(vehicle, ride);
                int points = points_computation[0];
                int time = points_computation[1];
                if (points > best_points) {
                    best_vehicle = vehicle;
                    best_ride = ride;
                    best_points = points;
                    best_end_step = time;
                }
            }
        }
        best_vehicle.rides.add(best_ride);
        best_vehicle.posX=best_ride.endX;
        best_vehicle.posY=best_ride.endY;
        best_vehicle.isAvailable(best_end_step);
        free_rides.remove(best_ride);

        //there's no possible way to finish rides
        if (best_points==0){
            free_rides.clear();
        }
    }

    private static void greedy(ArrayList<Vehicle> vehicles, ArrayList<Ride> free_rides){
        while (free_rides.size()>0){
            assignRide(vehicles,free_rides);
        }
    }
}