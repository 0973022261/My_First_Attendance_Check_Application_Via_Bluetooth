package com.example.inhm.project_heatcon;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckAttendandce extends AppCompatActivity {

    private static final int REQUEST_ATTENDANCE_CHECK_NUMBER = 6;

    boolean check =false;

    String attendance_number;
    String student_number;
    String lecture_number;

    TextView textView_name;
    TextView textView_week;
    TextView textView_date;

    TextView textView_attendance_number;
    TextView textView_absent_number;
    TextView textView_late_number;

    Button button_attendance_check;

    int attendance = 0;
    int absent = 0;
    int late = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);

        button_attendance_check = (Button)findViewById(R.id.button_attendance_check);

        textView_name = (TextView)findViewById(R.id.textView_name);
        textView_week = (TextView)findViewById(R.id.textView_week);
        textView_date = (TextView)findViewById(R.id.textView_date);

        textView_attendance_number = (TextView)findViewById(R.id.attendance_number);
        textView_absent_number = (TextView)findViewById(R.id.absent_number);
        textView_late_number = (TextView)findViewById(R.id.late_number);


        Intent intent = getIntent();
        textView_name.setText(intent.getStringExtra("Lecture_name"));
        textView_week.setText(intent.getStringExtra("Lecture_week"));
        textView_date.setText(intent.getStringExtra("Lecture_date"));
        attendance_number = (String) intent.getSerializableExtra("attendance_number");
        student_number = (String) intent.getSerializableExtra("student_number");
        lecture_number = (String) intent.getSerializableExtra("lecture_number");

        Log.d("LOGINING",""+attendance_number);

        char [] attendance_number_arr = attendance_number.toCharArray();

        for(int i = 0 ; i < attendance_number_arr.length;i++) {
            if(attendance_number_arr[i] == '0'){
                attendance++;
            }else if( attendance_number_arr[i] == '1') {
                absent++;
            } else if( attendance_number_arr[i] == '2') {
                late++;
            }
        }

        textView_attendance_number.setText(""+attendance);
        textView_absent_number.setText(""+absent);
        textView_late_number.setText(""+late);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onButton1Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(),RecoMonitoringActivity.class);
        intent.putExtra("student_number",student_number);
        intent.putExtra("lecture_number",lecture_number);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"출석 요청중입니다.",Toast.LENGTH_LONG).show();
    }
}
