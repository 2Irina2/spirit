package com.example.spirit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PropulsionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propulsion);
        setupBackButton();

        Intent intent = getIntent();
        TextView propulsionTextView = findViewById(R.id.textview_propulsion_title);
        propulsionTextView.setText(intent.getStringExtra("prop_name"));

        setUpDetailsButton();
        setUpJourneyButton();
        setUpPopButton();
    }

    private void setupBackButton(){
        ImageView backImageview = findViewById(R.id.imageView_propulsion_back);
        backImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setUpDetailsButton(){

    }

    private void setUpJourneyButton(){
        Button journeyButton = findViewById(R.id.button_propulsion_journey);
        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropulsionActivity.this, JourneyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpPopButton(){

    }
}
