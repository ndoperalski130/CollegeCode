package com.example.adapterlab;

public class Player {
    private int number;
    private String name;
    private String position;
    private int picID;

    public Player( int num, String theName, String pos, int draw ) {
        setNum( num );
        setName( theName );
        setPosition( pos );
        setPicID(draw);
    }

    public void setNum( int num ) {
        number = num;
    }
    public void setPicID( int num ) {
        picID = num;
    }
    public void setName( String newName ) {
        name = newName;
    }

    public void setPosition( String pos ) {
       position = pos;
    }

    public int getNumber( ) {
        return number;
    }
    public int getpicID( ) {
        return picID;
    }
    public String getName( ) {
        return name;
    }

    public String getPosition( ) {
        return position;
    }

    public String toString( ) {
        return number + " " + name + " " + position;
    }
}
