package com.example.devika.innovationtraining;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

/**
 * Created by devika on 12/29/15.
 */
public class MidnightService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MidnightBinder extends Binder{
        MidnightService getService(){
            return MidnightService.this;
        }
    }

    private final IBinder mBinder = new MidnightBinder();
}
