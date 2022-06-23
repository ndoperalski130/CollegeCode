package com.example.congestion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // set up the main screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // method to check which activity to launch
    public void LaunchActivity(View v)
    {
        // if the button was the pinger button
        if(v == findViewById(R.id.PingButton))
        {
            // go to Pinger.java
            Intent i = new Intent(this, Pinger.class);
            String test = "We made it";
            i.putExtra("this", test);
            startActivity(i);
        }

        // map button was pressed
        else if (v == findViewById(R.id.MapButton))
        {
            // go to Map.Java
            Intent i = new Intent(this, Map.class);
            startActivity(i);
        }
    }

}
