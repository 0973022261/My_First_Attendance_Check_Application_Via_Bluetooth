package com.example.inhm.project_heatcon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckAttendance extends AppCompatActivity {


    ///////////////ServerThread////////////////////
    ServerThread serverThread = new ServerThread();

    ///////////////static final int////////////////
    private static final int AFTER = 1;
    private static final int ATTENDANCE = 2;
    private static final int LATE = 3;
    private static final int BEFORE = 4;

    boolean check_time =false;

    ///////////////TextView////////////////
    TextView textView_name;
    TextView textView_week;
    TextView textView_date;
    TextView textView_attendance_number;

    ///////////////Button////////////////
    Button textView_absent_number;
    Button textView_late_number;
    Button button_attendance_check;

    ///////////////Bluetooth////////////////
    private BluetoothManager mBluetoothManager;                                         //블루투스 매니저 객체 변수
    private BluetoothAdapter mBluetoothAdapter;                                         //블루투스 어댑터 객체 변수

    ///////////////String////////////////
    String attendance_number;
    String student_number;
    String lecture_number;
    String lecture_start_time;

    String week_number;
    String date_number;

    String strweek;
    String str;
    String today_date;
    String check_check;
    String buttoncheck;
    String format;
    String lecture_name;
    String request_mid_score;
    String request_final_score;
    String request_absent;
    String request_late;

    ////////////////String[]///////////////
    String[] week_change;
    String[] date1;
    String[] date_time;
    String[] lecture_week_split_by_comma;
    char[] lecture_date_split_by_comma;
    char [] week;
    char [] attendance_number_arr;

    /////////////////////int////////////////
    int attendance = 0;
    int absent = 0;
    int late = 0;
    int number=0;
    int nWeek;

    Calendar cal;
    SimpleDateFormat sdf;

    Intent intent_get;
    Intent intent_Mid;
    Intent intent_Final;
    Intent intent_Absent;
    Intent intent_Late;

    public static Context mContext;
    MyThread myThread;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);

        /**
         * 버튼과 텍스트뷰를 레이아웃에서 찾아서 메모리에 올린다.
         */
        button_attendance_check = (Button)findViewById(R.id.button_attendance_check);
        textView_name = (TextView)findViewById(R.id.textView_name);
        textView_week = (TextView)findViewById(R.id.textView_week);
        textView_date = (TextView)findViewById(R.id.textView_date);
        textView_attendance_number = (TextView)findViewById(R.id.attendance_number);
        textView_absent_number = (Button)findViewById(R.id.absent_number);
        textView_late_number = (Button)findViewById(R.id.late_number);

        /**
         * 인텐트를 받아옵니다.
         */
        intent_get = getIntent();

        /**
         * 인텐트로 받은 lecture_name,week_number,date_number,attendace_number
         * ,student_number,lecture_number,lecture_start_time 을 변수에 넣습니다.
         */
        lecture_name = intent_get.getStringExtra("Lecture_name");
        week_number = intent_get.getStringExtra("Lecture_week");
        date_number = intent_get.getStringExtra("Lecture_date");
        attendance_number = (String) intent_get.getSerializableExtra("attendance_number");
        student_number = (String) intent_get.getSerializableExtra("student_number");
        lecture_number = (String) intent_get.getSerializableExtra("lecture_number");
        lecture_start_time = (String) intent_get.getSerializableExtra("lecture_start_time");

        /**
         * 받아온 데이터(week)를 토큰(,) 으로 나눕니다.
         * 받아온 데이터(date)를 Char단위로 나눕니다.
         */
        lecture_week_split_by_comma = week_number.split(",");
        lecture_date_split_by_comma = date_number.toCharArray();

        /**
         * 텍스트 뷰의 텍스트를 설정합니다.
         */
        textView_name.setText(lecture_name);
        textView_week.setText(lecture_week_split_by_comma[0]+lecture_date_split_by_comma[0]+"교시");
        textView_date.setText(lecture_week_split_by_comma[1]+lecture_date_split_by_comma[1]+"교시");

        /**
         * 출결현황(출석,지각,결석) 데이터를 알기 위하여 attendance_number에 각각 저장한다.
         */
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
        /**
         * 요일을 token(,)으로 따로 저장합니다. ex- 1,2 (월,화)
         */
        week_change = week_number.split(",");

        /**
         * 오늘 년도, 월, 날짜, 시간,분,초 단위의 포멧을 설정합니다.
         * Korea 지역의 date를 가져옵니다.
         */
        format = new String("yyyyMMddHHmmss");
        sdf = new SimpleDateFormat(format, Locale.KOREA);
        today_date = sdf.format(new Date());

        /**
         * 현재 요일을 받아옵니다.
         * integer값으로 들어오는데 1,2,3,4,5,6,7 (일,월,화,수,목,금,토) 로 들어옵니다.
         * 월요일을 1로 하기위해 -1을 합니다.
         */
        cal = Calendar.getInstance();
        nWeek = cal.get(Calendar.DAY_OF_WEEK);
        strweek = Integer.toString(nWeek - 1);

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
                mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  출석요청 버튼을 눌렀는지 안눌렀는지를 판단합니다.
         *  출석이 진행되었는지 판단합니다.
         */
        SharedPreferences sharedPreferences = getSharedPreferences("FLAG", 0);
        str=sharedPreferences.getString("FLAG","");
        SharedPreferences sharedPreferences2 = getSharedPreferences(lecture_number+"BUTTONCHECK", 0);
        buttoncheck = sharedPreferences2.getString(lecture_number+"BUTTONCHECK","");

        /**
         * 출결상황을 다시 한번 띄어줍니다.(업데이트 목적)
         */
        serverThread = new ServerThread(serverThread.REQUEST_ATTENDANCE_NUMBER(),student_number,lecture_number);
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        attendance_number = serverThread.request_attendance_number();
        attendance_number_arr = attendance_number.toCharArray();

        attendance = 0;
        absent = 0;
        late = 0;

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

        /**
         * 버튼이 체크되었는지 확인합니다.
         *
         * <조건1.>
         * 버튼체크가 "FALSE" 이면 버튼이 눌려있지 않은 상태이므로 버튼을 활성화 시킵니다.
         * 버튼 체크가 "TRUE" 이면 버튼이 눌려있는 상태이므로 버튼을 비활성화 시킵니다.
         * <조건2.>
         * 과목요일을 현재 요일과 비교하여 일치하면 버튼을 활성화 시킵니다.
         * 과목요일중 하나라도 맞지 않는다면 버튼을 비활성화 시킵니다.
         * <조건3.>
         * 출석체크가 이전에 진행되지 않았으면 버튼을 활성화 시킵니다.
         * 출석체크가 이미 진행되었다면 버튼을 비활성화 시킵니다.
         */
        if(buttoncheck.equals("FALSE")){
            if(week_change[0] == strweek || week_change[1] == strweek){
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

        /**
         * 새로고침 버튼을 클릭하면 BUTTONCHECK를 FALSE로 바꿔준다.
         */
        preferences = getSharedPreferences(lecture_number+"BUTTONCHECK",0);
        editor = preferences.edit();
        editor.putString(lecture_number+"BUTTONCHECK", "FALSE");
        editor.commit();

        /**
         * 블루투스 매니저와 어댑터를 설정합니다.
         */
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        /**
         * 만일 블루투스가 켜져있다면 블루투스를 껏다가 켜줍니다.
         * 블루투스가 꺼져있다면 블루투스를 킵니다.
         */
        if(mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON ||
                mBluetoothAdapter.getState() == mBluetoothAdapter.STATE_ON){
            mBluetoothAdapter.disable();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mBluetoothAdapter.enable();
        }
        else {
            mBluetoothAdapter.enable();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * onResume()함수를 불러옵니다.
         */
        if(mContext != null) {
            ((CheckAttendance) (CheckAttendance.mContext)).onResume();
        }
    }
    public void onButton1Clicked(View v) {

        /**
         * 출석 요청 버튼을 클릭하면 핸들러에서 메세지를 보냅니다.
         * 수업 전인지, 출석중인지, 지각인지, 결석인지를 판단합니다.
         */
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

        /**
         * 토스트 메세지로 출석체크 진행중임을 보여줍니다.
         */
        Toast toast = Toast.makeText(getApplicationContext(),"출석 체크를 진행합니다.",Toast.LENGTH_LONG);
        int offsetX = 0;
        int offsetY = 0;
        toast.setGravity(Gravity.CENTER,offsetX,offsetY);
        toast.show();
    }

    class MyThread extends Thread {
        long now;
        Date date;
        SimpleDateFormat CurTimeFormat;
        String strCurTime;
        String[] Time;
        public void run() {

            /**
             * 현재 시간을 받아옵니다 ("시 : 분")
             */
            now = System.currentTimeMillis();
            date = new Date(now);

            CurTimeFormat = new SimpleDateFormat("HH:mm");
            strCurTime = CurTimeFormat.format(date);

            /**
             *  시간을 split을 통해 시, 분을 나눕니다.
             */
            if(week_number.length() >=2) {
                date1 = lecture_start_time.split("-");
                if(Character.toString(week[0]).equals(strweek)){
                        date_time = date1[0].split(":");
                    } else if(Character.toString(week[1]).equals(strweek)){
                        date_time = date1[1].split(":");
                    }

                }

            /**
             * 오늘 시간을 변수에 저장합니다.
             */
            Time = strCurTime.split(":");

            /**
             * 오늘 시간과 수업시간을 비교합니다.
             * 수업시간 이전, 수업시간~ 5분후, 수업시간 5분후~ 수업시간 20분후, 수업시간 20분후
             * 총 4개의 부분으로 나누어 판단합니다.
             * 수업시간 이전에 들어오면 check_time = false로 처리합니다.
             * 수업시간 이후에 들어오면 check_time = true로 처리합니다.
             */
            if(Integer.parseInt(Time[0]) < Integer.parseInt(date_time[0])){
                    check_time =false;
                    number = AFTER;
            }else if(Integer.parseInt(Time[0]) == Integer.parseInt(date_time[0]) && Integer.parseInt(Time[1]) <= Integer.parseInt(date_time[1])+5){
                    check_time = true;
                    number = ATTENDANCE;
            }else if ((Integer.parseInt(Time[0]) == Integer.parseInt(date_time[0])&&
                    (Integer.parseInt(Time[1]) > Integer.parseInt(date_time[1])+5 &&
                            Integer.parseInt(Time[1]) <= Integer.parseInt(date_time[1])+20))){
                    check_time = true;
                    number=LATE;
            }else {
                    check_time = false;
                    number = BEFORE;
                    handler.sendEmptyMessage(BEFORE);
                }

            }
        }

    /**
     * 핸들러 함수에서는 메세지를 받아 처리합니다.
     */
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
                    serverThread = new ServerThread(serverThread.REQUEST_ATTENDANCE_CHECK_NUMBER(),student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread.start();
                    break;
                case 3:
                    //지각
                    attendance_number += "2";
                    editor.putString(lecture_number+"BUTTONCHECK", "TRUE");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"출석이 지각처리되었습니다.",Toast.LENGTH_LONG).show();
                    check_check = "2";
                    serverThread = new ServerThread(serverThread.REQUEST_ATTENDANCE_CHECK_NUMBER(),student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread.start();
                    break;
                case 4:
                    //결석
                    attendance_number += "1";
                    editor.putString(lecture_number+"BUTTONCHECK","TRUE");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"출석이 결석처리되었습니다.",Toast.LENGTH_LONG).show();
                    check_check = "1";
                    serverThread = new ServerThread(serverThread.REQUEST_ATTENDANCE_CHECK_NUMBER(),student_number,lecture_number,attendance_number,check_check,today_date);
                    serverThread.start();
                    break;
            }
        }
    };



    public void onButtonMidClicked(View v) {

        /**
         *  중간고사 점수를 서버에서 요청한후 받아옵니다.
         */
        //중간고사
        serverThread = new ServerThread(serverThread.REQUEST_MID_NUMBER(),student_number,lecture_number);
        serverThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        request_mid_score = serverThread.request_mid_score();

        intent_Mid = new Intent(getApplicationContext(),Mid.class);
        intent_Mid.putExtra("request_mid_score",request_mid_score);
        intent_Mid.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent_Mid);

    }
    public void onButtonFinalClicked(View v) {
        /**
         *  기말고사 점수를 서버에서 요청한후 받아옵니다.
         */
        //기말고사
        serverThread = new ServerThread(serverThread.REQUEST_FINAL_NUMBER(),student_number,lecture_number);
        serverThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        request_final_score = serverThread.request_final_score();

        intent_Final = new Intent(getApplicationContext(),Final.class);
        intent_Final.putExtra("request_final_score",request_final_score);
        intent_Final.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent_Final);
    }
    public void onButtonAbsentClicked(View v) {
        /**
         *  결석날짜를 서버에서 요청한후 받아옵니다.
         */
        //결석
        serverThread = new ServerThread(serverThread.REQUEST_ABSENT_NUMBER(),student_number,lecture_number);
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        request_absent = serverThread.request_absent();

        intent_Absent = new Intent(getApplicationContext(),Absent.class);
        intent_Absent.putExtra("request_absent",request_absent);
        intent_Absent.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent_Absent);
    }
    public void onButtonLateClicked(View v) {
        /**
         *  지각날짜를 서버에서 요청한후 받아옵니다.
         */
        //지각
        serverThread = new ServerThread(serverThread.REQUEST_LATE_NUMBER(),student_number,lecture_number);
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        request_late = serverThread.request_late();

        intent_Late = new Intent(getApplicationContext(),Late.class);
        intent_Late.putExtra("request_late", request_late);
        intent_Late.putExtra("Lecture_name", textView_name.getText().toString());
        startActivity(intent_Late);
    }
}
