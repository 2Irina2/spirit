package com.example.spirit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final ImageView imageView = findViewById(R.id.imageView_intro_gif);
        Glide.with(this)
                .load(R.drawable.intro_gif)
                .into(imageView);

        Button skipButton = findViewById(R.id.button_intro_skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);
                builder.setMessage(R.string.dialog_skip_message)
                        .setTitle(R.string.dialog_skip_title)
                        .setPositiveButton(R.string.dialog_skip_yes, (dialog, which) -> {
                            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton(R.string.dialog_skip_no, (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });
    }
}
