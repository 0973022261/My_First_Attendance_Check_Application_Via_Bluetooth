package com.example.inhm.project_heatcon;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * LogIn을 위한 액티비티 클래스이다.
 * 학번과 비밀번호를 입력받고 서버에 보내 서버 디비에 저장된 회원인지 판단한후 로그인 다음 화면으로 넘어간다.
 * 로그인이 성공하게 되면 학번을 계속 서버로 보내 확인을 한다.
 *
 **/
public class LogInActivity extends AppCompatActivity {


    ///////////////EditText///////////////////////////
    EditText editText_student_number;                           //학번
    EditText editText_student_password;                         //비밀번호

    ///////////////ServerThread_Request_number////////
    private static final int REQUEST_LOGIN_NUMBER = 1;          //서버 로그인 요청 코드
    ServerThread serverThread;

    ///////////////SharedPreferences//////////////////
    SharedPreferences sharedPreferences_student_number;
    SharedPreferences.Editor editor;

    ///////////////String/////////////////////////////
    String string_student_number;

    //////////////Intent//////////////////////////////
    Intent intent_MenuActivity;

    /////////////Boolean//////////////////////////////
    boolean login_Check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        /**
         * 학번 변수와 비밀번호 변수를 선언해서 findViewById로 선언합니다.
         */
        editText_student_number = (EditText) findViewById(R.id.editText_student_number);
        editText_student_password = (EditText) findViewById(R.id.editText_student_password);//비번

        /**
         * 로그인 했던 아이디 불러오기
         *
         * sharedPreferences를 이용해서 학번과 비밀번호를 저장한다.
         * 먼저 getSharedPreferences()함수를 이용하여 저장될 변수명을 선언한다.
         * getSharedPreferences의 파라미터는 저장할 변수명과 모드를 입력해야한다.
         *
         */
        sharedPreferences_student_number = getSharedPreferences("student_number",0);
        string_student_number = sharedPreferences_student_number.getString("student_number", "");
        editText_student_number.setText(string_student_number);

        Log.d("LoginActivity", editText_student_number.toString());
    }

    @Override
    protected void onResume() {super.onResume();}

    public void onButton1Clicked(View v) {

        /**
         *  서버스레드 클래스 변수를 설정하고 생성자를 불러옵니다.
         *  불러온 뒤에는 start()메소드를 실행합니다.
         *  서버 요청을 한뒤 데이터를 받기까지 시간이 걸리므로 약 1초의 시간동안 스레드를 sleep합니다.
         *  스레드가 깨어나면 서버 스레드의 request_attendance메소드를 불러와 Boolean으로 Ture, false를 불러옵니다.
         */
        serverThread = new ServerThread(REQUEST_LOGIN_NUMBER, editText_student_number.getText().toString(), editText_student_password.getText().toString(), true);
        serverThread.start();
        Log.d("LoginActivity", "서버접속중");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        login_Check = serverThread.request_attendance();
        Log.d("LoginActivity", "서버로 부터 받은 request 값 = " + login_Check);

        /**
         * 서버에서 받아온 로그인 체크를 통해 True 이면 Menu액티비티를 실행합니다.
         * 이때 로그인 아이디를 Preferences를 통해 저장을 합니다.
         * 로그인 정보가 틀리면 Toast 메세지를 통해 "학번 또는 비밀번호를 확인해주세요" 라는 문구를 띄어준다.
         */

        if (login_Check == true) {

            sharedPreferences_student_number = getSharedPreferences("student_number", 0);
            editor = sharedPreferences_student_number.edit();
            editor.putString("student_number", editText_student_number.getText().toString());
            editor.commit();

            intent_MenuActivity = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent_MenuActivity);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "학번 또는 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
        }
    }
}