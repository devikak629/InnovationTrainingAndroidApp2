package com.example.devika.innovationtraining;

import com.example.devika.innovationtraining.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.Window;

import static com.example.devika.innovationtraining.R.*;

//ToDo: have topic appear on "topic here page", make sure user can't click next until 10 topics have something in them, fix page so that button leads to congrats page
//ToDo: make sure while timer is on, user can't edit topic or ideas, when timer is up, make topic and ideas clear


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class NewTopic extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_new_topic);

        final View controlsView = findViewById(id.fullscreen_content_controls);
        final View contentView = findViewById(id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }


                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.show();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        ImageButton faqbuttonN = (ImageButton) findViewById(R.id.faqbutton2);
        faqbuttonN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), faq.class);
                startActivity(nextScreen);
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(id.topicNext).setOnTouchListener(mDelayHideTouchListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Topic");
            TextView editText2 = (TextView) findViewById(R.id.editText2);
            editText2.setText(value);
        }


        TextView topicNext = (TextView) findViewById(id.textView99);
        topicNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), CongratsPage.class);
                Log.e("n", "nextWasClicked");
                startActivity(nextScreen);
            }
        });

        TextView tv = (TextView) findViewById(id.textView99);
        Typeface tc = Typeface.createFromAsset(getAssets(), "fonts/Anke.ttf");
        tv.setTypeface(tc);
        TextView te = (TextView) findViewById(id.editText2);
        te.setTypeface(tc);

        TextView tn = (TextView) findViewById(id.idea1text);
        tn.setTypeface(tc);
        TextView tw = (TextView) findViewById(id.idea2text);
        tw.setTypeface(tc);
        TextView tr = (TextView) findViewById(id.idea3text);
        tr.setTypeface(tc);
        TextView tt = (TextView) findViewById(id.idea4text);
        tt.setTypeface(tc);
        TextView ty = (TextView) findViewById(id.idea5text);
        ty.setTypeface(tc);
        TextView tu = (TextView) findViewById(id.idea6text);
        tu.setTypeface(tc);
        TextView ti = (TextView) findViewById(id.idea7text);
        ti.setTypeface(tc);
        TextView to = (TextView) findViewById(id.idea8text);
        to.setTypeface(tc);
        TextView tp = (TextView) findViewById(id.idea9text);
        tp.setTypeface(tc);
        TextView tk = (TextView) findViewById(id.idea10text);
        tk.setTypeface(tc);


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
//        getActionBar().setTitle("New Topic");

    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.show();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    // from the link above
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

// Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Log.d("keyboard", "keyboardopen");
            //Log.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Log.d("keyboard", "keyboardclose");
            //Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }


}
