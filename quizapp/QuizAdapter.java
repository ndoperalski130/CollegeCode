package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizAdapter extends BaseAdapter
{
    private Context context;                    // current context that we're in
    private ArrayList<Quiz> quizArrayList;      // array list for quizes

    // public constructor for the quiz adapter that accepts a context and array list
    public QuizAdapter(Context context, ArrayList<Quiz> q)
    {
        this.context = context;
        this.quizArrayList = q;
    }

    // returns the size of the array list
    @Override
    public int getCount()
    {
        return quizArrayList.size();
    }

    // returns the item that we clicked
    @Override
    public Object getItem(int position)
    {
        return quizArrayList.get(position);
    }

    // returns the position of the item we clicked
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    // returns the converted view in the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // always returns true
        // create the view if the view is null
        if(convertView == null)
        {
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_layout, parent, false);

        }
        // gets the quiz and assigns the data into the text fields
        Quiz q = (Quiz) getItem(position);
        TextView tv1 = convertView.findViewById(R.id.tvQuizName);
        TextView tv2 = convertView.findViewById(R.id.tvQuizAuthor);
        TextView tv3 = convertView.findViewById(R.id.tvDateCreated);
        TextView tv4 = convertView.findViewById(R.id.tvFormat);

        // assigning data

        tv1.setText(q.getQuizName());
        tv2.setText(q.getAuthor());
        tv3.setText(q.getDateCreated());
        tv4.setText(q.getFormat());

        return convertView;
    }

}
