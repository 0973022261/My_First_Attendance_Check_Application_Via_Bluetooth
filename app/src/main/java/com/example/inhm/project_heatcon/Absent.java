package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Absent extends AppCompatActivity {

    TextView textView;
    TextView textView_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);
        textView = (TextView)findViewById(R.id.text_absent);
        textView_name = (TextView)findViewById(R.id.textView_name);
        Intent intent = getIntent();
        textView_name.setText(intent.getStringExtra("Lecture_name"));

        String str = (String) intent.getSerializableExtra("request_absent");
        String st = "";
        String[] strr = str.split(",");
        for(int i = 0 ; i < strr.length;i++) {
            st += strr[i]+"\n";
        }
        textView.setText(""+st);
    }
}
