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

    private long mScanDuration = 1 * 1000L;
    private long mSleepDuration = 10 * 1000L;
    private long mRegionExpirationTime = 60 * 1000L;

    private int mNotificationID = 9999;
    private static final int OUR_BEACON_MAJOR = 921;
    private RECOBeaconManager mRecoManager;
    private ArrayList<RECOBeaconRegion> mRegions;

    SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**
         * RECOBeaconManager 인스턴스틀 생성합니다. (스캔 대상 및 백그라운드 ranging timeout 설정)
         * RECO만을 스캔하고, 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * 주의: enableRangingTimeout을 false로 설정 시, 배터리 소모량이 증가합니다.
         */

        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), RecoActivity.SCAN_RECO_ONLY, true);
        this.bindRECOService();
        return START_STICKY;
    }

    private void bindRECOService() {

        mRegions = new ArrayList<RECOBeaconRegion>();
        this.generateBeaconRegion();

        mRecoManager.setMonitoringListener(this);
        mRecoManager.bind(this);
    }

    /**
     * 비콘 지역 객체를 선언한 후 recoRegion 변수에 인스턴스를 생성한다.
     * recoRegion은 1분간격으로 지역을 검색할 것이고,
     * 여기 우리 비콘 지역 채널 921 만 찾게하였다.
     * ArrayList에 객체를 저장한다(찾을경우)
     */

    private void generateBeaconRegion() {

        RECOBeaconRegion recoRegion;
        recoRegion = new RECOBeaconRegion(RecoActivity.RECO_UUID, OUR_BEACON_MAJOR, "교실");
        recoRegion.setRegionExpirationTimeMillis(mRegionExpirationTime);
        mRegions.add(recoRegion);
    }

    @Override
    public void onServiceConnect() {
        this.startMonitoring();
    }

    private void startMonitoring() {

        mRecoManager.setScanPeriod(mScanDuration);
        mRecoManager.setSleepPeriod(mSleepDuration);
        for (RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion region) {
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
    }

    @Override
    public void onDestroy() {
        this.tearDown();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    private void stopMonitoring() {
        for (RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void tearDown() {
        this.stopMonitoring();
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
        /**
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        this.popupNotification("출석이 가능한 지역입니다." + region.getUniqueIdentifier());
    }


    @Override
    public void didExitRegion(RECOBeaconRegion region) {
        /**
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        this.popupNotification("비콘지역으로부터 멀어졌습니다." + region.getUniqueIdentifier());
    }

        private void popupNotification(String msg) {
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
        return null;
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        return;
    }
}
