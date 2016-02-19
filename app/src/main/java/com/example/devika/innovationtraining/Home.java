package com.example.devika.innovationtraining;

import com.example.devika.innovationtraining.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.FindCallback;

import java.util.Calendar;
import java.util.List;

//ToDo: make topic that they choose appear on the "topic here" section of the next page
//ToDo: make topic uneditable while timer is on, and reset when timer is up
//ToDo: when click editText, select bottom radio button

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Home extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private Activity thisClass = this;

    public BroadcastReceiver receiver;
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

    private void selectString() {


        SharedPreferences settings = getSharedPreferences("Dictionary1", 0);

        String t = settings.getString("Topic", "");

        if (t.equals("")) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Topics2"); //where are we askin question


            query.countInBackground(new CountCallback() {


                @Override
                public void done(int i, ParseException e) {

                    SharedPreferences settings = getSharedPreferences("countDictionary", 0);
                    long count = settings.getLong("currentTopic", 1);

                    if (count >= i) {
                        count = 1;
                    } else {
                        count++;
                    }
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putLong("currentTopic", count);
                    editor.commit();

                    Log.e("DEVIKA", "i = " + i);

                    //       int x = (int) (Math.random() * i) + 1;

                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Topics2");
                    query2.whereEqualTo("num", count);  //what are we asking for, asking for the number that is 2

                    query2.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> topics, ParseException e) {
                            if (e == null) {
                                ((RadioButton) findViewById(R.id.radioButton)).setText(topics.get(0).getString("topicText"));
                                Log.d("DEVIKA", "Retrieved " + topics.get(0).getString("topicText") + " scores");
                            } else {
                                Log.d("DEVIKA", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
            });

        }
        else{  ((RadioButton) findViewById(R.id.radioButton)).setText(t);

        }
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

        Log.e("DEVIKA", "WE ARE AT ONRESUME");
        selectString();

        if(milis==0){

            Log.e("DEVIKA", "MILIS IS ZERO");
            return;
        }
        else{
        if(c.getTimeInMillis() <= milis) {
            Log.e("DEVIKA", "HOMEPAGE---TIME IS LESS");
          ((RadioButton) findViewById(R.id.radioButton)).setText(settings.getString("Topic", ""));
            ((TextView) findViewById(R.id.YourTopic)).setText("");
            ((RadioButton) findViewById(R.id.radioButton)).setChecked(true);
            setEnable(false);
        }
        else if(c.getTimeInMillis()>milis) {
            Log.e("DEVIKA", "MILIS IS MORE");
           SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DEVIKA", "HOMEPAGE OPENED");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.home);
        //   setContentView(R.layout.home);

        IntentFilter filter = new IntentFilter("com.example.devika.innovationtraining.CountDownService.destroy");

        if(receiver==null){
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("DEVIKA", "received message in home");
                    thisClass.recreate();
                   // context.startActivity(i);
                }
            };
        }
        registerReceiver(receiver, filter);

        //final View controlsView = findViewById(R.id.fullscreen_content_controls2);
        final View contentView = findViewById(R.id.LinearLayout1);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();

 /**SharedPreferences settings = getSharedPreferences("Dictionary1", 0);
       long milis = settings.getLong("MidnightCalendar", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();**/



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

         /**       if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.show();
                } else {
                    mSystemUiHider.show();
                }
**/
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
        tm.setTypeface(tf);

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
                editor.putString("Topic", getTopicString().toString());
                editor.commit();



                //  Log.e("n", "YourTextEquals " + tn.getText());


                setEnable(false);
                ((RadioButton) findViewById(R.id.radioButton)).setText(settings.getString("Topic", ""));
                ((RadioButton) findViewById(R.id.radioButton)).setChecked(true);
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
                delayedHide(0);
               // AUTO_HIDE_DELAY_MILLIS
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


    @Override
    public void onDestroy(){
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;

        }
        else
            Log.e("DEVIKA", "recicer is null");

    }
}


