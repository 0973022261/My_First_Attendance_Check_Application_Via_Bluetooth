package com.example.inhm.project_heatcon;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * LogIn을 위한 액티비티 클래스이다.
 * 학번과 비밀번호를 입력받고 서버에 보내 서버 디비에 저장된 회원인지 판단한후 로그인 다음 화면으로 넘어간다.
 * 로그인이 성공하게 되면 학번을 계속 서버로 보내 확인을 한다.
 *
 **/
public class LogInActivity extends AppCompatActivity {


    EditText editText_student_number;                           //학번
    EditText editText_student_password;                         //비밀번호

    private static final int REQUEST_LOGIN_NUMBER = 1;          //서버 로그인 요청 코드

    boolean login_Check;

    private static final int REQUEST_ENABLE_BT = 1;                                     //Bluetooth 연결 요청 변수

    private BluetoothManager mBluetoothManager;                                         //블루투스 매니저 객체 변수
    private BluetoothAdapter mBluetoothAdapter;                                         //블루투스 어댑터 객체 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editText_student_number = (EditText) findViewById(R.id.editText_student_number);//학번
        editText_student_password = (EditText) findViewById(R.id.editText_student_password);//비번

        //사용자가 블루투스를 켜도록 요청합니다.
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON ||
                mBluetoothAdapter.getState() == mBluetoothAdapter.STATE_ON){
//            mBluetoothAdapter.disable();
        }
        else {
            mBluetoothAdapter.enable();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
            } else {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
            }
        }


        //아이디 저장된거 불러오기

        SharedPreferences sharedPreferences = getSharedPreferences("student_number",0);
        String str=sharedPreferences.getString("student_number","");
        editText_student_number.setText(str);

        Intent intent = new Intent(this,RecoBackgroundMonitoringService.class);
        startService(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
            } else {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
            }
        }
    }

    public void onButton1Clicked(View v) {

//        ServerThread serverThread = new ServerThread(REQUEST_LOGIN_NUMBER,editText_student_number.getText().toString(),editText_student_password.getText().toString(),true);
//        serverThread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        login_Check = serverThread.request_attendance();


        login_Check = true;  ///// No server

        if (login_Check == true) {

            SharedPreferences sharedPreferences = getSharedPreferences("student_number", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("student_number",editText_student_number.getText().toString());
            editor.commit();

            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("student_number", editText_student_number.getText().toString());
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(getApplicationContext(), "학번 또는 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
        }
    }
}