package com.example.spirit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.spirit.objects.Propulsion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<Propulsion> propulsionList = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView settingsButton = findViewById(R.id.imageview_home_settings);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        Gson gson = new Gson();
        try {
            Type listType = new TypeToken<ArrayList<Propulsion>>(){}.getType();
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("propulsion",
                            "raw", getPackageName()));
            InputStreamReader insReader = new InputStreamReader(ins);
            BufferedReader reader = new BufferedReader(insReader);
            String line = reader.readLine();

            propulsionList = gson.fromJson(line, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        GridView gridView = findViewById(R.id.gridview_home_propulsion);
        CustomAdapter propulsionAdapter = new CustomAdapter(this, propulsionList);
        gridView.setAdapter(propulsionAdapter);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

    }
}
