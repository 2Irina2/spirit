package com.example.spirit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView settingsButton = findViewById(R.id.imageview_home_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        List<String> dummyPropulsion = makeDummyPropulsionList();

        GridView gridView = findViewById(R.id.gridview_home_propulsion);
        CustomAdapter propulsionAdapter = new CustomAdapter(this, dummyPropulsion);
        gridView.setAdapter(propulsionAdapter);
    }

    private List<String> makeDummyPropulsionList() {
        List<String> list = new ArrayList<>();
        list.add("Propulsion 1");
        list.add("Propulsion 2");
        list.add("Propulsion 3");
        list.add("Propulsion 4");
        list.add("Propulsion 5");
        list.add("Propulsion 6");
        list.add("Propulsion 7");
        list.add("Propulsion 8");
        list.add("Propulsion 9");
        list.add("Propulsion 10");
        return list;
    }
}
