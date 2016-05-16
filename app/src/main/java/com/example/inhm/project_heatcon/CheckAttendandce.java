package com.example.inhm.project_heatcon;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckAttendandce extends AppCompatActivity {

    private static final int REQUEST_ATTENDANCE_CHECK_NUMBER = 6;

    boolean check_time =false;

    public static Context mContext;

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
    String date_number;

    char [] week;
    char [] attendance_number_arr;
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

        Log.d("LOGINING","1"+intent.getStringExtra("Lecture_week"));

        date_number = intent.getStringExtra("Lecture_date");  //A ,09:00


        switch(date_number) {

        }

        String week_number = intent.getStringExtra("Lecture_week");
        week = week_number.toCharArray();

        attendance_number_arr = attendance_number.toCharArray();

        for(int i = 0 ; i < attendance_number_arr.length;i++) {
            if(attendance_number_arr[i] == '0'){
                attendance++;
            }else if( attendance_number_arr[i] == '1') {
                absent++;
            } else if( attendance_number_arr[i] == '2') {
                late++;
            }
        }

        textView_attendance_number.setText("" + attendance);
        textView_absent_number.setText("" + absent);
        textView_late_number.setText("" + late);


        String format = new String("yyyyMMddHHmmss");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
        Log.d("LOGINING", sdf.format(new Date()));
        Calendar cal = Calendar.getInstance();
        String strweek = null;

        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        strweek = Integer.toString(nWeek-1);

        Log.d("LOGINING",strweek);

        Log.d("LOGINING",Character.toString(week[0]));
        Log.d("LOGINING",Character.toString(week[1]));

        if(Character.toString(week[0]).equals(strweek) || Character.toString(week[1]).equals(strweek)){
            if(check_time) {
                button_attendance_check.setEnabled(true);
            }
            else {
                MyThread myThread = new MyThread();
                myThread.start();
            }
        } else {
            button_attendance_check.setEnabled(false);
        }


        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
//
//    public void button_enable(boolean check) {
//        if(check){
//            button_attendance_check.setEnabled(check);
//        } else {
//            button_attendance_check.setEnabled(check);
//        }
//    }

    public void onButton1Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(),RecoMonitoringActivity.class);
        intent.putExtra("student_number",student_number);
        intent.putExtra("lecture_number", lecture_number);
        startActivity(intent);
    }

    class MyThread extends Thread {
        public void run() {


            while(!check_time) {
                long now = System.currentTimeMillis();

                Date date = new Date(now);
                SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");
                String strCurTime = CurTimeFormat.format(date);
                Log.d("LOGINING", strCurTime);
                String[] Time = strCurTime.split(":");
                String[] date1 = date_number.split(":");


                if(Integer.parseInt(Time[0]) < Integer.parseInt(date1[0])){
                    Log.d("LOGINING","수업전이야 임마");
                    Log.d("LOGINING",""+Time[0]+":"+Time[1]+"시간입니다!!!!!!");
                    Log.d("LOGINING", "" + date1[0] + ":" + date1[1] + "시간입니다!!!!!!");
                    check_time =false;
                    handler.sendEmptyMessage(3);

                } else if(Integer.parseInt(Time[0]) >= Integer.parseInt(date1[0]) && Integer.parseInt(Time[0]) <= Integer.parseInt(date1[0])+1){
                    if(Integer.parseInt(Time[0]) >= Integer.parseInt(date1[0])+5 && Integer.parseInt(Time[0]) >= Integer.parseInt(date1[0])+40)
                    Log.d("LOGINING","수업시간이야");
                    check_time = true;
                    handler.sendEmptyMessage(2);
                    break;
                } else {
                    Log.d("LOGINING",""+Time[0]+":"+Time[1]+"시간입니다!!!!!!");
                    Log.d("LOGINING","수업 끝났어 임마");
                    check_time = false;

                    handler.sendEmptyMessage(1);
                    break;
                }

            }
        }
    }
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    button_attendance_check.setEnabled(check_time);
                    break;
                case 2:
                    button_attendance_check.setEnabled(check_time);
                    break;
                case 3:
                    button_attendance_check.setEnabled(check_time);
            }
        }
    };

}
