package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Mid extends AppCompatActivity {
    TextView textView_name;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);
        textView_name = (TextView)findViewById(R.id.textView_name);
        textView = (TextView)findViewById(R.id.text_mid);

        Intent intent = getIntent();

        textView_name.setText(intent.getStringExtra("Lecture_name"));

        String str = (String) intent.getSerializableExtra("request_mid_score");

        textView.setText(""+str+"점");
    }
}
