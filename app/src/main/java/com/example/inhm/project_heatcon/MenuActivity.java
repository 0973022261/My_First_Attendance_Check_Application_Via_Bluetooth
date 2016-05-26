package com.example.inhm.project_heatcon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *  로그인화면 이후 메뉴 화면입니다.
 *  버튼 5개가 있는 화면입니다.
 */
public class MenuActivity extends Activity {

    ///////////////Activity////////////////////
    LogInActivity logInActivity = new LogInActivity();
    ServerThread serverThread = new ServerThread();

    ///////////////String//////////////////////
    String student_number;
    String input_lecture_list;
    String input_food_list;

    ///////////////Intent//////////////////////
    Intent intent_AttendanceActivity;
    Intent intent_TimeTableActivity;
    Intent intent_FoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        /**
         * 로그인 학번을 preference에서 읽어온다.
         * 변수에 학번을 저장시킨다.
         */
        logInActivity.sharedPreferences_student_number = getSharedPreferences("student_number",0);
        student_number = logInActivity.sharedPreferences_student_number.getString("student_number", "");
        Log.d("MenuActivity",student_number);
    }

    public void onButtonAttendClicked(View v) {

        /**
         * 수강목록버튼을 누르면 서버에 수강목록요청코드와 학번 데이터를 보낸다.
         * 서버에서는 요청코드와 학번데이터를 받은뒤 수강목록 리스트를 보내준다.
         * 받은 수강목록 리스트를 수강목록액티비티에 인텐트로 넘겨준다.
         */
        serverThread = new ServerThread(serverThread.REQUEST_MENU_NUMBER(),student_number);
        Log.d("MenuActivity","서버 접속중");
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        input_lecture_list = serverThread.request_lecture_list();

        Log.d("MenuActivity","수강목록 리스트 request 값 = "+input_lecture_list);

        intent_AttendanceActivity = new Intent(this, AttendanceActivity.class);
        intent_AttendanceActivity.putExtra("lecture_list", student_number + " " + input_lecture_list);
        intent_AttendanceActivity.putExtra("student_number", student_number);

        Log.d("MenuActivity", "다음 수강목록화면으로 넘길때 인텐트 값 = "+student_number + " " + input_lecture_list);
        startActivity(intent_AttendanceActivity);
    }

    public void onButtonTimeTableClicked(View v) {

        /**
         * 시간표버튼을 누르면 서버에 수강목록요청코드와 학번 데이터를 보낸다.
         * 서버에서는 요청코드와 학번데이터를 받은뒤 수강목록 리스트를 보내준다.
         * 받은 수강목록 리스트를 수강목록액티비티에 인텐트로 넘겨준다.
         */
        serverThread = new ServerThread(serverThread.REQUEST_MENU_NUMBER(),student_number);
        Log.d("MenuActivity","서버 접속중");
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        input_lecture_list = serverThread.request_lecture_list();
        Log.d("MenuActivity","수강목록 리스트 request 값 = "+input_lecture_list);

        intent_TimeTableActivity = new Intent(this, TimeTableActivity.class);
        intent_TimeTableActivity.putExtra("student_number", student_number);
        intent_TimeTableActivity.putExtra("input",input_lecture_list);
        Log.d("MenuActivity", "다음 수강목록화면으로 넘길때 인텐트 값 = " + student_number + " " + input_lecture_list);
        startActivity(intent_TimeTableActivity);
    }

    public void onButtonFoodListClicked(View v) {


        /**
         * 식당메뉴버튼을 누르면 서버에 수강목록요청코드를 보낸다.
         * 서버에서는 요청코드를 받은뒤 음식목록 리스트를 보내준다.
         * 받은 수강목록 리스트를 FoodList액티비티에 인텐트로 넘겨준다.
         */
        serverThread = new ServerThread(serverThread.REQUEST_FOOD_NUMBER());
        Log.d("MenuActivity", "서버 접속중");
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        input_food_list = serverThread.request_food_list();
        Log.d("MenuActivity","서버에 요청한 음식리스트 request 값 = "+ input_food_list);
        intent_FoodList = new Intent(this, FoodList.class);
        intent_FoodList.putExtra("student_number", student_number);
        intent_FoodList.putExtra("food_list",input_food_list);
        Log.d("MenuActivity", "다음 학식메뉴 액티비티로 넘기는 인텐트 값 = " + input_food_list);
        startActivity(intent_FoodList);
    }

    public void onButtonUseInfoClicked(View v) {
        /**
         * 환경설정 버튼을 누르면 환경설정 액티비티가 실행된다.
         */
        Intent intent = new Intent(this, UseInfoActivity.class);
        intent.putExtra("student_number", student_number);
        startActivity(intent);
    }
}