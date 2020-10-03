package com.example.spirit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setTitle(R.string.settings_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView introTextView = findViewById(R.id.textview_settings_playintro);
        introTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage(R.string.settings_dialog_play_intro_content)
                        .setTitle(R.string.settings_dialog_play_intro_title)
                        .setPositiveButton(R.string.settings_dialog_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SettingsActivity.this, IntroActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.settings_dialog_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        TextView creditsTitleTextView = findViewById(R.id.textview_settings_credits_title);
        final TextView creditsContentTextView = findViewById(R.id.textview_settings_credits_content);
        creditsContentTextView.setVisibility(View.GONE);
        creditsTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditsContentTextView.setVisibility(creditsContentTextView.isShown()? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
