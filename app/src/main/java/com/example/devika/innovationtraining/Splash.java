package com.example.devika.innovationtraining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.parse.ParseObject;


public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final View contentView = findViewById(R.id.contentView);
        getSupportActionBar().hide();

        /**ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();**/

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("DEVIKA", "SLPASHE CLICKED");
                Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
                finish();
            }
        });

        TextView tv = (TextView) findViewById(R.id.textViewerr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Anke.ttf");
        tv.setTypeface(tf);
        TextView ty = (TextView) findViewById(R.id.test);
        ty.setTypeface(tf);
        TextView tx = (TextView) findViewById(R.id.textView10);
        tx.setTypeface(tf);
        TextView tp = (TextView) findViewById(R.id.textView9);
        tp.setTypeface(tf);
        TextView tz = (TextView) findViewById(R.id.paragraphsplash);
        tz.setTypeface(tf);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
