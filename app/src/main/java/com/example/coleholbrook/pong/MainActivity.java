package com.example.coleholbrook.pong;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

/**
 * MainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 *
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @version July 2013
 *
 */
public class MainActivity extends Activity {

    /**
     * creates an AnimationSurface containing theAnimator.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);


        // Connect the animation surface with the animator
        AnimationSurface mySurface = this.findViewById(R.id.animationSurface);
        mySurface.setAnimator(new theAnimator());
    }
}
