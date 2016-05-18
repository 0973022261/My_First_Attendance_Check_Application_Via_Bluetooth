package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    ArrayList<Lecture> lectureArrayList = new ArrayList<Lecture>();
    TextView textView;
    String[] txtAr;
    String[] lecture_infomation;
    String[] lecture_name;
    String[] lecture_week;
    String[] lecture_date;
    String[] lecture_number;
    String[] lecture_major;
    String[] lecture_minor;
    String[] lecture_start_time;
    String week_change_name;
    char[] week;
    char[] date_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Log.d("LOGINING^^^^^^^^^^^^", "onCreate호출됨");

//        LinearLayout containor = (LinearLayout) findViewById(R.id.layout_);
//
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        inflater.inflate(R.layout.timetable_item, containor, true);
//
//        LinearLayout containor_mon_a = (LinearLayout) findViewById(R.id.monday_a);
//        LinearLayout containor_mon_1 = (LinearLayout) findViewById(R.id.monday_1);
//
//        inflater.inflate(R.layout.moday_a, containor_mon_a, true);
//        inflater.inflate(R.layout.moday_1, containor_mon_1, true);
//
//        LinearLayout containor_tues_a = (LinearLayout) findViewById(R.id.tuesday_a);
//        LinearLayout containor_tues_1 = (LinearLayout) findViewById(R.id.tuesday_1);
//
//        inflater.inflate(R.layout.tuesday_a, containor_tues_a, true);
//        inflater.inflate(R.layout.tuesday_1, containor_tues_1, true);
//
//        LinearLayout containor_wed_a = (LinearLayout) findViewById(R.id.wedday_a);
//        LinearLayout containor_wed_1 = (LinearLayout) findViewById(R.id.wedday_1);
//
//        inflater.inflate(R.layout.wed_a, containor_wed_a, true);
//        inflater.inflate(R.layout.wed_1, containor_wed_1, true);
//
//        LinearLayout containor_thur_a = (LinearLayout) findViewById(R.id.thursday_a);
//        LinearLayout containor_thur_1 = (LinearLayout) findViewById(R.id.thursday_1);
//
//        inflater.inflate(R.layout.thursday_a, containor_thur_a, true);
//        inflater.inflate(R.layout.thursday_1, containor_thur_1, true);
//
//        LinearLayout containor_fri_a = (LinearLayout) findViewById(R.id.friday_a);
//        LinearLayout containor_fri_1 = (LinearLayout) findViewById(R.id.friday_1);
//
//        inflater.inflate(R.layout.friday_a, containor_fri_a, true);
//        inflater.inflate(R.layout.friday_1, containor_fri_1, true);

        Log.d("LOGINING^^^^^^^^^^^^", "인플레이터 끝");


        Intent intent = getIntent();


//        String input = (String) intent.getSerializableExtra("input");

        String input = "20133252 1/OS/11/A2/921/1/09:00-10:00";
        Log.d("LOGINING^^^^^^^^^^^^", ""+input);
        txtAr = input.split(" ");
        lecture_infomation = txtAr[1].split("/");


        Log.d("LOGINING^^^^^^^^^^^^", ""+txtAr[1]);
        lecture_number = lecture_infomation[0].split(",");
        lecture_name = lecture_infomation[1].split(",");
        lecture_week = lecture_infomation[2].split(",");
        lecture_date = lecture_infomation[3].split(",");
        lecture_major = lecture_infomation[4].split(",");
        lecture_minor = lecture_infomation[5].split(",");
        lecture_start_time = lecture_infomation[6].split(",");

        week_change_name = "";

        for (int i = 0; i < lecture_name.length; i++) {
            week = lecture_week[i].toString().toCharArray();
            Log.d("LOGINING^^^^^^^^^^^^", ""+week.length);
            for (int j = 0; j < week.length; j++) {

                if (j != week.length - 1) {
                    if (week[j] == '1') {
                        week_change_name += "월요일,";
                    } else if (week[j] == '2') {
                        week_change_name += "화요일,";
                    } else if (week[j] == '3') {
                        week_change_name += "수요일,";
                    } else if (week[j] == '4') {
                        week_change_name += "목요일,";
                    } else if (week[j] == '5') {
                        week_change_name += "금요일,";
                    }
                } else {
                    if (week[j] == '1') {
                        week_change_name += "월요일";
                    } else if (week[j] == '2') {
                        week_change_name += "화요일";
                    } else if (week[j] == '3') {
                        week_change_name += "수요일";
                    } else if (week[j] == '4') {
                        week_change_name += "목요일";
                    } else if (week[j] == '5') {
                        week_change_name += "금요일";
                    }
                }
            }
            Log.d("LOGINING^^^^^^^^^^^^", ""+week_change_name);

//            lectureArrayList.add(new Lecture(lecture_number[i], lecture_name[i], week_change_name, lecture_date[i], lecture_major[i], lecture_minor[i], lecture_start_time[i]));
// 1 / OS / 월요일,화요일 / A,2 / 921 / 1 / 09:00

            String[] a = week_change_name.split(",");

            date_date = lecture_date[i].toString().toCharArray();


            Log.d("LOGINING^^^^^^^^^^^^",""+a[i]);
            switch (a[i]){
                case "월요일":
                    switch (date_date[i]){
                        case '1':

                            textView = (TextView)findViewById(R.id.mon_1);
                            textView.setText(lecture_name[i]);
                            break;
                        case '2':
                            textView = (TextView)findViewById(R.id.mon_2);
                            textView.setText(lecture_name[i]);
                            break;
                        case '3':
                            textView = (TextView)findViewById(R.id.mon_3);
                            textView.setText(lecture_name[i]);
                            break;
                        case '4':
                            textView = (TextView)findViewById(R.id.mon_4);
                            textView.setText(lecture_name[i]);
                            break;
                        case '5':
                            textView = (TextView)findViewById(R.id.mon_5);
                            textView.setText(lecture_name[i]);
                            break;
                        case '6':
                            textView = (TextView)findViewById(R.id.mon_6);
                            textView.setText(lecture_name[i]);
                            break;
                        case '7':
                            textView = (TextView)findViewById(R.id.mon_7);
                            textView.setText(lecture_name[i]);
                            break;
                        case '8':
                            textView = (TextView)findViewById(R.id.mon_8);
                            textView.setText(lecture_name[i]);
                            break;
                        case '9':
                            textView = (TextView)findViewById(R.id.mon_9);
                            textView.setText(lecture_name[i]);
                            break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                        case 'A':
                            textView = (TextView)findViewById(R.id.mon_a);
                            textView.setText(""+lecture_name[i]);
                            break;
                        case 'B':
                            textView = (TextView)findViewById(R.id.mon_b);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'C':
                            textView = (TextView)findViewById(R.id.mon_c);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'D':
                            textView = (TextView)findViewById(R.id.mon_d);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'E':
                            textView = (TextView)findViewById(R.id.mon_e);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'F':
                            textView = (TextView)findViewById(R.id.mon_f);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'G':
                            textView = (TextView)findViewById(R.id.mon_g);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'H':
                            textView = (TextView)findViewById(R.id.mon_h);
                            textView.setText(lecture_name[i]);
                            break;
                        case 'I':
                            textView = (TextView)findViewById(R.id.mon_i);
                            textView.setText(lecture_name[i]);
                            break;
                    }

                    break;
                case "화요일":
                    break;
                case "수요일":
                    break;
                case "목요일":
                    break;
                case "금요일":
                    break;
            }
        }

//        inflater.inflate(R.layout.timetable_item, containor, true);
//        inflater.inflate(R.layout.moday_a, containor_mon_a, true);
//        inflater.inflate(R.layout.moday_1, containor_mon_1, true);
//        inflater.inflate(R.layout.tuesday_a, containor_tues_a, true);
//        inflater.inflate(R.layout.tuesday_1, containor_tues_1, true);
//        inflater.inflate(R.layout.wed_a, containor_wed_a, true);
//        inflater.inflate(R.layout.wed_1, containor_wed_1, true);
//        inflater.inflate(R.layout.thursday_a, containor_thur_a, true);
//        inflater.inflate(R.layout.thursday_1, containor_thur_1, true);
//        inflater.inflate(R.layout.friday_a, containor_fri_a, true);
//        inflater.inflate(R.layout.friday_1, containor_fri_1, true);

    }
    }
