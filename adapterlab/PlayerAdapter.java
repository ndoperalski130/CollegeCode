package com.example.adapterlab;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Player> team; //data source of the list adapter

    //public constructor
    public PlayerAdapter(Context context, ArrayList<Player> items) {
        this.context = context;
        this.team = items;
    }

    @Override  //returns total of items in the list
    public int getCount() {
        Log.i("Here", "Inside of getCount");
        return team.size();
    }

    @Override   //returns list item at the specified position
    public Object getItem(int position) 	{
        return team.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {   // if not null, reuse existing for efficiency
            LayoutInflater inf =       (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.layout_listview_player, parent, false);
        }

        // get current item to be displayed
        Player current = (Player) getItem(position);
        Resources res = parent.getResources();
        // get the TextView for item name and item description
        TextView textViewName = (TextView) convertView.findViewById(R.id.tvPlayerName);
        TextView textViewPosition = (TextView)
                convertView.findViewById(R.id.tvPlayerPosition);
        TextView textViewNumber = (TextView)
                convertView.findViewById(R.id.tvPlayerNumber);
        //sets the text for item name and item description from the current item object
        textViewName.setText(current.getName());
        textViewPosition.setText(current.getPosition());
        textViewNumber.setText(current.getNumber()+"");
        ImageView ivPic = convertView.findViewById(R.id.ivPicture);

        if (current.getpicID() != -1)
           ivPic.setImageDrawable(res.getDrawable(current.getpicID()));
        else
            ivPic.setImageDrawable(res.getDrawable(R.drawable.pacman));
        Log.i("Here", "Inside of getView");

        return convertView; // returns the view for the current row
    }
}

