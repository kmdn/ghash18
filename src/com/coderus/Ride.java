package com.coderus;

import java.io.Serializable;

public class Ride implements Comparable, Serializable {
    public int ID = -1;
    public int startX = -1;
    public int startY = -1;
    public int endX = -1;
    public int endY = -1;
    public int startStep = -1;
    public int latestFinish = -1;
    public int cost = -1;
    public int latestStartTime = -1;
    public int bonusLatestStartTime = -1;
    public void updatePoints()
    {
        this.cost = Math.abs(startX-endX) + Math.abs(startY - endY);
    }

    public void updateLatestStartTimes()
    {
        latestStartTime = latestFinish - cost;
        bonusLatestStartTime = startStep + cost;
    }
    @Override
    public int compareTo(Object o) {
        // Sort by latest starting time
        if (o instanceof Ride)
        {
            final Ride castO = (Ride) o;
            //If data is consistent, this should lead to bonsuLatestStartTime to always be taken

            //11kk score
            return Math.min(castO.latestStartTime, castO.bonusLatestStartTime) - Math.min(latestStartTime, bonusLatestStartTime);
            //return castO.latestStartTime+castO.bonusLatestStartTime+castO.cost - (latestStartTime+bonusLatestStartTime+cost);
            //return castO.cost+castO.bonusLatestStartTime - (cost+bonusLatestStartTime);
            //Improves C(8.09kk), D(2.8kk), E(1.8kk)
            //return castO.latestFinish - latestFinish;
            //return castO.startStep - startStep;//e goes up, but all others go down

        }
        return 0;
    }
}
