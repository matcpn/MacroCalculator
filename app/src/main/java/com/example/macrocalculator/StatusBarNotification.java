package com.example.macrocalculator;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class StatusBarNotification extends Service {

    private static final int SERVICE_ID = 999003;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int requestID = (int) System.currentTimeMillis();
        DBAdapter myDb;
        myDb = new DBAdapter(this);
        myDb.open();
        FoodList list = myDb.loadConsumedList();

        float totalCaloriesNeeded;
        try {
            totalCaloriesNeeded = ((MyApplication) this.getApplication()).getUser().getCaloriesToEat();
        }
        catch (NullPointerException e) {
            totalCaloriesNeeded = 3000f;
        }

        float remainingCalories = totalCaloriesNeeded - (int)list.getCalorieCount();
        Log.d("cals", String.valueOf(remainingCalories));
        Log.d("total cals", String.valueOf(totalCaloriesNeeded));

        //comments have to do with making the notification show the right image in the notification bar
        //I probably shouldn't commit it like this but I'm going to anyways
 //       LevelListDrawable d = (LevelListDrawable) getResources().getDrawable(R.drawable.remaining_calorie_counter);
 //       d.setLevel(remainingCalories);
  //      Bitmap largeIcon = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this,requestID, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.remaining_calorie_counter, (int)remainingCalories)
 //               .setLargeIcon(largeIcon)
                .setContentIntent(contentIntent)
                .setContentTitle("Remaining Calories")
                .setContentText("Tap to log food!");

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