package com.example.coleholbrook.pong;

import android.graphics.*;
import android.view.MotionEvent;
import java.util.ArrayList;

/**
 * class that animates a ball repeatedly moving around the playing area,
 * bouncing off of walls and the paddle. When the user presses the screen,
 * more balls are added. The paddle is now moveable, the score is displayed,
 * and the game ends when the user loses 5 lives. (The game is also
 * reasonable to play)
 *
 * @author Cole Holbrook
 * @version March 2018
 */
public class theAnimator implements Animator
{

    // instance variables
    ArrayList<Ball> ballsList = new ArrayList<>();
    Paddle paddle = new Paddle(0, 0, 0, 0);
    private int canvasHeight;  //To be used throughout the class
    private int canvasWidth;   //To be used throughout the class
    private int ballRadius = 60; //Ball size is not changing
    private int lives = 5; //The amount of lives the user has
    private int score = 0; //Score of the player
    private int endTick; //This "tick" times the loser screen
    private int paddleY; //Paddle position, there is not an X because
                         //the paddle only move up and down

    /**
     * A constructor for the theAnimator that
     * adds the first ball into the array list.
     *
     */
    public theAnimator()
    {
        //Add the first ball to the array list
        Ball firstBall = new Ball(0, randomYPos(), randomXMov(), randomYMov());
        ballsList.add(firstBall);

        //Set the paddle here first
        paddleY = 550;
    }

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval()
    {
        return 1;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor()
    {
        // create/return the background color
        return Color.rgb(255, 255, 193); //light yellow
    }

    /**
     * Picks a random speed for the ball to travel
     * in the x direction
     *
     * @return the random speed of the X direction (xMov)
     */
    public int randomXMov() {
        //A random number to determine which direction
        //the ball will be moving in
        int direction = (int) (Math.random()*2);

        //creates the random speed
        int mySpeed = (int) (Math.random()* 20)+15;

        //Depending on if the direction is one or not,
        //the balls direction will change.
        if(direction == 1)
        {
            mySpeed = mySpeed * (-1); //flips the direction
        }
        else
        {
            mySpeed = mySpeed *1 ;
        }
        return mySpeed;
    }

    /**
     * Picks a random speed for the ball to travel
     * in the y direction
     *
     * @return the random speed of the Y direction (yMov)
     */
    public int randomYMov()
    {
        //A random number to determine which direction
        //the ball will be moving in
        int direction = (int) (Math.random()*2);

        //creates the random speed
        int mySpeed = (int) (Math.random() * 20)+20;

        //Depending on if the direction is one or not,
        //the balls direction will change.
        if(direction == 1)
        {
            mySpeed = mySpeed * (-1);
        }
        else
        {
            mySpeed = mySpeed *1; //flips the direction
        }
        return mySpeed;
    }

    /**
     * Picks a random spot along the y axis for the ball
     * to start at. There is not an X one because it
     * is always starting along the right wall.
     *
     * @return the random value on the Y axis (yPos)
     */
    public int randomYPos()
    {
        //Get a random spot in between 0 and the height of Canvas
        int myPosition = (int) (Math.random() * canvasHeight);
        return myPosition;
    }

    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {

        //Set the canvas height and width to be used
        canvasHeight = g.getHeight();
        canvasWidth = g.getWidth();

        paddle.drawPaddle(g, canvasWidth, canvasHeight, paddleY);

        /**
         * External Citation
         *  Date: 19 March 2018
         *  Problem: Did not know hex colors
         *
         *  Resource: http://www.color-hex.com/color-wheel/
         *  Solution: Used to color wheel to find colors
         */

        //Loops through each ball in the array list
        for (Ball object : ballsList) {
            //draw each ball
            Paint redPaint = new Paint();
            redPaint.setColor(0xFF92e2f7);
            g.drawCircle(object.xPos, object.yPos, ballRadius, redPaint);
            //Draw an outline for each ball
            Paint emptyPaint = new Paint();
            emptyPaint.setStyle(Paint.Style.STROKE);
            emptyPaint.setColor(0xFF000000);
            emptyPaint.setStrokeWidth(7);
            g.drawCircle(object.xPos, object.yPos, ballRadius, emptyPaint);

        }

        //Game over, out of lives
        if (lives == -1)
        {
            //numberOfBalls = ballsList.size();
            loser(g);
        } else
            {


            //Loop through for each ball in the array list
            for (Ball object : ballsList) {
                //If the ball hits the left most wall
                if (object.xPos < 0) {
                    //Adds to the score
                    score++;
                    //Start moving right
                    object.xMov = object.xMov * (-1);
                }
                //If the ball hits the right wall
                if (object.xPos > canvasWidth) {
                    //Takes away a life
                    lives--;
                    //Checks to see if it was the only ball
                    if (ballsList.size() == 1) {
                        //Resets a the ball if it is the only one
                        object.xPos = 0;
                        object.yPos = randomYPos();
                        object.xMov = randomXMov();
                        object.yMov = randomYMov();
                    } else {
                        //If there are others the ball that hit the right wall
                        //gets deleted/removed from the array list.
                        ballsList.remove(object);
                        break;
                    }
                }
                //If the ball hits the top wall
                if (object.yPos < 0) {
                    //Start moving down
                    object.yMov = object.yMov * (-1);
                }
                //If the ball hits the bottom wall
                if (object.yPos > canvasHeight) {
                    //Start moving up
                    object.yMov = object.yMov * (-1);
                }
                //If the ball hits the paddle
                if (object.xPos >= canvasWidth - 100 && object.xPos <= canvasWidth - 60
                        && object.yPos >= paddleY && object.yPos <= paddleY + 510 && object.yMov != 0) {
                    //Start moving left
                    object.xMov = object.xMov * (-1);
                }

                //Updates the positions of the balls depending
                //on the speed
                object.xPos += object.xMov;
                object.yPos += object.yMov;
                scoreBoard(g);

                /**
                 * External Citation
                 *  Date: 19 March 2018
                 *  Problem: Couldn't remember the syntax for for each loops
                 *
                 *  Resource: https://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work
                 *  Solution: Followed the example code
                 */
            }
        }
    }


    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause()
    {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit()
    {
        return false;
    }

    /**
     * Add new balls when the screen is tapped
     */
    public void onTouch(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(event.getX() < (canvasWidth -200))
            {
                //Add another instance of a ball to the array list
                //when the user taps the screen
                Ball myBall = new Ball(0, randomYPos(), randomXMov(), randomYMov());
                ballsList.add(myBall);
            }
        }

        //If the user is trying to move the paddle
        if(event.getX() > (canvasWidth - 200))
        {
            paddleY = (int) event.getY() -250;  //update the position of the paddle, adding 250 because
                                                // it is half of the paddle and the paddle will be centered
                                                // on the persons finger
        }
    }

    /**
     * Draws the scoreboard for the player to see their
     * score and how many lives they have left
     *
     * @param g the graphics object on which to draw
     *
     */
    public void scoreBoard(Canvas g)
    {
        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(80.0f);
        g.drawText("Points: "+score, 40.0f, 100.0f, scorePaint);
        g.drawText("Lives: "+lives, 40.0f, 180.0f, scorePaint);

    }

    /**
     * Draws the game over screen if the player loses and then exits
     * the game.
     *
     * @param g the graphics object on which to draw
     *
     */
    public void loser(Canvas g)
    {
        //Displays the user's score
        Paint loserPaint = new Paint();
        loserPaint.setColor(Color.BLACK);
        loserPaint.setTextSize(300.0f);
        scoreBoard(g);
        g.drawText("Score: "+score, 800.0f, 450.0f, loserPaint);

        //Shows the loser screen for a few seconds
        endTick++;

            if (endTick >= 400)
            {
                System.exit(0);
            }
        /**
         * External Citation
         *  Date: 27 March 2018
         *  Problem: Did not know how to exit the app
         *
         *  Resource: https://stackoverflow.com/questions/457338/use-of-system-exit0
         *  Solution: Used the system exit method to close down the app
         */


    }
}//class theAnimator
