package com.example.devika.innovationtraining;

import com.example.devika.innovationtraining.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.CountDownTimer;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.view.Window;
import android.content.SharedPreferences;

//ToDo: when timer is up, put a button there that takes you to home page
//ToDo: while timer is going, open app to that page

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class CongratsPage extends Activity {
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
    private TextView text;
    private TwentyFourHoursCountDownTimer countDownTimer;
    private final long startTime = 30000;
    private final long interval = 1000;


    public String selectString() {
        Resources res = getResources();
        String[] inspirationalMessages = res.getStringArray(R.array.inspirationalMessages);
        int x = (int) (Math.random() * inspirationalMessages.length);
        return inspirationalMessages[x];
    }

    // Method to start the service
    public void startService() {
        startService(new Intent(getBaseContext(), CountDownService.class));
        Log.e("DEVIKA", "SERVICE STARTED 2");
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), CountDownService.class));
    }

    public static Calendar midnightCalendarFactory(){

        Calendar midnightCalendar = Calendar.getInstance();
        /*midnightCalendar.add(Calendar.DAY_OF_YEAR, 1);
        midnightCalendar.set(Calendar.HOUR_OF_DAY, 0);
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);
        midnightCalendar.set(Calendar.MILLISECOND, 0);
    */
        midnightCalendar.add(Calendar.SECOND, 30);
        midnightCalendar.set(Calendar.MILLISECOND, 0);
        return midnightCalendar;

        //Log.e()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_congrats_page);

       Calendar midnightCalendar = midnightCalendarFactory();
        midnightCalendar.getTimeInMillis();
      SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong( "MidnightCalendar",midnightCalendar.getTimeInMillis());
        editor.commit();
        text = (TextView) this.findViewById(R.id.timer);


        Calendar c = midnightCalendarFactory();
        long howMany = (c.getTimeInMillis() - System.currentTimeMillis());
        countDownTimer = new TwentyFourHoursCountDownTimer(howMany, interval);
        if (text != null) {
            text.setText(text.getText() + countDownTimer.timeFromMillis(howMany));
        }
        countDownTimer.start();
        Log.e("DEVIKA", "SERVICE STARTED");
        startService();

        /**    final View controlsView = findViewById(R.id.fullscreen_content_controls);
         final View contentView = findViewById(R.id.fullscreen_content);

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
         @Override public void onClick(View view) {
         if (TOGGLE_ON_CLICK) {
         mSystemUiHider.toggle();
         } else {
         mSystemUiHider.show();
         }
         }
         });
         **/
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        ImageButton faqbuttonN = (ImageButton) findViewById(R.id.faqbutton3);
        faqbuttonN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), faq.class);
                startActivity(nextScreen);
            }
        });
        TextView tp = (TextView) findViewById(R.id.textView7);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Anke.ttf");
        tp.setTypeface(tf);

        TextView tu = (TextView) findViewById(R.id.InspirationalMessage);
        tu.setTypeface(tf);

        TextView ty = (TextView) findViewById(R.id.timer);
        ty.setTypeface(tf);


        findViewById(R.id.Next).setOnTouchListener(mDelayHideTouchListener);

        ((TextView) findViewById(R.id.InspirationalMessage)).setText(selectString());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
//            mSystemUiHider.show();
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

    public class TwentyFourHoursCountDownTimer extends CountDownTimer {

        public TwentyFourHoursCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

            text.setText("Time's Up!");
        }

        @Override
        public void onTick(long millisUntilFinished) {

            text.setText("Time remaining:\n" + "        " + timeFromMillis(millisUntilFinished));

        }

        public String timeFromMillis(long timestamp) {

            long seconds = timestamp / 1000;
            long outputseconds = seconds % 60;
            long minutes = seconds / 60;
            long outputminutes = minutes % 60;
            long hours = minutes / 60;
            long outputhours = hours % 24;

            // Date date = new Date(timestamp);
            // DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return String.format("%02d:%02d:%02d", outputhours, outputminutes, outputseconds);
        }
    }
}