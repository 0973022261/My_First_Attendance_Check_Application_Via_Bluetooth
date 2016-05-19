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

    String student_number;                          //학번
    String input;

    private static final int REQUEST_MENU_NUMBER = 5;       //서버 스레드 요청 코드
    private static final int REQUEST_FOOD_NUMBER = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Intent intent = getIntent();
        student_number = (String) intent.getSerializableExtra("student_number");
    }

    public void onButtonAttendClicked(View v) {

//        ServerThread serverThread = new ServerThread(REQUEST_MENU_NUMBER,student_number);
//        serverThread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        input = serverThread.request_lecture_list();


        input = "1,2,3,4,5/OS,COMP,디마커,모바일커뮤니케이션,광고학개론/12,23,24,15,34/AB,AC,FA,BD,AB/921,921,921,921,921/1,2,1,2,1/09:00-10:30,09:00-10:30,09:00-10:30,09:00-10:30,15:00-15:30";

        Intent intent = new Intent(this, AttendanceActivity.class);
        intent.putExtra("lecture_list", student_number + " " + input);
        intent.putExtra("student_number", student_number);
        startActivity(intent);
    }

    public void onButtonTimeTableClicked(View v) {
//                ServerThread serverThread = new ServerThread(REQUEST_MENU_NUMBER,student_number);
//        serverThread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        input = serverThread.request_lecture_list();


        input = "1,2,3,4,5/OS,COMP,디마커,모바일커뮤니케이션,광고학개론/12,23,24,15,34/A2,AC,A1,2D,A4/921,921,921,921,921/1,2,1,2,1/09:00-10:30,09:00-10:30,09:00-10:30,09:00-10:30,15:00-15:30";

        Intent intent = new Intent(this, TimeTableActivity.class);
        intent.putExtra("student_number", student_number);
        intent.putExtra("input",input);
        startActivity(intent);
    }

    public void onButtonFoodListClicked(View v) {

//        ServerThread serverThread = new ServerThread(REQUEST_FOOD_NUMBER);
//        serverThread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        input = serverThread.request_food_list();

        input = "김치찌개,된장국,시래기국/5000,4000,3000";
        Intent intent = new Intent(this, FoodList.class);
        intent.putExtra("student_number", student_number);
        intent.putExtra("food_list",input);
        startActivity(intent);
    }

    public void onButtonUseInfoClicked(View v) {
        Intent intent = new Intent(this, UseInfoActivity.class);
        intent.putExtra("student_number", student_number);
        startActivity(intent);
    }
}