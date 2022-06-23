package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private QuizAdapter qa;                     // my quiz adapter
    public static ArrayList<Quiz> quizes;       // array list of quizes
    private String[] details = new String[6];   // arra to hold the data of the entry
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.quizList);
        quizes = new ArrayList<>(); // use an array list for variable size of quiz array
        qa = new QuizAdapter(this,quizes);
        lv.setAdapter(qa);          // set the adapter to my custom one
        // this method does 3 things
        /*
        * 1. gets the quizes from the quizArray
        * 2. gets the data for each of the quizes
        * 3. updates the listView with the data
        * */
        Resources res = getResources();                             // get the resources
        String[] theQuizes = res.getStringArray(R.array.quizes);    // get the quizes array
        for(int i = 0; i < theQuizes.length; i++)
        {
            String entry = theQuizes[i];    // get the entry from the array
            details = entry.split(":");
            Quiz q = new Quiz();            // make a new quiz
            Log.i("Details of quiz", details[0] + " " + details[1] + " " + details[2] + " " + details[3]);
            q.setQuizName(details[0]);      // initialize the quiz name
            q.setAuthor(details[1]);        // initialize the author
            q.setDateCreated(details[2]);   // initialize the date
            q.setFormat(details[3]);        // initialize the format
            entry = details[0];             // entry is the quiz name

            // important line here
            // get the data of the quiz
            details = res.getStringArray(textquestionfragment.getResId(entry, R.array.class));
            Log.i("Here after res", details[0]);
            String[] copy = details;                // store a copy of the details
            String[] questions = new String[5];     // array to hold the questions
            String[] answers = new String[5];       // array to hold the answers
            for(int k = 0; k < copy.length; k++)
            {
                String e = copy[k];             // initial entry
                Log.i("E val", e);
                details = e.split(":");   // split the contents of the array
                questions[k] = details[0];      // set the question
                Log.i("Values of array",copy[k]);
                Log.i("The question", questions[k]);
                for(int num = 0; num < answers.length; num++)
                {
                    answers[num] = details[num+1];  // set the answer
                    Log.i("Answer", "Answer " + num + " = " + answers[num]);
                }
                q.setQuestion(questions[k],k);  // set the question at the current index
                q.setAnswers(answers,k);        // set the answers of the current index
            }
            // add 4 instances of each quiz
            for(int num = 0; num < 4; num++)
                quizes.add(q);

        }

        // set the listener for the list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Quiz q = (Quiz)qa.getItem(position);
                Intent myIntent = new Intent(MainActivity.this, FragmentActivity.class);    // intent to go the fragment activity

                // put the data for the quiz that we clicked on
                myIntent.putExtra("format", q.getFormat());
                myIntent.putExtra("author", q.getAuthor());
                myIntent.putExtra("date", q.getDateCreated());
                myIntent.putExtra("quizName", q.getQuizName());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // method used for debugging only
    public void play(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,FragmentActivity.class);     // intent to go to the fragment activity
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);      // make it so we can go back out of the activity
        startActivity(myIntent);                                // start the activity

    }
}
