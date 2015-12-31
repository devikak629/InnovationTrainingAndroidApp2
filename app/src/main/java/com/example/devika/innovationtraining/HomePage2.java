package com.example.devika.innovationtraining;

import com.example.devika.innovationtraining.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

//ToDo: make topic that they choose appear on the "topic here" section of the next page
//ToDo: make topic uneditable while timer is on, and reset when timer is up
//ToDo: when click editText, select bottom radio button

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class HomePage2 extends Activity {
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

    private String selectString() {
        Resources res = getResources();
        String[] topicsOfTheDay = res.getStringArray(R.array.topicsOfTheDay);
        int x = (int) (Math.random() * topicsOfTheDay.length);
        return topicsOfTheDay[x];
    }

    private CharSequence getTopicString() {

        if (((RadioButton) findViewById(R.id.radioButton)).isChecked()) {
            Log.e("DEVIKA", ((RadioButton) findViewById(R.id.radioButton)).getText().toString());

            return ((RadioButton) findViewById(R.id.radioButton)).getText();

        } else {
            Log.e("DEVIKA", ((TextView) findViewById(R.id.YourTopic)).getText().toString());
            return ((TextView) findViewById(R.id.YourTopic)).getText();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        Calendar c = Calendar.getInstance();
        SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
        long milis = settings.getLong("MidnightCalendar", 0);

        if(milis==0){
            return;
        }
        else{
        if(c.getTimeInMillis()<milis){
            Log.e("DEVIKA", "HOMEPAGE---TIME IS LESS");
            ((RadioButton) findViewById(R.id.radioButton)).setText(settings.getString("Topic", ""));
            ((TextView) findViewById(R.id.YourTopic)).setText("");
            setEnable(false);
        }
        else if(c.getTimeInMillis()>=milis) {
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(getIntent());
        }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.home);
        //   setContentView(R.layout.home);

        //final View controlsView = findViewById(R.id.fullscreen_content_controls2);
        final View contentView = findViewById(R.id.LinearLayout1);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();

    SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
        long milis = settings.getLong("MidnightCalendar", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();

        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        /*
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
                        */
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
                */
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.homeNextButton).setOnTouchListener(mDelayHideTouchListener);
        TextView tv = (TextView) findViewById(R.id.textView3);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Anke.ttf");
        tv.setTypeface(tf);
        TextView te = (TextView) findViewById(R.id.textView5);
        te.setTypeface(tf);
        TextView tn = (TextView) findViewById(R.id.YourTopic);
        tn.setTypeface(tf);
        TextView tm = (TextView) findViewById(R.id.textViewww);
        tn.setTypeface(tf);

        RadioButton tq = (RadioButton) findViewById(R.id.radioButton);
        tq.setTypeface(tf);


        Button homeNextButton = (Button) findViewById(R.id.homeNextButton);

        homeNextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), NewTopic.class);

                //  Log.e("n", "nextWasClicked");

                Log.e("GETTOPICSTRING", getTopicString().toString());
                nextScreen.putExtra("Topic", getTopicString().toString());
                SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Topic",getTopicString().toString());
                editor.commit();



                //  Log.e("n", "YourTextEquals " + tn.getText());


                setEnable(false);
                ((RadioButton) findViewById(R.id.radioButton)).setText(settings.getString("Topic", ""));
                ((TextView) findViewById(R.id.YourTopic)).setText("");
                startActivity(nextScreen);
            }
        });


        ImageButton faqbuttonN = (ImageButton) findViewById(R.id.faqbutton);
        faqbuttonN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), faq.class);
                startActivity(nextScreen);
            }
        });

        ((RadioButton) findViewById(R.id.radioButton)).setText(selectString());

        // TextView tu = (TextView) findViewById(R.id.textView900);
        //tu.setTypeface(tf);


        ((EditText)findViewById(R.id.YourTopic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText)findViewById(R.id.YourTopic)).setText("");


            }
        });
    }


    private void setEnable(boolean b) {
        ((RadioGroup)findViewById(R.id.radiogroup)).setEnabled(b);
        ((RadioButton)findViewById(R.id.radioButton)).setEnabled(b);
        ((RadioButton)findViewById(R.id.radioButton2)).setEnabled(b);
        ((EditText)findViewById(R.id.YourTopic)).setEnabled(b);

        if(b==false){
        ((EditText)findViewById(R.id.YourTopic)).setInputType(InputType.TYPE_NULL);}
        else {
            ((EditText)findViewById(R.id.YourTopic)).setInputType(InputType.TYPE_CLASS_TEXT);}
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
            mSystemUiHider.hide();
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

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_home_page, popup.getMenu());
        popup.show();

    }
}


