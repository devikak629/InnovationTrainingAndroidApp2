
package com.example.devika.innovationtraining;

        import android.app.Service;
        import android.content.Intent;
        import android.content.Context;
        import android.os.CountDownTimer;
        import android.os.IBinder;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;
        import android.widget.Toast;
        import android.app.TaskStackBuilder;
        import android.app.PendingIntent;
        import android.app.NotificationManager;
        import java.util.Calendar;

public class CountDownService extends Service {


    private TwentyFourHoursCountDownTimer countDownTimer;
    public CountDownService thisCDS = this;

    public class TwentyFourHoursCountDownTimer extends CountDownTimer {

        public TwentyFourHoursCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            Log.e("DEVIKA", "SERVICEAND TIMER  FINISHED");
            stopSelf();
            Toast.makeText(thisCDS, "Service Ended", Toast.LENGTH_LONG).show();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(thisCDS)
                            .setSmallIcon(R.drawable.menu3button)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setAutoCancel(true);

// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(thisCDS, HomePage2.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(thisCDS);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(HomePage2.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(0, mBuilder.build());
            //return START_STICKY;

        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.e("DEVIKA", "Tick " + millisUntilFinished);
        }

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
return START_STICKY;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.e("DEVIKA", "SERVICE STARTED again");
        // Let it continue running until it is stopped.
        Calendar c = CongratsPage.midnightCalendarFactory();
        long howMany = (c.getTimeInMillis() - System.currentTimeMillis());
        TwentyFourHoursCountDownTimer cdt = new TwentyFourHoursCountDownTimer(howMany, 1000);
        cdt.start();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}