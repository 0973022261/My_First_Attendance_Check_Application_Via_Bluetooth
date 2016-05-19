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

    String[] week_week;
    char[] week;
    char[] date_date;
    String[] a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Intent intent = getIntent();

        String input = (String) intent.getSerializableExtra("input");
//        Log.d("LOGINING^^^^^^^^^^^^", ""+input);
//        String input = "20133252 1/OS/11/A2/921/1/09:00-10:00";

        lecture_infomation = input.split("/");

//        Log.d("LOGINING000000000000", ""+lecture_infomation[0]);
//        Log.d("LOGINING111111111111", ""+lecture_infomation[1]);
//        Log.d("LOGINING222222222222", ""+lecture_infomation[2]);
//        Log.d("LOGINING333333333333", ""+lecture_infomation[3]);
//        Log.d("LOGINING444444444444", ""+lecture_infomation[4]);
//        Log.d("LOGINING555555555555", ""+lecture_infomation[5]);
//        Log.d("LOGINING666666666666", ""+lecture_infomation[6]);
//
        lecture_number = lecture_infomation[0].split(",");
        lecture_name = lecture_infomation[1].split(",");
        lecture_week = lecture_infomation[2].split(",");
        lecture_date = lecture_infomation[3].split(",");
        lecture_major = lecture_infomation[4].split(",");
        lecture_minor = lecture_infomation[5].split(",");
        lecture_start_time = lecture_infomation[6].split(",");

        week_change_name = "";

        week_week = new String[lecture_name.length];
//        Log.d("LOGINING_week_week길이", ""+week_week.length);

        for (int i = 0; i < week_week.length; i++) {
            week = lecture_week[i].toString().toCharArray();
//            Log.d("LOGINING^^^^^^^^^^^^", "" + week.length);
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

                week_week[i] = week_change_name;
//                Log.d("LOGINING_week_week[i] = ", i+""+week_week[i]);

            }
            week_change_name ="";
        }

        for(int i = 0; i <week_week.length;i++) {
            Log.d("LOGINING@@@@@@@@"," "+week_week.length);
            Log.d("LOGINING@@@@@@@@",i+" "+week_week[i]);
            a = week_week[i].split(",");

            for (int j = 0; j < a.length; j++) {
                date_date = lecture_date[i].toString().toCharArray();
                Log.d("LOGINING^^^^^^^^^^^^", "a의 길이 =" + a.length);
                Log.d("LOGINING^^^^^^^^^^^^", "" + a[j]);
                switch (a[j]) {
                    case "월요일":
                        switch (date_date[j]) {
                            case '1':
                                textView = (TextView) findViewById(R.id.mon_1);
                                textView.setText(lecture_name[i]);
                                break;
                            case '2':
                                textView = (TextView) findViewById(R.id.mon_2);
                                textView.setText(lecture_name[i]);
                                break;
                            case '3':
                                textView = (TextView) findViewById(R.id.mon_3);
                                textView.setText(lecture_name[i]);
                                break;
                            case '4':
                                textView = (TextView) findViewById(R.id.mon_4);
                                textView.setText(lecture_name[i]);
                                break;
                            case '5':
                                textView = (TextView) findViewById(R.id.mon_5);
                                textView.setText(lecture_name[i]);
                                break;
                            case '6':
                                textView = (TextView) findViewById(R.id.mon_6);
                                textView.setText(lecture_name[i]);
                                break;
                            case '7':
                                textView = (TextView) findViewById(R.id.mon_7);
                                textView.setText(lecture_name[i]);
                                break;
                            case '8':
                                textView = (TextView) findViewById(R.id.mon_8);
                                textView.setText(lecture_name[i]);
                                break;
                            case '9':
                                textView = (TextView) findViewById(R.id.mon_9);
                                textView.setText(lecture_name[i]);
                                break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                            case 'A':
                                textView = (TextView) findViewById(R.id.mon_a);
                                textView.setText("" + lecture_name[i]);
                                break;
                            case 'B':
                                textView = (TextView) findViewById(R.id.mon_b);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'C':
                                textView = (TextView) findViewById(R.id.mon_c);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'D':
                                textView = (TextView) findViewById(R.id.mon_d);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'E':
                                textView = (TextView) findViewById(R.id.mon_e);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'F':
                                textView = (TextView) findViewById(R.id.mon_f);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'G':
                                textView = (TextView) findViewById(R.id.mon_g);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'H':
                                textView = (TextView) findViewById(R.id.mon_h);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'I':
                                textView = (TextView) findViewById(R.id.mon_i);
                                textView.setText(lecture_name[i]);
                                break;
                        }

                        break;
                    case "화요일":
                        switch (date_date[j]) {
                            case '1':

                                textView = (TextView) findViewById(R.id.tues_1);
                                textView.setText(lecture_name[i]);
                                break;
                            case '2':
                                textView = (TextView) findViewById(R.id.tues_2);
                                textView.setText(lecture_name[i]);
                                break;
                            case '3':
                                textView = (TextView) findViewById(R.id.tues_3);
                                textView.setText(lecture_name[i]);
                                break;
                            case '4':
                                textView = (TextView) findViewById(R.id.tues_4);
                                textView.setText(lecture_name[i]);
                                break;
                            case '5':
                                textView = (TextView) findViewById(R.id.tues_5);
                                textView.setText(lecture_name[i]);
                                break;
                            case '6':
                                textView = (TextView) findViewById(R.id.tues_6);
                                textView.setText(lecture_name[i]);
                                break;
                            case '7':
                                textView = (TextView) findViewById(R.id.tues_7);
                                textView.setText(lecture_name[i]);
                                break;
                            case '8':
                                textView = (TextView) findViewById(R.id.tues_8);
                                textView.setText(lecture_name[i]);
                                break;
                            case '9':
                                textView = (TextView) findViewById(R.id.tues_9);
                                textView.setText(lecture_name[i]);
                                break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                            case 'A':
                                textView = (TextView) findViewById(R.id.tues_a);
                                textView.setText("" + lecture_name[i]);
                                break;
                            case 'B':
                                textView = (TextView) findViewById(R.id.tues_b);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'C':
                                textView = (TextView) findViewById(R.id.tues_c);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'D':
                                textView = (TextView) findViewById(R.id.tues_d);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'E':
                                textView = (TextView) findViewById(R.id.tues_e);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'F':
                                textView = (TextView) findViewById(R.id.tues_f);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'G':
                                textView = (TextView) findViewById(R.id.tues_g);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'H':
                                textView = (TextView) findViewById(R.id.tues_h);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'I':
                                textView = (TextView) findViewById(R.id.tues_i);
                                textView.setText(lecture_name[i]);
                                break;
                        }

                        break;
                    case "수요일":
                        switch (date_date[j]) {
                            case '1':

                                textView = (TextView) findViewById(R.id.wed_1);
                                textView.setText(lecture_name[i]);
                                break;
                            case '2':
                                textView = (TextView) findViewById(R.id.wed_2);
                                textView.setText(lecture_name[i]);
                                break;
                            case '3':
                                textView = (TextView) findViewById(R.id.wed_3);
                                textView.setText(lecture_name[i]);
                                break;
                            case '4':
                                textView = (TextView) findViewById(R.id.wed_4);
                                textView.setText(lecture_name[i]);
                                break;
                            case '5':
                                textView = (TextView) findViewById(R.id.wed_5);
                                textView.setText(lecture_name[i]);
                                break;
                            case '6':
                                textView = (TextView) findViewById(R.id.wed_6);
                                textView.setText(lecture_name[i]);
                                break;
                            case '7':
                                textView = (TextView) findViewById(R.id.wed_7);
                                textView.setText(lecture_name[i]);
                                break;
                            case '8':
                                textView = (TextView) findViewById(R.id.wed_8);
                                textView.setText(lecture_name[i]);
                                break;
                            case '9':
                                textView = (TextView) findViewById(R.id.wed_9);
                                textView.setText(lecture_name[i]);
                                break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                            case 'A':
                                textView = (TextView) findViewById(R.id.wed_a);
                                textView.setText("" + lecture_name[i]);
                                break;
                            case 'B':
                                textView = (TextView) findViewById(R.id.wed_b);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'C':
                                textView = (TextView) findViewById(R.id.wed_c);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'D':
                                textView = (TextView) findViewById(R.id.wed_d);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'E':
                                textView = (TextView) findViewById(R.id.wed_e);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'F':
                                textView = (TextView) findViewById(R.id.wed_f);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'G':
                                textView = (TextView) findViewById(R.id.wed_g);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'H':
                                textView = (TextView) findViewById(R.id.wed_h);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'I':
                                textView = (TextView) findViewById(R.id.wed_i);
                                textView.setText(lecture_name[i]);
                                break;
                        }

                        break;
                    case "목요일":
                        switch (date_date[j]) {
                            case '1':

                                textView = (TextView) findViewById(R.id.thur_1);
                                textView.setText(lecture_name[i]);
                                break;
                            case '2':
                                textView = (TextView) findViewById(R.id.thur_2);
                                textView.setText(lecture_name[i]);
                                break;
                            case '3':
                                textView = (TextView) findViewById(R.id.thur_3);
                                textView.setText(lecture_name[i]);
                                break;
                            case '4':
                                textView = (TextView) findViewById(R.id.thur_4);
                                textView.setText(lecture_name[i]);
                                break;
                            case '5':
                                textView = (TextView) findViewById(R.id.thur_5);
                                textView.setText(lecture_name[i]);
                                break;
                            case '6':
                                textView = (TextView) findViewById(R.id.thur_6);
                                textView.setText(lecture_name[i]);
                                break;
                            case '7':
                                textView = (TextView) findViewById(R.id.thur_7);
                                textView.setText(lecture_name[i]);
                                break;
                            case '8':
                                textView = (TextView) findViewById(R.id.thur_8);
                                textView.setText(lecture_name[i]);
                                break;
                            case '9':
                                textView = (TextView) findViewById(R.id.thur_9);
                                textView.setText(lecture_name[i]);
                                break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                            case 'A':
                                textView = (TextView) findViewById(R.id.thur_a);
                                textView.setText("" + lecture_name[i]);
                                break;
                            case 'B':
                                textView = (TextView) findViewById(R.id.thur_b);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'C':
                                textView = (TextView) findViewById(R.id.thur_c);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'D':
                                textView = (TextView) findViewById(R.id.thur_d);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'E':
                                textView = (TextView) findViewById(R.id.thur_e);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'F':
                                textView = (TextView) findViewById(R.id.thur_f);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'G':
                                textView = (TextView) findViewById(R.id.thur_g);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'H':
                                textView = (TextView) findViewById(R.id.thur_h);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'I':
                                textView = (TextView) findViewById(R.id.thur_i);
                                textView.setText(lecture_name[i]);
                                break;
                        }

                        break;
                    case "금요일":
                        switch (date_date[j]) {
                            case '1':

                                textView = (TextView) findViewById(R.id.fri_1);
                                textView.setText(lecture_name[i]);
                                break;
                            case '2':
                                textView = (TextView) findViewById(R.id.fri_2);
                                textView.setText(lecture_name[i]);
                                break;
                            case '3':
                                textView = (TextView) findViewById(R.id.fri_3);
                                textView.setText(lecture_name[i]);
                                break;
                            case '4':
                                textView = (TextView) findViewById(R.id.fri_4);
                                textView.setText(lecture_name[i]);
                                break;
                            case '5':
                                textView = (TextView) findViewById(R.id.fri_5);
                                textView.setText(lecture_name[i]);
                                break;
                            case '6':
                                textView = (TextView) findViewById(R.id.fri_6);
                                textView.setText(lecture_name[i]);
                                break;
                            case '7':
                                textView = (TextView) findViewById(R.id.fri_7);
                                textView.setText(lecture_name[i]);
                                break;
                            case '8':
                                textView = (TextView) findViewById(R.id.fri_8);
                                textView.setText(lecture_name[i]);
                                break;
                            case '9':
                                textView = (TextView) findViewById(R.id.fri_9);
                                textView.setText(lecture_name[i]);
                                break;
//                        case '10':
//                            textView = (TextView)containor_mon_1.findViewById(R.id.mon10);
//                            textView.setText(lecture_name[i]);
//                            break;
                            case 'A':
                                textView = (TextView) findViewById(R.id.fri_a);
                                textView.setText("" + lecture_name[i]);
                                break;
                            case 'B':
                                textView = (TextView) findViewById(R.id.fri_b);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'C':
                                textView = (TextView) findViewById(R.id.fri_c);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'D':
                                textView = (TextView) findViewById(R.id.fri_d);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'E':
                                textView = (TextView) findViewById(R.id.fri_e);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'F':
                                textView = (TextView) findViewById(R.id.fri_f);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'G':
                                textView = (TextView) findViewById(R.id.fri_g);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'H':
                                textView = (TextView) findViewById(R.id.fri_h);
                                textView.setText(lecture_name[i]);
                                break;
                            case 'I':
                                textView = (TextView) findViewById(R.id.fri_i);
                                textView.setText(lecture_name[i]);
                                break;
                        }
                        break;


                }
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
            }
        }
    }
}
