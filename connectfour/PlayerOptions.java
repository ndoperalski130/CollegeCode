package com.example.connectfour;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerOptions extends AppCompatActivity
{
    SeekBar redVal, greenVal, blueVal;
    View theColor;
    double red, green, blue;
    SeekBarHandler myListener;
    int whichPlayer = 0;

    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.player_options);
        myListener = new SeekBarHandler();
        redVal = findViewById(R.id.redVal);
        redVal.setOnSeekBarChangeListener(myListener);
        greenVal = findViewById(R.id.greenVal);
        greenVal.setOnSeekBarChangeListener(myListener);
        blueVal = findViewById(R.id.blueVal);
        blueVal.setOnSeekBarChangeListener(myListener);
        theColor = findViewById(R.id.theColor);
    }
    public class SeekBarHandler implements SeekBar.OnSeekBarChangeListener
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if(seekBar == redVal)
            {
                red = seekBar.getProgress() * 2.55;

                Log.i("here", "The value of red is: " + red);
            }
            else if(seekBar == greenVal)
            {
                green = seekBar.getProgress() * 2.55;
                Log.i("Here", "The value of green is: " + green);
            }
            else
            {
                blue = seekBar.getProgress() * 2.55;
                Log.i("Here", "The value of blue is: " + blue);
            }
            theColor.setBackgroundColor(Color.rgb((int)red,(int)green,(int)blue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
    public void applyChanges(View v)
    {
        EditText et = findViewById(R.id.whatPlayer);
        String str = et.getText().toString();
        whichPlayer = Integer.parseInt(str);
        Log.i("Here", "Player is: " + whichPlayer);
        if(whichPlayer != 1 && whichPlayer != 2)
        {
            et.requestFocus();
        }
        else
        {
            if(whichPlayer == 1)
            {
                MainActivity.players[0].setColor((int)red, (int)green, (int)blue);
                et = findViewById(R.id.playerName);
                str = et.getText().toString();
                if(str.equals(" "))
                {
                    et.requestFocus();
                }
                else
                {
                    MainActivity.players[0].setName(str);
                }
            }
            else
            {
                MainActivity.players[1].setColor((int)red, (int)green, (int)blue);
                et = findViewById(R.id.playerName);
                str = et.getText().toString();
                if(str.equals(" "))
                {
                    et.requestFocus();
                }
                else
                {
                    MainActivity.players[1].setName(str);
                }
            }

            this.finish();
        }

    }
}

