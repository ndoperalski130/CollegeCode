package com.example.quizapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class answerfragment extends Fragment
{
    private Resources res;
    private String [] quizes;
    private String [] details;

    public answerfragment()
    {

        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_answer, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        res = getResources();
        quizes = res.getStringArray(R.array.quizes);
        TextView tv = view.findViewById(R.id.tvQuizName);

        // gets the name of the quiz from the details
        String entry = quizes[0];
        details = entry.split(":");
        tv.setText(details[0]);

        // gets the author from the details
        tv = view.findViewById(R.id.tvAuthor);
        tv.setText(details[1]);

        // gets the date from the details
        tv = view.findViewById(R.id.tvDateCreated);
        tv.setText(details[2]);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
}
