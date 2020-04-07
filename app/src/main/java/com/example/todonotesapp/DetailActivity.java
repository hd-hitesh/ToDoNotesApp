package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textViewtitle, textViewDescriptipn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setIntentData();
    }

    private void setIntentData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(AppConstant.TITLE);
        String desc = intent.getStringExtra(AppConstant.DESCRIPTION);

        textViewtitle.setText(title);
        textViewDescriptipn.setText(desc);
    }

    private void bindViews() {
        textViewtitle = findViewById(R.id.textViewTitle);
        textViewDescriptipn = findViewById(R.id.textViewDescription);
    }
}
