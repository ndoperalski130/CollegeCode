package com.example.quizapp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class textquestionfragment extends Fragment
{
    private Resources res;          // get the resources
    private String [] quizes;       // holds the quizes?
    private String [] details;      // holds the details


    public textquestionfragment()
    {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        return v;       // return a referance to the view
    }

    // essentially the same as the image fragment
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        res = getResources();
        quizes  = res.getStringArray(R.array.quizes);
        String entry = quizes[0];
        details = entry.split(":");
        entry = details[0];
        int resId = getResId(entry, R.array.class);
        details = res.getStringArray(resId);
        entry = details[0];
        String [] mod = entry.split(":");

        TextView tv = view.findViewById(R.id.tvQuestion);
        tv.setText(mod[0]);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
    // adapted from stackoverflow
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
