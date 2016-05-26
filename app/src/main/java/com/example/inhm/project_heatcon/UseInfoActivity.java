package com.example.inhm.project_heatcon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UseInfoActivity extends AppCompatActivity {

    ///////////////Bluetooth////////////////
    private BluetoothManager mBluetoothManager;                                         //블루투스 매니저 객체 변수
    private BluetoothAdapter mBluetoothAdapter;                                         //블루투스 어댑터 객체 변수

    Intent intent;
    Button button;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_info);
        button =(Button)findViewById(R.id.bluetooth);
        button.setBackgroundColor(Color.argb(255, 78, 139, 83));
        button.setText("블루투스 ON");
    }

    public void onButton1Clicked(View v) {
        //로그아웃 기능

        Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_LONG).show();
        intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
    }
    public void onButton2Clicked(View v) {
        // 블루투스
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        handler = new Handler(){
            public void handleMessage(Message msg){

                switch (msg.what){
                    case 1:
                        button.setBackgroundColor(Color.argb(55,78,139,83));
                        button.setText("블루투스 OFF");
                        break;
                    case 2:
                        button.setBackgroundColor(Color.argb(255,78,139,83));
                        button.setText("블루투스 ON");
                        break;
                }
            }
        };
        /**
         * 만일 블루투스가 켜져있다면 블루투스를 껏다가 켜줍니다.
         * 블루투스가 꺼져있다면 블루투스를 킵니다.
         */
        if(mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON ||
                mBluetoothAdapter.getState() == mBluetoothAdapter.STATE_ON){
            mBluetoothAdapter.disable();
            handler.sendEmptyMessage(1);
        }
        else {
            mBluetoothAdapter.enable();
            handler.sendEmptyMessage(2);
        }

    }
}
