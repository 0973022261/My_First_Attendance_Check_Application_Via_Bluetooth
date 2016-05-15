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
    String lecture_number;

    String input;
    private static final int REQUEST_MENU_NUMBER = 5;       //서버 스레드 요청 코드

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


        input = "1,2/OS,COMP/43,15/A,B/921,921/1,2";

        Intent intent = new Intent(this, AttendanceActivity.class);
        intent.putExtra("lecture_list", student_number + " " + input);
        intent.putExtra("student_number",student_number);
//        intent.putExtra("lecture_number",lecture_number);
        startActivity(intent);
    }

    public void onButtonTimeTableClicked(View v) {
        Intent intent = new Intent(this, TimeTableActivity.class);
        intent.putExtra("student_number", student_number);
        startActivity(intent);
    }

    public void onButtonUseInfoClicked(View v) {
        Intent intent = new Intent(this, UseInfoActivity.class);
        intent.putExtra("student_number", student_number);
        startActivity(intent);
    }
}