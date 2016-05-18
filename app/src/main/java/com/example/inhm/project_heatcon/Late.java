package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Late extends AppCompatActivity {
    TextView textView_name;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late);
        textView = (TextView)findViewById(R.id.text_late);
        textView_name = (TextView)findViewById(R.id.textView_name);

        Intent intent = getIntent();


        textView_name.setText(intent.getStringExtra("Lecture_name"));
        String str = (String) intent.getSerializableExtra("request_late");

        String st = "";
        String[] strr = str.split(",");
        for(int i = 0 ; i < strr.length;i++) {
            st += strr[i]+"\n";
        }
        textView.setText(""+st);
    }
}