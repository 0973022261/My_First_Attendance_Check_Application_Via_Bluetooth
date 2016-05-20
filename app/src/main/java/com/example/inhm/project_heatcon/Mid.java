package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Mid extends AppCompatActivity {
    TextView textView_name;
    TextView textView;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);

        /**
         * 메모리에 올려진 텍스트뷰를 변수에 저장합니다.
         */
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView = (TextView) findViewById(R.id.text_mid);

        /**
         * 인텐트를 받아옵니다.
         */
        Intent intent_get = getIntent();

        /**
         * 받아온 인텐트를 텍스트뷰의 텍스트로 설정합니다.
         */
        textView_name.setText(intent_get.getStringExtra("Lecture_name"));
        str = (String) intent_get.getSerializableExtra("request_mid_score");

        if (str == null) {
            textView.setText("중간고사 점수가 아직 나오지 않았습니다.");
        } else {
            textView.setText("" + str + "점");
        }
    }
}
