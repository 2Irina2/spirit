package com.example.spirit.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.spirit.MainActivity;
import com.example.spirit.R;
import com.example.spirit.TravelActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "NotificationChannelID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Integer[] timeRemaining = {intent.getIntExtra("TimeValue", 0)};
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Intent intent1local = new Intent();
                intent1local.setAction("Counter");
                timeRemaining[0]--;
                if (timeRemaining[0] <= 0){
                    NotificationUpdate(timeRemaining[0]);
                    timer.cancel();
                }
                intent1local.putExtra("TimeRemaining", timeRemaining[0]);
                sendBroadcast(intent1local);
            }
        }, 0,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    public void NotificationUpdate(Integer timeLeft){
        try {
            Intent notificationIntent = new Intent(this, TravelActivity.class);
            notificationIntent.putExtra("notif", true);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Notification[] notification = {new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getResources().getString(R.string.travel_notification_title) + "PLANET NAME")
                    .setContentText(getResources().getString(R.string.travel_notification_content))
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build()};
            startForeground(1, notification[0]);
            NotificationChannel notificationChannel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Counter Service", NotificationManager.IMPORTANCE_DEFAULT);
            }
            NotificationManager notificationManager = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
