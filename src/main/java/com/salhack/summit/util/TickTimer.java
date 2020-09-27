package com.salhack.summit.util;

public final class TickTimer
{

    private int tick;

    public void update()
    {
        tick++;
    }

    public void reset()
    {
        tick = 0;
    }

    public boolean hasTimePassed(final int ticks)
    {
        return tick >= ticks;
    }
}