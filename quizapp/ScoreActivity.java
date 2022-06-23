package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);        // initialize the view
        Intent myIntent = getIntent();                  // get the intent from the previous activity
        int score = myIntent.getIntExtra("score", 0);   // get the score from the fragments
        TextView tv = findViewById(R.id.tvFinalScore);  // find the text field
        tv.setText("Your final score is: " + score);    // set the text field
    }
    public void returnToMain(View v)
    {
        Intent myIntent = new Intent(this, MainActivity.class);     // intent to return to main
        startActivity(myIntent);    // start the activity
        this.finish();              // finish this activity
    }
}
