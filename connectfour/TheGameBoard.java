package com.example.connectfour;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.widget.TextView;

public class TheGameBoard extends AppCompatActivity {
    private int columns, rows;
    public static final int EMPTY = 0;
    public static final int DRAW = -1;
    public static final int BLACK = 1;
    public static final int RED = 2;
    // public static final int INVALID = -2;

    private ConnectFourGame game;
    private Random r;
    private int result, player;
    private boolean isPlayer1 = true;

    private FrameLayout.LayoutParams params, initialParams;
    private DisplayMetrics metrics;
    private int startX, startY;
    private int startTouchX, startTouchY;
    private View.OnTouchListener myListener;
    private GestureDetector.SimpleOnGestureListener dtListener;
    private ImageView iv;
    //private ImageView b = findViewById(R.id.theBoard);

    // stuff for the animation detection and execution
    private GestureDetector detector;
    ObjectAnimator animator;
    float heightCalc, initialHeight;

    ImageView[] chips = new ImageView[42];
    int chipCounter = 0;

    boolean[][] updateBoard = new boolean[7][6];

    // does a lot of stuff
//    Creates the views that we need for the game and everything.
//    Sets up the board, chips, and animations.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for(int i = 0; i < 42; i++)
        {
            chips[i] = new ImageView(this);
        }
        setContentView(R.layout.game);
        params = new FrameLayout.LayoutParams(300, 1100);
        initialParams = new FrameLayout.LayoutParams(300, 1100);

        initialHeight = params.topMargin;

        heightCalc = initialHeight;
        initialParams = params;

        // listener for the actual chip
        myListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        startX = params.leftMargin;
                        startY = params.topMargin;
                        startTouchX = (int) event.getRawX();
                        startTouchY = (int) event.getRawY();
                        Log.i("Here", "In press down");
                    }
                    case MotionEvent.ACTION_MOVE: {

                        params.leftMargin = startX + (int) event.getRawX() - startTouchX;
                        //params.topMargin = startY + (int) event.getRawY() - startTouchY;
                        v.setLayoutParams(params);
//                        Log.i("Here", "In move");
//                        Log.i("Here1", "ActionMove: v = " + v + "; Event = " + event);
//                        Log.i("Here2", "ActionMove: " + (int)event.getX() + "  " + (int)event.getY());
//                        Log.i("Here3", "ActionMove Params: " + params.leftMargin + "  " + params.topMargin);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        {
                            Log.i("Here", "In up");
                        }

                }
                return true;
            }

        };
        // sets the detector
        dtListener = new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e)
            {
                Log.i("Here", "In singleTap");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e)
            {
                Log.i("here", "In doubleTap");
                dropChip();
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e)
            {
                Log.i("Here", "In doubleTapEvent");
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
            {
                Log.i("Here", "In onFling");
                if(velocityY > velocityX)
                {
                    View v = findViewById(R.id.theView);
                    if(!isPlayer1)
                        v.setBackgroundColor(MainActivity.players[0].getColor());
                    else
                        v.setBackgroundColor(MainActivity.players[1].getColor());
                    isPlayer1 = !isPlayer1;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        detector = new GestureDetector(this, dtListener);

        // getting the new image view to appear on the screen
        firstChip();
        // getting name and wins and losses for player one
        drawText();

        // actually create the animator for the object
        animator = new ObjectAnimator();

//        game = new ConnectFourGame();
//
//        columns = ConnectFourGame.COLUMNS;
//        rows = ConnectFourGame.ROWS;
//        Log.i(ConnectFourGame.ME, rows + " " + columns);
//
//        game.InitBoard();
//        game.DrawBoard();
//
//        r = new Random();
//        player = RED;

    }

    public void takeTurn(View v) {
        int col = -1, row = -1;
        player = player % 2 + 1;
        Log.i(ConnectFourGame.ME, "inside takeTurn, player " + player);

        // plays randomly

        col = r.nextInt(ConnectFourGame.COLUMNS);
        row = game.DropChipIntoColumn(col, player);
        while (ConnectFourGame.INVALID == row) {
            Log.i(ConnectFourGame.ME, "looping");
            col = r.nextInt(ConnectFourGame.COLUMNS);
            row = game.DropChipIntoColumn(col, player);

        }
        Log.i(ConnectFourGame.ME, "row: " + row + "  column: " + col);

        result = game.GameOver(row, col, player);
        Log.i(ConnectFourGame.ME, "the result is" + result);
        if (result != 0)
            Log.i(ConnectFourGame.ME, "GameOver!");
        // NOTE: I do not end the game so that you can keep testing ... you should
        // fix this when finished!
        game.DrawBoard();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // todo:
    // keep the chip on screen
    // reset the chip to the top

    public void dropChip()
    {
        int[] whereTo = new int[2];
        AccelerateInterpolator ac = new AccelerateInterpolator();
        ImageView iv1 = chips[chipCounter];
        animator = ObjectAnimator.ofFloat(iv1, "translationY", 950f);
        animator.setInterpolator(ac);
//        params.topMargin += 100;

        if(isOverlapping(iv1, findViewById(R.id.rowOne)))
        {
            findViewById(R.id.rowOne).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowTwo)))
        {
            findViewById(R.id.rowTwo).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowThree)))
        {
            findViewById(R.id.rowThree).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowFour)))
        {
            findViewById(R.id.rowFour).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowFive)))
        {
            findViewById(R.id.rowFive).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowSix)))
        {
            findViewById(R.id.rowSix).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);
            animator.start();
        }
        else if(isOverlapping(iv1, findViewById(R.id.rowSeven)))
        {
            findViewById(R.id.rowSeven).getLocationOnScreen(whereTo);
            iv1.setX(whereTo[0] - 130f);

            animator.start();
        }

        updateText();
        chips[chipCounter].setOnTouchListener(null);
        chips[chipCounter].setEnabled(false);
        iv1.setEnabled(false);

        chipCounter++;

        if(isPlayer1)
        {
            int col = Color.rgb(MainActivity.players[1].getRed(), MainActivity.players[1].getGreen(), MainActivity.players[1].getBlue());
            chips[chipCounter].setColorFilter(col, PorterDuff.Mode.SRC_ATOP);
            chips[chipCounter].setImageResource(R.drawable.circle);

            chips[chipCounter].setOnTouchListener(myListener);
            chips[chipCounter].setScaleX(0.5f);
            chips[chipCounter].setScaleY(0.5f);

            addContentView(chips[chipCounter], params);
        }
        else
        {
            int col = Color.rgb(MainActivity.players[0].getRed(), MainActivity.players[0].getGreen(), MainActivity.players[0].getBlue());
            chips[chipCounter].setColorFilter(col, PorterDuff.Mode.SRC_ATOP);
            chips[chipCounter].setImageResource(R.drawable.circle);

            chips[chipCounter].setOnTouchListener(myListener);
            chips[chipCounter].setScaleX(0.5f);
            chips[chipCounter].setScaleY(0.5f);

            addContentView(chips[chipCounter], params);
        }

        isPlayer1 = !isPlayer1;
        updateText();

        Log.i("here", "Is the view overlapping with the first row?" + isOverlapping(iv1, findViewById(R.id.rowOne)));
    }
    // done
    public void updateText()
    {
        TextView tv = findViewById(R.id.player1Name);
        if(!isPlayer1)
        {
            tv.setBackgroundColor(Color.GREEN);
            tv = findViewById(R.id.player2Name);
            tv.setBackgroundColor(getResources().getColor(R.color.CoolBlue));
        }
        else
        {
            tv.setBackgroundColor(getResources().getColor(R.color.CoolBlue));
            tv = findViewById(R.id.player2Name);
            tv.setBackgroundColor(Color.GREEN);
        }
        isPlayer1 = !isPlayer1;
    }
    // done
    public void firstChip()
    {
//        chips[chipCounter] = new ImageView(this);

        int col = Color.rgb(MainActivity.players[0].getRed(), MainActivity.players[0].getGreen(), MainActivity.players[0].getBlue());
        chips[chipCounter].setColorFilter(col, PorterDuff.Mode.SRC_ATOP);
        chips[chipCounter].setImageResource(R.drawable.circle);

        chips[chipCounter].setOnTouchListener(myListener);
        chips[chipCounter].setScaleX(0.5f);
        chips[chipCounter].setScaleY(0.5f);

        addContentView(chips[chipCounter], params);
    }
    // done
    public void drawText()
    {
        TextView tv = findViewById(R.id.player1Name);
        tv.setText(MainActivity.players[0].getName());
        tv = findViewById(R.id.player1WL);
        tv.setText("W: " + MainActivity.players[0].getWins() + "/L: " + MainActivity.players[0].getLosses());

        // getting name and wins and losses for player two
        tv = findViewById(R.id.player2Name);
        tv.setText(MainActivity.players[1].getName());
        tv = findViewById(R.id.player2WL);
        tv.setText("W: " + MainActivity.players[1].getWins() + "/L: " + MainActivity.players[1].getLosses());
    }
    // done
    public void goBack(View v)
    {
        if(isPlayer1)
        {
            MainActivity.players[0].incrementScore(!isPlayer1);
            MainActivity.players[1].incrementScore(isPlayer1);
        }
        else {
            MainActivity.players[0].incrementScore(isPlayer1);
            MainActivity.players[1].incrementScore(!isPlayer1);
        }
        this.finish();
    }
    // checks if two views are overlapping
    // adopted from stackoverflow
    private boolean isOverlapping(View firstView, View secondView)
    {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        Rect rectFirstView = new Rect(firstPosition[0], firstPosition[1],
                firstPosition[0] + firstView.getMeasuredWidth(), firstPosition[1] + firstView.getMeasuredHeight());
        Rect rectSecondView = new Rect(secondPosition[0], secondPosition[1],
                secondPosition[0] + secondView.getMeasuredWidth(), secondPosition[1] + secondView.getMeasuredHeight());
        return rectFirstView.intersect(rectSecondView);
    }

}
