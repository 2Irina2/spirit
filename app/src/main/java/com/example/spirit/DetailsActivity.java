package com.example.spirit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spirit.objects.Propulsion;
import com.google.gson.Gson;

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

        Intent intent = getIntent();
        Propulsion propulsion = (Propulsion) intent.getSerializableExtra("propulsion");

        titleTextView = findViewById(R.id.textview_details_propulsion_type);
        imageView = findViewById(R.id.imageview_details_image);
        statusTextView = findViewById(R.id.textview_details_status);
        maxVelocityTextView = findViewById(R.id.textview_details_max_velocity);
        exhaustVelocityTextView = findViewById(R.id.textview_details_exhaust_velocity_status);
        highlightTextView = findViewById(R.id.textview_details_highlight_status);
        riskTextView = findViewById(R.id.textview_details_risk_status);
        descriptionTextView = findViewById(R.id.textview_details_description);

        titleTextView.setText(propulsion.getName());
        imageView.setImageResource(getResources().getIdentifier(propulsion.getPicture(), "drawable", getPackageName()));
        statusTextView.setText(propulsion.getStatus());
        maxVelocityTextView.setText(String.valueOf(propulsion.getMaxVelocity()));
        exhaustVelocityTextView.setText(String.valueOf(propulsion.getExhaustVelocity()));
        highlightTextView.setText(propulsion.getHighlights());
        riskTextView.setText(propulsion.getRisks());
        descriptionTextView.setText(propulsion.getDescription());

    }
}
