package com.example.devika.innovationtraining;

import com.example.devika.innovationtraining.util.SystemUiHider;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.os.CountDownTimer;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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

    BroadcastReceiver receiver;
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


    public void selectString() {
        SharedPreferences settings = getSharedPreferences("Dictionary1", 0);

        String t = settings.getString("Message", "");

        if (t.equals("")) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages"); //where are we askin question


            query.countInBackground(new CountCallback() {


                @Override
                public void done(int i, ParseException e) {

                    SharedPreferences settings = getSharedPreferences("countDictionary", 0);
                    long count = settings.getLong("currentMessage", 1);

                    if (count >= i) {
                        count = 1;
                    } else {
                        count++;
                    }
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putLong("currentMessage", count);
                    editor.commit();

                    Log.e("DEVIKA", "i = " + i);

                    //       int x = (int) (Math.random() * i) + 1;

                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Messages");
                    query2.whereEqualTo("num", count);  //what are we asking for, asking for the number that is 2

                    query2.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> topics, ParseException e) {
                            if (e == null) {
                                ((TextView) findViewById(R.id.InspirationalMessage)).setText(topics.get(0).getString("messageText"));
                                Log.d("DEVIKA", "Retrieved " + topics.get(0).getString("messageText"));
                            } else {
                                Log.d("DEVIKA", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
            });

        }
        else{  ((TextView) findViewById(R.id.InspirationalMessage)).setText(t);

        }
    }

    // Method to start the service
    public void startService() {
        ComponentName cn = startService(new Intent(getBaseContext(), CountDownService.class));
        Log.e("DEVIKA", "SERVICE STARTED 2");
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), CountDownService.class));
    }
    public static Calendar midnightCalendarFactory(){

        Calendar midnightCalendar = Calendar.getInstance();
  /** midnightCalendar.add(Calendar.DAY_OF_YEAR, 1);
        midnightCalendar.set(Calendar.HOUR_OF_DAY, 0);
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);
        midnightCalendar.set(Calendar.MILLISECOND, 0);**/

      midnightCalendar.add(Calendar.SECOND, 7);
        //midnightCalendar.setTimeInMillis(System.currentTimeMillis()+timeLeft);
        return midnightCalendar;

        //Log.e()
    }

    public static Calendar midnightCalendarFactory(long timeLeft){

        Calendar midnightCalendar = Calendar.getInstance();
        /*midnightCalendar.add(Calendar.DAY_OF_YEAR, 1);
        midnightCalendar.set(Calendar.HOUR_OF_DAY, 0);
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);
        midnightCalendar.set(Calendar.MILLISECOND, 0);
    */
      //  midnightCalendar.add(Calendar.SECOND, 30);
        midnightCalendar.setTimeInMillis(System.currentTimeMillis()+timeLeft);
        return midnightCalendar;

        //Log.e()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DEVIKA", "congrats OPENED");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_congrats_page);


        settingTimer(startTime);

        //creating a broadcast receiver that has the time left
        IntentFilter filter = new IntentFilter("com.example.devika.innovationtraining.CountDownService");

        //MyReceiver receiver = new MyReceiver();
        if (receiver==null){
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long result =   intent.getLongExtra("start timer", 0);
                    settingTimer(result);
                }
            };
        }
        registerReceiver(receiver, filter);

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

        selectString();
    }

    private void settingTimer(long timeLeft) {
        Calendar midnightCalendar = midnightCalendarFactory(timeLeft);
        SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong( "MidnightCalendar", midnightCalendar.getTimeInMillis());
        editor.commit();
        text = (TextView) this.findViewById(R.id.timer);


        long howMany = (midnightCalendar.getTimeInMillis() - System.currentTimeMillis());
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        countDownTimer = new TwentyFourHoursCountDownTimer(howMany, interval);
        if (text != null) {
            text.setText("Time remaining:\n" + "        "  + countDownTimer.timeFromMillis(howMany));
        }
        countDownTimer.start();
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

    public static int whoAmI = 0;
    public class TwentyFourHoursCountDownTimer extends CountDownTimer {

        //public static int whoAmI = 0;
        public int me;

        public TwentyFourHoursCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
            me = whoAmI;
            whoAmI++;
        }

        @Override
        public void onFinish() {

            text.setText("Time's Up!");


        }

        @Override
        public void onTick(long millisUntilFinished) {

            text.setText("Time remaining:\n" + "        " + timeFromMillis(millisUntilFinished));

            Log.e("DEVIKA",  "Number: "+me+"; "+text.getText().toString());

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

    @Override

    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
        finish();


    }
}