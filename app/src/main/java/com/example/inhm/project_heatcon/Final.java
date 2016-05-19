package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Final extends AppCompatActivity {
    TextView textView_name;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Intent intent = getIntent();
        textView_name = (TextView)findViewById(R.id.textView_name);
        textView = (TextView)findViewById(R.id.text_final);
        textView_name.setText(intent.getStringExtra("Lecture_name"));


        String str = (String) intent.getSerializableExtra("request_final_score");

        if(str == null) {
            textView.setText("아직 기말고사점수가 나오지 않았습니다.");
        }else {
            textView.setText("" + str + "점");
        }
    }
}
