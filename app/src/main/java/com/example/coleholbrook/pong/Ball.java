package com.example.coleholbrook.pong;

/**
 * Ball class with x and y positions
 * as well as x and y speeds.
 *
 * @author Cole Holbrook
 * @version March 2018
 */

public class Ball
{
    //Instance variables
    int yMov;
    int xMov;
    int yPos;
    int xPos;

    /**
     * A constructor for the Ball that
     * sets all of the parameters.
     *
     */
    public Ball(int myxPos, int myyPos, int myxMov, int myyMov)
    {
        xPos = myxPos;
        yPos = myyPos;
        xMov = myxMov;
        yMov = myyMov;
    }
}//class Ball
