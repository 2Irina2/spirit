package com.example.spirit;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.example.spirit.utils.NotificationService;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class TravelActivity extends AppCompatActivity {

    private static boolean DONE = false;
    Intent intentService;

    TextView minutesView;
    TextView secondsView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
            if (!DONE) {
                secondsView.setText(String.format("%02d", integerTime % 60));
                minutesView.setText(String.format("%02d:", integerTime / 60));
            }
            if (integerTime == 0) {
                DONE = true;
                displayAlertDialog();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        minutesView = findViewById(R.id.textview_travel_minutes);
        secondsView = findViewById(R.id.textview_travel_seconds);

        ImageView imageView = findViewById(R.id.imageview_travel_space_warp);
        Glide.with(this)
                .load(R.drawable.space_warp_black)
                .transition(GenericTransitionOptions.with(R.anim.shake))
                .into(imageView);

        if (!DONE) {

            startCountdownService(0, 10);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DONE) {
            minutesView.setText("00:");
            secondsView.setText("00");
            displayAlertDialog();
        }
    }

    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TravelActivity.this);
        builder.setTitle("Your " + "PROPULSION TYPE" + "spacecraft has arrived at: ")
                .setView(R.layout.travel_dialog)
                .setPositiveButton(R.string.travel_dialog_yes, (dialog, which) -> {
                    Intent intent = new Intent(TravelActivity.this, JourneyActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.travel_dialog_no, (dialog, which) -> {
                    Intent intent = new Intent(TravelActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
        Dialog dialog = builder.create();
        dialog.show();

        TextView exoplanetTextView = dialog.findViewById(R.id.textview_travel_dialog_exoplanet);
        exoplanetTextView.setText("PLANET NAME");
        TextView yearTextView = dialog.findViewById(R.id.textview_travel_dialog_year);
        yearTextView.setText("YEAR");
        TextView distanceTextView = dialog.findViewById(R.id.textview_travel_dialog_distance);
        distanceTextView.setText("DISTANCE");
        TextView timeTextView = dialog.findViewById(R.id.textview_travel_dialog_time);
        timeTextView.setText("TIME");
    }

    private void startCountdownService(int minutes, int seconds) {
        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");
        registerReceiver(broadcastReceiver, intentFilter);

        intentService = new Intent(this, NotificationService.class);
        Integer integerTimeSet = minutes * 60 + seconds;
        intentService.putExtra("TimeValue", integerTimeSet);
        startService(intentService);
    }
}
