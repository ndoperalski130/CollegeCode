package com.example.adapterlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PlayerAdapter pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Player> a = new ArrayList<Player>();
        pa = new PlayerAdapter(this, a);
        ListView playerListView = (ListView)findViewById(R.id.lvPlayerList);
        playerListView.setAdapter(pa);
        Resources res = getResources();
        String [] allPlayers = res.getStringArray(R.array.thePlayers);
        for (int i = 0; i < allPlayers.length; i++){
            String [] data = allPlayers[i].split(":");
            int drawID = -1;
            if (data.length == 4)
            {
                drawID = res.getIdentifier(data[3], "drawable", getPackageName());
                Log.i("here", data[3] + " "  + getPackageName() + " " + drawID);
            }

            Player b = new Player(Integer.parseInt(data[0]), data[1], data[2], drawID);
            a.add(b);
        }

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id)
            {
                Player p = (Player)pa.getItem(whichItem);
                Log.i("HERE", p.getNumber() + " " + p.getName() + " " + p.getPosition());
                Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);

                myIntent.putExtra("Number", p.getNumber());
                myIntent.putExtra("Name", p.getName());
                myIntent.putExtra("Position", p.getPosition());
                myIntent.putExtra("Picture", p.getpicID());
//                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
    }

}

