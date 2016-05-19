package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class
        AttendanceActivity extends AppCompatActivity {

    private static final int REQUEST_ATTENDANCE_NUMBER = 2;

    private static final String TAG = "Attendance";

    ArrayList<Lecture> lectureArrayList = new ArrayList<Lecture>();

    String[] txtAr;
    String[] lecture_infomation;
    String[] lecture_name;
    String[] lecture_week;
    String[] lecture_date;
    String[] lecture_number;
    String[] lecture_major;
    String[] lecture_minor;
    String[] lecture_start_time;

    String student_number;
    String attendance_number;
    String lecture_list;

    String week_change_name;
    char[] week;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();
        lecture_list = (String) intent.getSerializableExtra("lecture_list");
        student_number = (String) intent.getSerializableExtra("student_number");

        txtAr = lecture_list.split(" ");
        lecture_infomation = txtAr[1].split("/");
        lecture_number = lecture_infomation[0].split(",");
        lecture_name = lecture_infomation[1].split(",");
        lecture_week = lecture_infomation[2].split(",");
        lecture_date = lecture_infomation[3].split(",");
        lecture_major = lecture_infomation[4].split(",");
        lecture_minor = lecture_infomation[5].split(",");
        lecture_start_time = lecture_infomation[6].split(",");

        week_change_name="";

        for (int i = 0; i < lecture_name.length; i++) {

            week = lecture_week[i].toString().toCharArray();
            for(int j = 0 ; j < week.length;j++) {

                if(j != week.length-1) {
                    if (week[j] == '1') {
                        week_change_name += "월요일, ";
                    } else if (week[j] == '2') {
                        week_change_name += "화요일, ";
                    } else if (week[j] == '3') {
                        week_change_name += "수요일, ";
                    } else if (week[j] == '4') {
                        week_change_name += "목요일, ";
                    } else if (week[j] == '5') {
                        week_change_name += "금요일, ";
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
            lectureArrayList.add(new Lecture(lecture_number[i],lecture_name[i], week_change_name, lecture_date[i],lecture_major[i],lecture_minor[i],lecture_start_time[i]));
//            lectureArrayList.add(new Lecture(lecture_number[i],lecture_name[i], lecture_week[i], lecture_date[i],lecture_major[i],lecture_minor[i],lecture_start_time[i]));
            week_change_name="";
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.item_list_lecture, lectureArrayList);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.item_list_lecture, lectureArrayList);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void init_Lecture(ArrayList<Lecture> arrayList) {
        lectureArrayList = arrayList;
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        TextView textView_lecture_name;
        TextView textView_lecture_date;
        TextView textView_lecture_week;
        int layout;
        LayoutInflater inflater;

        public MyAdapter(Context context, int layout, ArrayList<Lecture> lectureArrayList) {
            this.context = context;
            this.layout = layout;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return lectureArrayList.size();
        }

        public Object getItem(int position) {
            return lectureArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(layout, null);

            textView_lecture_name = (TextView) convertView.findViewById(R.id.lecture_name);
            textView_lecture_week = (TextView) convertView.findViewById(R.id.lecture_week);
            textView_lecture_date = (TextView) convertView.findViewById(R.id.lecture_date);

            final Lecture lecture = lectureArrayList.get(position);

            textView_lecture_name.setText(lecture.lecture_name);


            String[] a = lecture.lecture_week.split(",");

            char[] b = lecture.lecture_date.toString().toCharArray();

                textView_lecture_week.setText(a[0]+b[0]+"교시"+" , ");
                textView_lecture_date.setText(a[1]+b[1]+"교시");

//                textView_lecture_week.setText(lecture.lecture_week);
//                textView_lecture_date.setText(lecture.lecture_date);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    ServerThread serverThread = new ServerThread(REQUEST_ATTENDANCE_NUMBER,student_number,lecture_number[position]);
//
//                    serverThread.start();
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    attendance_number = serverThread.request_attendance_number();

                    attendance_number = "110010201";


                    Intent intent = new Intent(context, CheckAttendandce.class);
                    intent.putExtra("Lecture_name", lecture.lecture_name);
                    intent.putExtra("Lecture_week", lecture.lecture_week);
                    intent.putExtra("Lecture_date", lecture.lecture_date);
                    intent.putExtra("attendance_number",attendance_number);
                    intent.putExtra("student_number",student_number);
                    intent.putExtra("lecture_number",lecture_number[position]);
                    intent.putExtra("lecture_start_time",lecture_start_time[position]);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
