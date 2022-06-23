package com.example.quizapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FragmentActivity extends AppCompatActivity
{
    private Quiz q = new Quiz();    // quiz to hold the data for a text quiz
    private int score = 0;          // our score as we go through the program
    private int questionNumber = 0; // public question number used throughout the class
    Resources res;                  // resources variable
    private String quizName;        // string to hold the name of the quiz
    private String format;          // string to hold the format of the quiz
    private String author;          // string to hold the author of the quiz
    private String date;            // string to hold creation of quiz
    private String[] details;       // array to hold the details of the quiz or quiz data

    // a new countdown timer is initialized and sent a max time of 10 seconds with a tick every 1
    private CountDownTimer timer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            bar.setProgress((int)millisUntilFinished/100);      // set the progress bar for every tick
        }

        @Override
        public void onFinish()
        {
            bar.setProgress(0);     // on finish, empty the progress bar
            nextQuestion();         // go to next question
        }
    };

    private String[][] questionAnswers = new String[5][5];  // double string array to hold the info for a text question
    private String correctAnswer;           // variable to hold the correct answer
    private Random rand = new Random();     // random variable for random question placement

    private ProgressBar bar;                // a reference to our progress bar

    // this function is essentially a duplicate of the play function except for a few things
    /*
    * 1. checks if the user ran out of time and forces the next question to appear
    * 2. checks if the user is correct
    * 3. resets the timer at the end of the function*/
    public void nextQuestion()
    {
        int randNum = rand.nextInt(4);
        if(format.equals("text"))
        {
            // make sure the view being clicked is a button
            //if(v instanceof Button)
            {
                Button b = findViewById(R.id.answerOne);
                String val = (String)b.getText();
                if(val.equals(correctAnswer))
                {
                    Toast.makeText(getApplicationContext(), "Out of time!", Toast.LENGTH_SHORT).show();
                   // score++;
                    TextView tv = this.findViewById(R.id.tvScore);
                    tv.setText("User score: " + score);
                    tv = this.findViewById(R.id.tvQuestion);

                    // increment question number to get the next question
                    questionNumber++;
                    // check if we're out of range
                    if (questionNumber == 5)
                        quit();

                    else {
                        // update all the text on the buttons and the question from the quiz
                        tv.setText(q.getQuestion(questionNumber));

                        if(randNum == 0)
                        {
                            b.setText(questionAnswers[questionNumber][1]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if (randNum == 1)
                        {
                            b.setText(questionAnswers[questionNumber][2]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if (randNum == 2)
                        {
                            b.setText(questionAnswers[questionNumber][3]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else
                        {
                            b.setText(questionAnswers[questionNumber][4]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][1]);
                        }


                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Out of time!", Toast.LENGTH_SHORT).show();
                    TextView tv = this.findViewById(R.id.tvQuestion);

                    // increment question number to get the next question
                    questionNumber++;
                    // check if we're out of range
                    if (questionNumber == 5)
                        quit();

                    else {
                        // update all the text on the buttons and the question from the quiz
                        tv.setText(q.getQuestion(questionNumber));
                        b = findViewById(R.id.answerOne);
                        b.setText(questionAnswers[questionNumber][1]);
                        correctAnswer = questionAnswers[questionNumber][1];
                        b = findViewById(R.id.answerTwo);
                        b.setText(questionAnswers[questionNumber][2]);
                        b = findViewById(R.id.answerThree);
                        b.setText(questionAnswers[questionNumber][3]);
                        b = findViewById(R.id.answerFour);
                        b.setText(questionAnswers[questionNumber][4]);

                    }


                }
            }

        }
        else
        {
//            if(v instanceof Button)
            {
                Button b = findViewById(R.id.answerOne);
                String val = (String)b.getText();

                String entry = "";
                String[] mod = new String[7];

                if(val.equals(correctAnswer))
                {
                    questionNumber++;
                    if(questionNumber == 5)
                    {
                        quit();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Out of time!", Toast.LENGTH_SHORT).show();
                        ImageView iv = findViewById(R.id.imageQuestion);
                        entry = details[questionNumber];
                        Log.i("Content of entry", entry);
                        mod = entry.split(":");

                        int resId = getResources().
                                getIdentifier(mod[0].split("\\.")[0], "drawable", getApplicationInfo().packageName);
                        iv.setImageResource(resId);

                        TextView tv = findViewById(R.id.tvImageDescription);
                        tv.setText(mod[1]);

                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if (randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[5]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[2]);
                        }

                     //   score++;

                        tv = this.findViewById(R.id.tvScore);
                        tv.setText("User score: " + score);
                    }

                }
                else
                {
                    questionNumber++;
                    if(questionNumber == 5)
                        quit();
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Out of time!", Toast.LENGTH_SHORT).show();
                        ImageView iv = findViewById(R.id.imageQuestion);
                        entry = details[questionNumber];
                        mod = entry.split(":");

                        int resId = getResources().
                                getIdentifier(mod[0].split("\\.")[0], "drawable", getApplicationInfo().packageName);
                        iv.setImageResource(resId);

                        TextView tv = findViewById(R.id.tvImageDescription);
                        tv.setText(mod[1]);

                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if (randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[5]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[2]);
                        }
                    }

                }
            }
        }
        timer.cancel();
        bar.setProgress(100);
        timer.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        res = getResources();                                   // get the resources
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);             // set the content view to our layout
        Intent myIntent = getIntent();                          // get the intent from the previous activity

        // get the data from the previous activity via the intent
        format = myIntent.getStringExtra("format");
        quizName = myIntent.getStringExtra("quizName");
        author = myIntent.getStringExtra("author");
        date = myIntent.getStringExtra("date");

        FragmentManager fm = getFragmentManager();          // initialize the fragment manager
        // will always return true
        // if the fragment is null, set it up
        if(fm.findFragmentById(R.id.placeHolder) == null)
        {
            // if the format of the quiz is a text quiz
            if(format.equals("text"))
            {
                FragmentTransaction ft = fm.beginTransaction();             // start a new transaction
                textquestionfragment textFrag = new textquestionfragment(); // create a new textfragment
                ft.replace(R.id.placeHolder, textFrag);                     // replace the placeholder with our new textfragment
                ft.commit();                                                // commit it to the screen

            }
            // otherwise it's an image quiz
            else if(format.equals("image"))
            {
                FragmentTransaction ft = fm.beginTransaction();             // start a new transaction
                imagefragment imagefrag = new imagefragment();              // create a new imagefragment
                ft.replace(R.id.placeHolder, imagefrag);                    // replace the placeholder with our new imagefragment
                ft.commit();                                                // commit it to the screen

            }

        }
        TextView tv = findViewById(R.id.tvQuizName);
        tv.setText(quizName);                   // set the quiz name
        tv = findViewById(R.id.tvAuthor);
        tv.setText(author);                     // set the author name
        tv = findViewById(R.id.tvDateCreated);
        tv.setText(date);                       // set the date of creation
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        play();             // this must be in onStart because onCreate only does views and initializing
    }

    // done
    // written by Noah Doperalski
    public void play()
    {
        timer.start();      // start the timer right away

        // if the quiz is a text format
        if(format.equals("text"))
        {
            TextView tv = new TextView(this);       // dummy text view
            Button b = new Button(this);            // dummy button

            // look for the quiz name text field and initialize
            tv = findViewById(R.id.tvQuizName);
            tv.setText(quizName);

            // set up for the remainder of the code block
            // find the question text and the first button
            tv = this.findViewById(R.id.tvQuestion);
            b = this.findViewById(R.id.answerOne);
            for(int i = 0; i < MainActivity.quizes.size(); i++)
            {
                // find the quiz in the list
                q = MainActivity.quizes.get(i);

                // if found, leave
                if(q.getQuizName().equals(quizName))
                    break;
            }
            Log.i("After getting quiz", "The question is: ");

            // this block initializes the buttons and the question
            questionAnswers = q.getData();                          // get the data from the quiz object
            tv.setText(q.getQuestion(questionNumber));              // set the question

            // set all the buttons text according to their spots in the array
            b.setText(questionAnswers[questionNumber][1]);
            correctAnswer = questionAnswers[questionNumber][1];
            b = findViewById(R.id.answerTwo);
            b.setText(questionAnswers[questionNumber][2]);
            b = findViewById(R.id.answerThree);
            b.setText(questionAnswers[questionNumber][3]);
            b = findViewById(R.id.answerFour);
            b.setText(questionAnswers[questionNumber][4]);

            tv = this.findViewById(R.id.tvScore);
            tv.setText("User score: " + score);
        }
        // otherwise is an image quiz
        else
        {
            ImageView iv = this.findViewById(R.id.imageQuestion);
            TextView tv = this.findViewById(R.id.tvImageDescription);
            String[] mod = new String[7];           // image.png, image description
            String entry = "";                      // holds the entry in the array

            // this is one of the most important lines
            // get the details about the quiz from the array
            details = res.getStringArray(textquestionfragment.getResId(quizName, R.array.class));

            entry = details[0];
            Log.i("Content of entry", entry);
            mod = entry.split(":");             // image.png, image description
            Log.i("Content of mod0", mod[0]);

            // get the resource id for the first picture
            int resId = getResources().
                    getIdentifier(mod[0].split("\\.")[0], "drawable", getApplicationInfo().packageName);
            iv.setImageResource(resId);         // set the resource of the image to the id
            tv.setText(mod[1]);                 // initialize the image description

            // find all the buttons and initialize them with the answers to the first question
            Button b = this.findViewById(R.id.answerOne);
            correctAnswer = mod[2];
            b.setText(mod[2]);
            b = this.findViewById(R.id.answerTwo);
            b.setText(mod[3]);
            b = this.findViewById(R.id.answerThree);
            b.setText(mod[4]);
            b = this.findViewById(R.id.answerFour);
            b.setText(mod[5]);

            tv = this.findViewById(R.id.tvQuizName);    // find the quiz name text
            tv.setText(quizName);                       // initialize the quiz name

            tv = this.findViewById(R.id.tvScore);       // find the score
            tv.setText("User score: " + score);         // initialize the score
        }
        bar = findViewById(R.id.progressBar);   // assign the progress bar
        bar.setProgress(100);                   // initialize the progress

    }



    @Override
    protected void onResume() {

        super.onResume();
    }

    // done
    // written by Noah Doperalski
    public void isCorrect(View v)
    {
        int randNum = rand.nextInt(4);      // generate a random number from 0-3
        if(format.equals("text"))
        {
            // make sure the view being clicked is a button
            if(v instanceof Button)
            {
                // local variables for text quiz
                Button b = (Button) v;              // button that we clicked
                String val = (String)b.getText();   // text of the button
                // if our answer is the correct answer
                if(val.equals(correctAnswer))
                {
                    Toast.makeText(getApplicationContext(), "That's correct!", Toast.LENGTH_SHORT).show();
                    score = score + bar.getProgress()/10;           //increment the score based on the progress bar
                    TextView tv = this.findViewById(R.id.tvScore);
                    tv.setText("User score: " + score);             // display the score
                    tv = this.findViewById(R.id.tvQuestion);

                    // increment question number to get the next question
                    questionNumber++;
                    // check if we're out of range
                    if(questionNumber == 5)
                        quit();     // quit if we are

                    else
                    {
                        tv.setText(q.getQuestion(questionNumber));      // update the question based on the question number
                        // check for the random number and update the buttons and text
                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][1]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][2]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if(randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][3]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][4]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][1]);
                        }
                        // reset the timer and progress bar to what they were
                        // need to cancel the timer in order to start it again
                        timer.cancel();
                        bar.setProgress(100);
                        timer.start();
                    }

                }
                // the user is incorrect otherwise
                else
                {
                    Toast.makeText(getApplicationContext(), "That's not correct.", Toast.LENGTH_SHORT).show();
                    TextView tv = this.findViewById(R.id.tvQuestion);

                    // increment question number to get the next question
                    questionNumber++;
                    // check if we're out of range
                    if(questionNumber == 5)
                        quit();     // if we are quit

                    else
                    {
                        // update all the text on the buttons and the question from the quiz
                        tv.setText(q.getQuestion(questionNumber));

                        // look for the random answer
                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][1]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][2]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else if(randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][3]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][1]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][4]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            b.setText(questionAnswers[questionNumber][4]);
                            correctAnswer = questionAnswers[questionNumber][1];
                            b = findViewById(R.id.answerTwo);
                            b.setText(questionAnswers[questionNumber][2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(questionAnswers[questionNumber][3]);
                            b = findViewById(R.id.answerFour);
                            b.setText(questionAnswers[questionNumber][1]);
                        }

                        // reset the timer and progress bar to what they were
                        // need to cancel the timer in order to start it again
                        timer.cancel();
                        bar.setProgress(100);
                        timer.start();
                    }



                }
            }

        }
        else
        {
            if(v instanceof Button)
            {
                // local variables to function of image quiz
                Button b = (Button) v;              // tells us what button was pressed
                String val = (String)b.getText();   // tells us the text of the button

                String entry = "";                  // gets the entry in the array to get new info
                String[] mod = new String[7];       // will eventually hold the info from entry

                // if user answer is the correct answer
                if(val.equals(correctAnswer))
                {
                    questionNumber++;       // increment question
                    // check we're not out of the question limit
                    if(questionNumber == 5)
                    {
                        quit();     // need to go out of the game loop
                    }
                    // user is correct
                    else
                    {
                        Toast.makeText(getApplicationContext(), "That's correct!", Toast.LENGTH_SHORT).show();
                        ImageView iv = findViewById(R.id.imageQuestion);
                        entry = details[questionNumber];            // get the details of the next question
                        Log.i("Content of entry", entry);
                        mod = entry.split(":");              // split array to find contents

                        // get the id of the next picture
                        int resId = getResources().
                                getIdentifier(mod[0].split("\\.")[0], "drawable", getApplicationInfo().packageName);
                        iv.setImageResource(resId);         // set the image resource to the picture

                        // update the image description
                        TextView tv = findViewById(R.id.tvImageDescription);
                        tv.setText(mod[1]);

                        /*
                         * 1. check what the random number is
                         * 2. assign the correct button depending on the random num
                         * 3. update the buttons and text*/
                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if (randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[5]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[2]);
                        }

                        // reset the timer and progress bar to what they were
                        // need to cancel the timer in order to start it again
                        timer.cancel();
                        bar.setProgress(100);
                        timer.start();
                        score = score + bar.getProgress()/10;   // calculate score based on progress bar

                        tv = this.findViewById(R.id.tvScore);
                        tv.setText("User score: " + score);     // update score
                    }

                }
                else
                {
                    // increment our question
                    questionNumber++;
                    // check that we're not out of bounds of the question limit
                    if(questionNumber == 5)
                        quit();     // quit if we are
                    // else say that the user is incorrect and update the text
                    else
                    {
                        Toast.makeText(getApplicationContext(), "That's incorrect.", Toast.LENGTH_SHORT).show();
                        ImageView iv = findViewById(R.id.imageQuestion);
                        entry = details[questionNumber];            // get new entry from details
                        mod = entry.split(":");              // split the array for information

                        // get the resource of the picture
                        int resId = getResources().
                                getIdentifier(mod[0].split("\\.")[0], "drawable", getApplicationInfo().packageName);
                        iv.setImageResource(resId);         // set the image resource to the new picture

                        // update the image description
                        TextView tv = findViewById(R.id.tvImageDescription);
                        tv.setText(mod[1]);

                        /*
                        * 1. check what the random number is
                        * 2. assign the correct button depending on the random num
                        * 3. update the buttons and text*/
                        if(randNum == 0)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if(randNum == 1)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else if (randNum == 2)
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[2]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[5]);
                        }
                        else
                        {
                            b = findViewById(R.id.answerOne);
                            correctAnswer = mod[2];
                            b.setText(mod[5]);
                            b = findViewById(R.id.answerTwo);
                            b.setText(mod[3]);
                            b = findViewById(R.id.answerThree);
                            b.setText(mod[4]);
                            b = findViewById(R.id.answerFour);
                            b.setText(mod[2]);
                        }

                        // reset the timer and progress bar to what they were
                        // need to cancel the timer in order to start it again
                        timer.cancel();
                        bar.setProgress(100);
                        timer.start();
                    }

                }
            }
        }

    }
    // done
    // written by Noah Doperalski
    public void quit()
    {
        Intent myIntent = new Intent(FragmentActivity.this,ScoreActivity.class);    // intent to go to scoreActivity
        myIntent.putExtra("score", score);  // save the score
        startActivity(myIntent);                  // launch the activity
        timer.cancel();                           // IMPORTANT! Stop the timer in order to proceed.  Memory error otherwise
        this.finish();                            // finish the activity
    }
}
