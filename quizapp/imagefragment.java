package com.example.quizapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

public class imagefragment extends Fragment
{
    String[] picIds = new String[5];                    // holds the values for the pictures
    String[][] questionsAnswers = new String[5][5];     // holds the questions and the answers
    String[] picDescriptions = new String[5];           // holds the text description of the image

    public imagefragment()
    {
        // empty constructor
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_image_question, container, false);
        return v;   // return a referance to the view
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();
        String [] details = res.getStringArray(R.array.Countries);
        String [] mod = new String[7];      // 7 because first element is the png and the second is the text description
        String entry = "";                  // first entry of the array
        ImageView iv = view.findViewById(R.id.imageQuestion);
        TextView tv = view.findViewById(R.id.tvImageDescription);
        Log.i("Contents of array", details[0]);
        entry = details[0];                 // first entry of the array
        mod = entry.split(":");     // image.png, description, answers
        Log.i("Content of mod", mod[0]);

        // this doesn't really matter since the image will get replaced anyway
        Context context = iv.getContext();
        int id = context.getResources().getIdentifier(mod[0], "drawable", context.getPackageName());
        iv.setImageResource(id);
        tv.setText(mod[1]);

    }

    @Nullable
    @Override
    public View getView()
    {
        return super.getView();
    }
}
