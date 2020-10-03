package com.example.spirit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.example.spirit.utils.NotificationService;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class TravelActivity extends AppCompatActivity {

    TextView minutesView;
    TextView secondsView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        ImageView imageView = findViewById(R.id.imageview_travel_space_warp);
        Glide.with(this)
                .load(R.drawable.space_warp_black)
                .transition(GenericTransitionOptions.with(R.anim.shake))
                .into(imageView);

        int minutes = 1;
        int seconds = 5;
        minutesView = findViewById(R.id.textview_travel_minutes);
        secondsView = findViewById(R.id.textview_travel_seconds);

        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                secondsView.setText(String.format("%02d", integerTime%60));
                minutesView.setText(String.format("%02d:", integerTime/60));
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);

        Intent intentService = new Intent(this, NotificationService.class);
        Integer integerTimeSet = minutes * 60 + seconds;
        intentService.putExtra("TimeValue", integerTimeSet);
        startService(intentService);
    }
}
