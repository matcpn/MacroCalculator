package com.example.macrocalculator;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class StatusBarNotification extends Service {

    private static final int SERVICE_ID = 999003;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DBAdapter myDb;
        myDb = new DBAdapter(this);
        myDb.open();
        FoodList list = myDb.loadConsumedList();

        int totalCaloriesNeeded = 3000;
        int remainingCalories = totalCaloriesNeeded - (int)list.getCalorieCount();
        Log.d("cals", String.valueOf(remainingCalories));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.remaining_calorie_counter, remainingCalories)
                .setContentTitle("Remaining Calories")
                .setContentText("Tap to log food!");

        final Intent notificationIntent = new Intent(this, StatusBarNotification.class);
        final PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pi);
        final Notification note = builder.build();

        startForeground(SERVICE_ID, note);

        return(START_NOT_STICKY);
    }


    @Override
    public void onDestroy() {
        stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return(null);
    }


    private void stop() {

        stopForeground(true);

    }
}