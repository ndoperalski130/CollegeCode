package com.example.connectfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static Player[] players = new Player[2];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        players[0] = new Player();
        players[1] = new Player();
    }
    public void launchGame(View v)
    {
        Intent myIntent = new Intent(this, TheGameBoard.class);
        Log.i("Here", "In launchActivity");
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }
    public void launchOptions(View v)
    {
        Intent myIntent = new Intent(this, PlayerOptions.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }
    public void Quit(View v)
    {
        this.finish();
    }
}
