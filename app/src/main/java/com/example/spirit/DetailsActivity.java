package com.example.spirit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView titleTextView;
    ImageView imageView;
    TextView statusTextView;
    TextView maxVelocityTextView;
    TextView exhaustVelocityTextView;
    TextView highlightTextView;
    TextView riskTextView;
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleTextView = findViewById(R.id.textview_details_propulsion_type);
        imageView = findViewById(R.id.imageview_details_image);
        statusTextView = findViewById(R.id.textview_details_status);
        maxVelocityTextView = findViewById(R.id.textview_details_max_velocity);
        exhaustVelocityTextView = findViewById(R.id.textview_details_exhaust_velocity_status);
        highlightTextView = findViewById(R.id.textview_details_highlight_status);
        riskTextView = findViewById(R.id.textview_details_risk_status);
        descriptionTextView = findViewById(R.id.textview_details_description);
    }
}
