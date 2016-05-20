package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = "Attendance";

    ///////////////ServerThread////////////////////
    ServerThread serverThread = new ServerThread();

    ///////////////ArrayList////////////////////
    ArrayList<Lecture> lectureArrayList = new ArrayList<Lecture>();

    ///////////////Intent////////////////////
    Intent intent_get;
    Intent intent_CheckAttendance;

    ///////////////ListView////////////////////
    ListView listView_listView;

    ///////////////MyAdapter////////////////////
    MyAdapter adapter_item_list_lecture;

    ///////////////Lecture////////////////////
    Lecture lecture;

    ///////////////String[]////////////////////
    String[] lecture_list_split;
    String[] lecture_infomation_split_by_slash;
    String[] lecture_number_split_by_comma;
    String[] lecture_name_split_by_comma;
    String[] lecture_week_split_by_comma;
    String[] lecture_date_split_by_comma;
    String[] lecture_major_split_by_comma;
    String[] lecture_minor_split_by_comma;
    String[] lecture_start_time_split_by_comma;
    char[] week_toCharArray;

    ///////////////String////////////////////
    String student_number;
    String attendance_number;
    String lecture_list;
    String week_change_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        /**
         * 메뉴 액티비티 인텐트를 getIntent()메소드로 받는다.
         * 수강목록리스트 데이터도 인텐트로 받는다.
         * 학번 데이터도 인텐트로 받는다.
         */
        intent_get = getIntent();
        lecture_list = (String) intent_get.getSerializableExtra("lecture_list");
        student_number = (String) intent_get.getSerializableExtra("student_number");


        /**
         * 수강목록 리스트에는 여러 토큰이 존재한다(" " , "/" , ",")
         * 토큰을 나누어 수강과목정보를 split()메소드로 나눈다.
         */
        lecture_list_split = lecture_list.split(" ");
        lecture_infomation_split_by_slash = lecture_list_split[1].split("/");
        lecture_number_split_by_comma = lecture_infomation_split_by_slash[0].split(",");
        lecture_name_split_by_comma = lecture_infomation_split_by_slash[1].split(",");
        lecture_week_split_by_comma = lecture_infomation_split_by_slash[2].split(",");
        lecture_date_split_by_comma = lecture_infomation_split_by_slash[3].split(",");
        lecture_major_split_by_comma = lecture_infomation_split_by_slash[4].split(",");
        lecture_minor_split_by_comma = lecture_infomation_split_by_slash[5].split(",");
        lecture_start_time_split_by_comma = lecture_infomation_split_by_slash[6].split(",");


        /**
         * 수강 요일 자료는 숫자로 넘어오기때문에 숫자를 조건문을통해 월~금까지로 바꿔준다.
         */
        week_change_name="";
        for (int i = 0; i < lecture_name_split_by_comma.length; i++) {

            week_toCharArray = lecture_week_split_by_comma[i].toString().toCharArray();
            for(int j = 0 ; j < week_toCharArray.length;j++) {

                if(j != week_toCharArray.length-1) {
                    if (week_toCharArray[j] == '1') {
                        week_change_name += "월요일, ";
                    } else if (week_toCharArray[j] == '2') {
                        week_change_name += "화요일, ";
                    } else if (week_toCharArray[j] == '3') {
                        week_change_name += "수요일, ";
                    } else if (week_toCharArray[j] == '4') {
                        week_change_name += "목요일, ";
                    } else if (week_toCharArray[j] == '5') {
                        week_change_name += "금요일, ";
                    }
                } else {
                    if (week_toCharArray[j] == '1') {
                        week_change_name += "월요일";
                    } else if (week_toCharArray[j] == '2') {
                        week_change_name += "화요일";
                    } else if (week_toCharArray[j] == '3') {
                        week_change_name += "수요일";
                    } else if (week_toCharArray[j] == '4') {
                        week_change_name += "목요일";
                    } else if (week_toCharArray[j] == '5') {
                        week_change_name += "금요일";
                    }
                }
            }

            /**
             * split()을 통한 변수들은 각각 ArrayList에 저장된다.
             */
            lectureArrayList.add(new Lecture(lecture_number_split_by_comma[i],lecture_name_split_by_comma[i], week_change_name, lecture_date_split_by_comma[i],lecture_major_split_by_comma[i],lecture_minor_split_by_comma[i],lecture_start_time_split_by_comma[i]));
            week_change_name="";
        }

        /**
         * 어댑터를 연결하고 리스트뷰를 띄어준다.
         */
        adapter_item_list_lecture = new MyAdapter(getApplicationContext(), R.layout.item_list_lecture, lectureArrayList);
        listView_listView = (ListView) findViewById(R.id.listView);
        listView_listView.setAdapter(adapter_item_list_lecture);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * BaseAdapter를 상속받는 MyAdapter는 리스트뷰를 띄우기위한 클래스 함수이다.
     */
    class MyAdapter extends BaseAdapter {

        Context context;

        TextView textView_lecture_name;
        TextView textView_lecture_one;
        TextView textView_lecture_two;

        int layout;

        LayoutInflater inflater;

        String[] lecture_week_split_by_comma;
        char[] lecture_date_toCharArray;

        public MyAdapter(Context context, int layout, ArrayList<Lecture> lectureArrayList) {
            this.context = context;
            this.layout = layout;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            /**
             * ArrayList의 사이즈를 받아서 리턴해준다.
             */
            return lectureArrayList.size();
        }

        public Object getItem(int position) {
            /**
             * ArrayList의 아이템을 position을 통해 꺼내온다.
             */
            return lectureArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            /**
             * ListView를 보여주는 getView()메소드이다.
             */
            if (convertView == null)
                convertView = inflater.inflate(layout, null);

            /**
             * ArrayList에 있는 과목을 가져온다.
             */
            lecture = lectureArrayList.get(position);


            /**
             * 텍스트 뷰를 메모리에 올린다.(findViewById)
             */
            textView_lecture_name = (TextView) convertView.findViewById(R.id.lecture_name);
            textView_lecture_one = (TextView) convertView.findViewById(R.id.lecture_week);
            textView_lecture_two = (TextView) convertView.findViewById(R.id.lecture_date);
            textView_lecture_name.setText(lecture.lecture_name);


            /**
             * 수강 요일을 split으로 나눈다.
             * 수강 교시를 split으로 나눈다.
             */
            lecture_week_split_by_comma = lecture.lecture_week.split(",");
            lecture_date_toCharArray = lecture.lecture_date.toString().toCharArray();

            /**
             * 텍스트 뷰의 텍스트를 설정한다.
             */
            textView_lecture_one.setText(lecture_week_split_by_comma[0]+lecture_date_toCharArray[0]+"교시"+" , ");
            textView_lecture_two.setText(lecture_week_split_by_comma[1] + lecture_date_toCharArray[1] + "교시");

            /**
             * 리스트 뷰의 한 목록이 선택될 경우 실행되는 이벤트 메소드
             */
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /**
                     * 요청코드와 학번, 과목번호를 서버에 전송한다.
                     * 서버에서는 과목번호에 맞는 출결현황(출석,지각,결석) 데이터를 전송한다.
                     */
                    serverThread = new ServerThread(serverThread.REQUEST_ATTENDANCE_NUMBER(),student_number,lecture_number_split_by_comma[position]);

                    serverThread.start();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    attendance_number = serverThread.request_attendance_number();

//                    attendance_number = "110010201";


                    intent_CheckAttendance = new Intent(context, CheckAttendance.class);
                    intent_CheckAttendance.putExtra("Lecture_name", lecture.lecture_name);
                    intent_CheckAttendance.putExtra("Lecture_week", lecture.lecture_week);
                    intent_CheckAttendance.putExtra("Lecture_date", lecture.lecture_date);
                    intent_CheckAttendance.putExtra("attendance_number",attendance_number);
                    intent_CheckAttendance.putExtra("student_number",student_number);
                    intent_CheckAttendance.putExtra("lecture_number",lecture_number_split_by_comma[position]);
                    intent_CheckAttendance.putExtra("lecture_start_time",lecture_start_time_split_by_comma[position]);
                    startActivity(intent_CheckAttendance);
                }
            });
            return convertView;
        }
    }
}
