package com.example.spirit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spirit.objects.PopCulture;
import com.example.spirit.objects.Propulsion;

public class PropulsionActivity extends AppCompatActivity {

    Propulsion propulsion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propulsion);
        setupBackButton();

        Intent intent = getIntent();
        propulsion = (Propulsion) intent.getSerializableExtra("propulsion");
        TextView propulsionTextView = findViewById(R.id.textview_propulsion_title);

        propulsionTextView.setText(propulsion.getName());

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
        Button detailsButton = findViewById(R.id.button_propulsion_details);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropulsionActivity.this, DetailsActivity.class);
                intent.putExtra("propulsion", propulsion);
                startActivity(intent);
            }
        });
    }

    private void setUpJourneyButton(){
        Button journeyButton = findViewById(R.id.button_propulsion_journey);
        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropulsionActivity.this, JourneyActivity.class);
                intent.putExtra("propulsion", propulsion);
                startActivity(intent);
            }
        });
    }

    private void setUpPopButton(){
        Button popArtButton = findViewById(R.id.button_propulsion_popculture);
        popArtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropulsionActivity.this, PopCultureActivity.class);
                startActivity(intent);
            }
        });
    }
}
