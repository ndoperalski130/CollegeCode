package com.example.adapterlab;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        LinearLayout ll = new LinearLayout(this);
        Intent myIntent = getIntent();
        int number = myIntent.getIntExtra("Number", 0);
        String name = myIntent.getStringExtra("Name");
        String position = myIntent.getStringExtra("Position");
        int picId = myIntent.getIntExtra("Picture", 0);

        TextView num = new TextView(this);
        num.setText(Integer.toString(number));
        TextView stringName = new TextView(this);
        stringName.setText(name);
        TextView positionText = new TextView(this);
        positionText.setText(position);
        ImageView picture = new ImageView(this);
        Resources res = getResources();
        picture.setImageDrawable(res.getDrawable(picId));

        ll.addView(num);
        ll.addView(stringName);
        ll.addView(positionText);
        ll.addView(picture);

        Button btn = new Button(this);
        btn.setText("Quit");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll.addView(btn);

        setContentView(ll);

        super.onCreate(savedInstanceState);
    }
}
