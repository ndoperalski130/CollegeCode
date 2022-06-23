package com.example.quizapp;

public class Quiz
{
    private String quizName;                // holds the name of the quiz
    private String author;                  // holds the author of the quiz
    private String dateCreated;             // holds the date of creation
    private String format;                  // holds the format
    private String[][] questionAnswers;     // only for text quizes.  holds the question and answers

    // default constructor initialized with dummy variables
    public Quiz()
    {
        quizName = "USHistory";
        author = "Noah";
        dateCreated = "November 1";
        format = "text";
        questionAnswers = new String[5][5];
        for(int i = 0; i < questionAnswers.length; i++)
        {
            questionAnswers[i][0] = "Question";
            for(int k = 0; k < questionAnswers.length; k++)
            {
                if(k == 0)
                    questionAnswers[i][k] = "Correct Answer";
                else
                    questionAnswers[i][k] = "Some Answer";
            }
        }
    }

    // constructor with arguments sent to it
    public Quiz(String q, String a, String d, String f, String[][] qa)
    {
        quizName = q;
        author = a;
        dateCreated = d;
        format = f;
        questionAnswers = qa;
    }
    public String getQuizName()
    {
        return quizName;
    }           // return the quiz name
    public String getAuthor()
    {
        return author;
    }               // return the author
    public String getDateCreated()
    {
        return dateCreated;
    }     // return the date of creation
    public String getFormat()
    {
        return format;
    }               // return the format
    public void setQuizName(String q)
    {
        quizName = q;
    }        // set the quiz name
    public void setAuthor(String a)
    {
        author = a;
    }            // set the author
    public void setDateCreated(String d)
    {
        dateCreated = d;
    }  // set the date of creation
    public void setFormat(String f)
    {
        format = f;
    }            // set the format

    // returns the question at the given index
    public String getQuestion(int index)
    {
        return questionAnswers[index][0];
    }
    // returns the entire question array
    public String[] getQuestions()
    {
        String[] questions = new String[5];
        for(int i = 0; i < questions.length; i++)
        {
            questions[i] = questionAnswers[i][0];
        }
        return questions;
    }
    // returns the entire data array
    public String[][] getData()
    {
        return questionAnswers;
    }
    // returns a string array of answers at the given index
    public String[] getAnswers(int index)
    {
        String[] answers = new String[5];
        for(int i = 0; i < answers.length; i++)
        {
            answers[i] = questionAnswers[index][i];
        }
        return answers;
    }

    // sets the question at a certain index
    public void setQuestion(String q, int index)
    {
        questionAnswers[index][0] = q;
    }

    // set the answers of a certain index
    public void setAnswers(String[] a, int index)
    {
        for(int i = 1; i < 5; i++)
        {
            questionAnswers[index][i] = a[i-1];
        }
    }
}
