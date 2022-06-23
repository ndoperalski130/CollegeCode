package com.example.connectfour;

import android.graphics.Color;

public class Player
{
    int r, g , b;
    String name;
    boolean isFirst;
    int wins,losses;

    public Player()
    {
        r = 255;
        g = 0;
        b = 0;
        name = "John Doe";
        isFirst = true;
        wins = 0;
        losses = 0;
    }
    public Player(int cR, int cG, int cB, String n, boolean i, int w, int l)
    {
        r = cR;
        g = cG;
        b = cB;
        name = n;
        isFirst = i;
        wins = w;
        losses = l;
    }
    public int getRed()
    {
        return r;
    }
    public int getGreen()
    {
        return g;
    }
    public int getBlue()
    {
        return b;
    }
    public String getName()
    {
        return name;
    }
    public boolean isFirst()
    {
        return isFirst;
    }
    public void setColor(int cR, int cG, int cB)
    {
        r = cR;
        g = cG;
        b = cB;
    }
    public void setName(String n)
    {
        name = n;
    }
    public void setFirst(boolean f)
    {
        isFirst = f;
    }
    public int getWins()
    {
        return wins;
    }
    public int getLosses()
    {
        return losses;
    }
    public void incrementScore(boolean won)
    {
        if(won)
            wins++;
        else
            losses++;
    }
    public int getColor()
    {
        return Color.rgb(r,g,b);
    }
}
