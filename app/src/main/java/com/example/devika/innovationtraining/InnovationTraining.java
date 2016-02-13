package com.example.devika.innovationtraining;
import android.app.Application;

import com.parse.Parse;

public class InnovationTraining extends Application {

    @Override

    public void onCreate(){

        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }






}