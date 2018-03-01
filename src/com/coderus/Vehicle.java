package com.coderus;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    public int posX = 0;
    public int posY = 0;
    public boolean occupied = false;
    //Step when it is available
    public int whenAvailable = 0;
    public List<Ride> rides = new ArrayList<Ride>();


    public boolean isAvailable(final int step)
    {
        return step > whenAvailable;
    }

    public void updateWhenAvailable(final Ride r)
    {
        this.whenAvailable = r.cost;
    }

}
