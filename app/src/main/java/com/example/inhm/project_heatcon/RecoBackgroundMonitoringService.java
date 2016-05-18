/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.inhm.project_heatcon;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.prefs.PreferencesFactory;

/**
 *
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행합니다.
 *
 */
public class RecoBackgroundMonitoringService extends Service implements RECOMonitoringListener, RECOServiceConnectListener {
    /**
     * 1초 스캔, 10초 간격으로 스캔, 60초의 region expiration time은 당사 권장사항입니다.
     */

    Socket socket;                              //소켓통신을 위한 소켓 객체 변수 생성
    String host = "54.199.247.89";               //소켓 통신을 위한 서버 host 주소
    int port = 8000;                            //소켓 통신을 위한 서버 port 번호

    public boolean flag = false;
    private long mScanDuration = 1 * 1000L;
    private long mSleepDuration = 10 * 1000L;
    private long mRegionExpirationTime = 60 * 1000L;

    private int mNotificationID = 9999;
    private static final int OUR_BEACON_MAJOR = 921;
    private RECOBeaconManager mRecoManager;
    private ArrayList<RECOBeaconRegion> mRegions;

    //////1번째 실행////
    @Override
    public void onCreate() {
        Log.d("LOGINING3", "onCreate()");

        super.onCreate();
    }


    //////2번째 실행//////
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOGINGING3", "onStartCommand()");
        /**
         *
         * RECOBeaconManager 인스턴스틀 생성합니다. (스캔 대상 및 백그라운드 ranging timeout 설정)
         * RECO만을 스캔하고, 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * 주의: enableRangingTimeout을 false로 설정 시, 배터리 소모량이 증가합니다.
         */
        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), RecoActivity.SCAN_RECO_ONLY, true);
        Log.d("LOGINGING3", "mRecoManager = "+mRecoManager);
        this.bindRECOService();
        return START_STICKY;
    }


    //////3번쨰 실행///////
    private void bindRECOService() {
        Log.d("LOGINING3", "bindRECOService()");

        mRegions = new ArrayList<RECOBeaconRegion>();
        Log.d("LOGINGING3", "mRegions = "+mRegions);
        this.generateBeaconRegion();

        mRecoManager.setMonitoringListener(this);
        mRecoManager.bind(this);
    }


    /////4번쨰 실행 //////////

    /**
     * 비콘 지역 객체를 선언한 후 recoRegion 변수에 인스턴스를 생성한다.
     * recoRegion은 1분간격으로 지역을 검색할 것이고,
     * 여기 우리 비콘 지역 채널 921 만 찾게하였다.
     * ArrayList에 객체를 저장한다(찾을경우)
     */

    private void generateBeaconRegion() {
        Log.d("LOGINING3", "generateBeaconRegion()");

        RECOBeaconRegion recoRegion;

        recoRegion = new RECOBeaconRegion(RecoActivity.RECO_UUID, OUR_BEACON_MAJOR, "교실");
        recoRegion.setRegionExpirationTimeMillis(mRegionExpirationTime);   //1분 간격으로 찾음
        Log.d("LOGINGING3", "recoRegion="+recoRegion);
        mRegions.add(recoRegion);
    }


    ////////5번째 실행///////
    @Override
    public void onServiceConnect() {
        Log.d("LOGINING3", "onServiceConnect()");
        this.startMonitoring();
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }


    //////////6번째 실행///////
    //서비스가 커넥트 될시 불리는 함수
    private void startMonitoring() {
        Log.d("LOGINING3", "startMonitoring()");

        mRecoManager.setScanPeriod(mScanDuration);
        mRecoManager.setSleepPeriod(mSleepDuration);
        Log.d("LOGINGING3", "region = ");
        for (RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.startMonitoringForRegion(region);
                Log.d("LOGINGING5", "mRecoManager = startMonitoringforregion");
            } catch (RemoteException e) {
                Log.e("LOGINING5", "RemoteException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("LOGINING5", "NullPointerException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }


    /////////7번째 실행//////////
    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion region) {
        Log.d("LOGINING3", "didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
        //Write the code when starting monitoring the region is started successfully
    }


    //////////8번째 실행/////////
    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
        Log.d("LOGINING3", "didDetermineStateForRegion()");
        //Write the code when the state of the monitored region is changed
    }


    @Override
    public void onDestroy() {
        Log.d("LOGINING3", "onDestroy()");
        this.tearDown();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("BackMonitoringService", "onTaskRemoved()");
        super.onTaskRemoved(rootIntent);
    }

    private void stopMonitoring() {
        Log.i("BackMonitoringService", "stopMonitoring()");

        for (RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void tearDown() {
        Log.i("BackMonitoringService", "tearDown()");
        this.stopMonitoring();

        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.e("BackMonitoringService", "RemoteException has occured while executing unbind()");
            e.printStackTrace();
        }
    }


    @Override
    public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
        /**
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        //Get the region and found beacon list in the entered region
        Log.d("LOGINING3", "didEnterRegion() - " + region.getUniqueIdentifier());
        this.popupNotification("출석이 가능합니다." + region.getUniqueIdentifier());
        flag =true;


        Log.d("LOGINGING3", ""+flag);
        SharedPreferences preferences = getSharedPreferences("FLAG",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("FLAG","TRUE");
        editor.commit();

        //Write the code when the device is enter the region

    }


    @Override
    public void didExitRegion(RECOBeaconRegion region) {
        /**
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        Log.i("BackMonitoringService", "didExitRegion() - " + region.getUniqueIdentifier());
        this.popupNotification("비콘지역으로부터 멀어졌습니다." + region.getUniqueIdentifier());
        flag = false;
        SharedPreferences preferences = getSharedPreferences("FLAG",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("FLAG", "FALSE");
        editor.commit();

    }

        private void popupNotification(String msg) {
        Log.i("BackMonitoringService", "popupNotification()");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg + " " + currentTime)
                .setContentText(msg);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        builder.setStyle(inboxStyle);
        nm.notify(mNotificationID, builder.build());
        mNotificationID = (mNotificationID - 1) % 1000 + 9000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This method is not used
        return null;
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed to monitor the region.
        //See the RECOErrorCode in the documents.
        return;
    }
}
