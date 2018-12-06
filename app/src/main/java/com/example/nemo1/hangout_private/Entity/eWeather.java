package com.example.nemo1.hangout_private.Entity;

/**
 * Created by nemo1 on 11/25/2018.
 */

public class eWeather {
    private Location location;

    private Current current;

    public eWeather() {
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public Current getCurrent ()
    {
        return current;
    }

    public void setCurrent (Current current)
    {
        this.current = current;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [location = "+location+", current = "+current+"]";
    }
}
