package com.example.spirit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PopCultureActivity extends AppCompatActivity {

    TextView showNameView;
    TextView yearView;
    TextView spacecraftView;
    TextView propulsionTypeView;
    ImageView imageView;
    TextView synopsisView;
    TextView factView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_culture);
        
        showNameView = findViewById(R.id.textview_popculture_name);
        yearView = findViewById(R.id.textview_popculture_year);
        imageView = findViewById(R.id.imageview_popculture_image);
        spacecraftView = findViewById(R.id.textview_popculture_spacecraft);
        propulsionTypeView = findViewById(R.id.textview_popculture_propulsion);
        synopsisView = findViewById(R.id.textview_popculture_synopsis);
        factView = findViewById(R.id.textview_popculture_fact);
    }
}
