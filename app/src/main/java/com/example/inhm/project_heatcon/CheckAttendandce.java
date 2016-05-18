package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final int REQUEST_LATE_NUMBER = 7;
    private static final int REQUEST_ABSENT_NUMBER = 8;
    private static final int REQUEST_MID_NUMBER = 3;
    private static final int REQUEST_FINAL_NUMBER = 4;

    private static final int AFTER = 1;
    private static final int ATTENDANCE = 2;
    private static final int LATE = 3;
    private static final int BEFORE = 4;

    boolean check_time =false;

    int number=0;
    String attendance_number;
    String student_number;
    String lecture_number;
    String lecture_start_time;

    String week_number;
    String strweek;
    String str;

    TextView textView_name;
    TextView textView_week;
    TextView textView_date;

    TextView textView_attendance_number;
    Button textView_absent_number;
    Button textView_late_number;

    Button button_attendance_check;

    int attendance = 0;
    int absent = 0;
    int late = 0;
    String date_number;

    char [] week;
    char [] attendance_number_arr;

    String[] date1;
    String[] date_time;
    String format;
    SimpleDateFormat sdf;
    String today_date;
    String check_check;
    String buttoncheck;
    Intent intent;
    public static Context mContext;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String[] week_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);
        button_attendance_check = (Button)findViewById(R.id.button_attendance_check);

        textView_name = (TextView)findViewById(R.id.textView_name);
        textView_week = (TextView)findViewById(R.id.textView_week);
        textView_date = (TextView)findViewById(R.id.textView_date);
        textView_attendance_number = (TextView)findViewById(R.id.attendance_number);
        textView_absent_number = (Button)findViewById(R.id.absent_number);
        textView_late_number = (Button)findViewById(R.id.late_number);
        intent = getIntent();


        textView_name.setText(intent.getStringExtra("Lecture_name"));
        textView_week.setText(intent.getStringExtra("Lecture_week"));
        textView_date.setText(intent.getStringExtra("Lecture_date"));
        attendance_number = (String) intent.getSerializableExtra("attendance_number");
        student_number = (String) intent.getSerializableExtra("student_number");
        lecture_number = (String) intent.getSerializableExtra("lecture_number");
        lecture_start_time = (String) intent.getSerializableExtra("lecture_start_time");
        Log.d("LOGINING", "" + attendance_number);

        Log.d("LOGINING","1"+intent.getStringExtra("Lecture_week"));
        date_number = intent.getStringExtra("Lecture_date");  //A ,09:00

        week_number = intent.getStringExtra("Lecture_week");

        week_change = week_number.split(",");

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

        format = new String("yyyyMMddHHmmss");
        sdf = new SimpleDateFormat(format, Locale.KOREA);
        Log.d("LOGINING44444444", sdf.format(new Date()));
        today_date = sdf.format(new Date());
        Calendar cal = Calendar.getInstance();
        strweek = null;
        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        strweek = Integer.toString(nWeek-1);

        if(strweek == "1") {
            strweek = "월요일";
        } else if(strweek == "2"){
            strweek = "화요일";
        }else if(strweek == "3"){
            strweek = "수요일";
        }else if(strweek == "4"){
            strweek = "목요일";
        }else if(strweek == "5"){
            strweek = "금요일";
        }

        Log.d("LOGINING",strweek);

        SharedPreferences sharedPreferences = getSharedPreferences("FLAG", 0);
        str=sharedPreferences.getString("FLAG","");
        SharedPreferences sharedPreferences2 = getSharedPreferences("BUTTONCHECK", 0);
        buttoncheck = sharedPreferences2.getString(lecture_number+"BUTTONCHECK","");
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        textView_attendance_number.setText("" + attendance);
        textView_absent_number.setText("" + absent);
        textView_late_number.setText("" + late);

        if(buttoncheck.equals("FALSE")){
            if(week_change[0] == strweek || week_change[1] == strweek){
                Log.d("LOGINING_FLAG",""+str);
                if(str.equals("TRUE")) {
                    button_attendance_check.setEnabled(true);
                    MyThread myThread = new MyThread();
                    myThread.start();
                }
                else{
                    button_attendance_check.setEnabled(false);
                }
            } else {
                button_attendance_check.setEnabled(false);
            }
        } else {
            button_attendance_check.setEnabled(false);
        }
    }

    public void onButtonTestClicked(View v) {

        preferences = getSharedPreferences("BUTTONCHECK",0);
        editor = preferences.edit();

        editor.putString(lecture_number+"BUTTONCHECK", "FALSE");
        editor.commit();

        if(mContext != null) {

            ((CheckAttendandce) (CheckAttendandce.mContext)).onResume();

        }
    }
    public void onButton1Clicked(View v) {

        switch (number){
            case AFTER:
                handler.sendEmptyMessage(AFTER);
                break;
            case ATTENDANCE:
                handler.sendEmptyMessage(ATTENDANCE);
                break;
            case LATE:
                handler.sendEmptyMessage(LATE);
                break;
            case BEFORE:
                handler.sendEmptyMessage(BEFORE);
                break;
        }
    }

    class MyThread extends Thread {
        public void run() {

                long now = System.currentTimeMillis();

                Date date = new Date(now);

                //오늘 시간 : 분
                SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");
                String strCurTime = CurTimeFormat.format(date);


                Log.d("LOGINING", strCurTime);

                Log.d("LOGINING", ""+week_number.length());
                if(week_number.length() >=2) {
                    date1 = lecture_start_time.split("-");    //수업 시간

                    if(Character.toString(week[0]).equals(strweek)){
                        date_time = date1[0].split(":");
                    } else if(Character.toString(week[1]).equals(strweek)){
                        date_time = date1[1].split(":");
                    }

                }

                String[] Time = strCurTime.split(":");      //오늘 시간

                // 23 = 요일(화요일, 수요일)
                // 9:00 - 10:00
//                String[] date1 = date_number.split(":");    //수업 시간

                if(Integer.parseInt(Time[0]) < Integer.parseInt(date_time[0])){
                    Log.d("LOGINING","수업전이야 임마");
                    Log.d("LOGINING",""+Time[0]+":"+Time[1]+"시간입니다!!!!!!");
                    Log.d("LOGINING", "" + date_time[0] + ":" + date_time[1] + "시간입니다!!!!!!");
                    check_time =false;
                    number = AFTER;

                } else if(Integer.parseInt(Time[0]) == Integer.parseInt(date_time[0]) && Integer.parseInt(Time[1]) <= Integer.parseInt(date_time[1])+5){

                    Log.d("LOGINING","수업시간이야");
                    check_time = true;
                    number = ATTENDANCE;

                }else if ((Integer.parseInt(Time[0]) == Integer.parseInt(date_time[0])&&
                        (Integer.parseInt(Time[1]) > Integer.parseInt(date_time[1])+5 &&
                                Integer.parseInt(Time[1]) <= Integer.parseInt(date_time[1])+20))){
                    check_time = true;
                    number=LATE;

                }
                else {
                    Log.d("LOGINING",""+Time[0]+":"+Time[1]+"시간입니다!!!!!!");
                    Log.d("LOGINING","수업 끝났어 임마");
                    check_time = false;
                    number = BEFORE;
                    handler.sendEmptyMessage(BEFORE);

                }

            }
        }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            preferences = getSharedPreferences("BUTTONCHECK",0);
            editor = preferences.edit();

            switch (msg.what){
                case 1:
                    //수업전
                    button_attendance_check.setEnabled(check_time);
                    editor.putString(lecture_number+"BUTTONCHECK", "FALSE");
                    editor.commit();

                    break;
                case 2:
                    //수업 시간 부터 5분까지
                    attendance_number += "0";
                    editor.putString(lecture_number+"BUTTONCHECK", "TRUE");
                    editor.commit();

                    Toast.makeText(getApplicationContext(),"출석이 정상처리되었습니다.",Toast.LENGTH_LONG).show();
                    check_check = "0";
                    ServerThread serverThread = new ServerThread(REQUEST_ATTENDANCE_CHECK_NUMBER,student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread.start();
                    break;
                case 3:
                    //지각
                    attendance_number += "2";
                    editor.putString(lecture_number+"BUTTONCHECK", "TRUE");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"출석이 지각처리되었습니다.",Toast.LENGTH_LONG).show();

                    check_check = "2";
                    ServerThread serverThread1 = new ServerThread(REQUEST_ATTENDANCE_CHECK_NUMBER,student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread1.start();
                    break;
                case 4:
                    //결석
                    attendance_number += "1";
                    editor.putString(lecture_number+"BUTTONCHECK","TRUE");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"출석이 결석처리되었습니다.",Toast.LENGTH_LONG).show();

                    check_check = "1";
                    ServerThread serverThread2 = new ServerThread(REQUEST_ATTENDANCE_CHECK_NUMBER,student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread2.start();
                    break;
            }
        }
    };


    public void onButtonMidClicked(View v) {
        //중간고사
//        ServerThread serverThread = new ServerThread(REQUEST_MID_NUMBER,student_number,lecture_number);
//        serverThread.start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String request_mid_score = serverThread.request_mid_score();

        String request_mid_score = "100";  //Test

        Intent intent = new Intent(getApplicationContext(),Mid.class);
        intent.putExtra("request_mid_score",request_mid_score);
        intent.putExtra("Lecture_name", textView_name.getText().toString());

        startActivity(intent);

    }
    public void onButtonFinalClicked(View v) {
        //기말고사
//        ServerThread serverThread = new ServerThread(REQUEST_FINAL_NUMBER,student_number,lecture_number);
//        serverThread.start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String request_final_score = serverThread.request_final_score();

        String request_final_score = "80";

        Intent intent = new Intent(getApplicationContext(),Final.class);
        intent.putExtra("request_final_score",request_final_score);
        intent.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent);

    }
    public void onButtonAbsentClicked(View v) {
        //결석
//        ServerThread serverThread = new ServerThread(REQUEST_ABSENT_NUMBER,student_number,lecture_number);
//        serverThread.start();
//        String request_absent = serverThread.request_absent();
//
        String request_absent = "20160504,20160511,20160513";
        Intent intent = new Intent(getApplicationContext(),Absent.class);
        intent.putExtra("request_absent",request_absent);
        intent.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent);



    }
    public void onButtonLateClicked(View v) {
        //지각
//        ServerThread serverThread = new ServerThread(REQUEST_LATE_NUMBER,student_number,lecture_number);
//        serverThread.start();
//        String request_late = serverThread.request_late();

        String request_late= "20160518,20160519";
        Intent intent = new Intent(getApplicationContext(),Late.class);
        intent.putExtra("request_late", request_late);
        intent.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent);


    }
}
