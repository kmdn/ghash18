package com.coderus;

import java.io.Serializable;

public class Ride implements Comparable, Serializable {
    public int startX = -1;
    public int startY = -1;
    public int endX = -1;
    public int endY = -1;
    public int startStep = -1;
    public int latestFinish = -1;
    public int cost = -1;
    public int latestStartTime = -1;
    public void updatePoints()
    {
        this.cost = Math.abs(startX-endX) + Math.abs(startY - endY);
    }

    public void updateLatestStartTime()
    {
        latestStartTime = latestFinish - cost;
    }
    @Override
    public int compareTo(Object o) {
        // Sort by latest starting time
        if (o instanceof Ride)
        {
            Ride castO = (Ride) o;
            return castO.latestStartTime - latestStartTime;
        }
        return 0;
    }
}
