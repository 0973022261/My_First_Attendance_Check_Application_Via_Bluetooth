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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * RECOMonitoringActivity 클래스는 foreground 상태에서 monitoring을 수행합니다.
 */
public class RecoMonitoringActivity extends RecoActivity implements RECOMonitoringListener {

    /**
     * 1초 스캔, 10초 간격으로 스캔, 60초의 region expiration time은 당사 권장사항입니다.
     **/

    private long mScanPeriod = 1*1000L;
    private long mSleepPeriod = 10*1000L;

    private boolean mInitialSetting = true;

    private static final String TAG = "ATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking);

        //mRecoManager will be created here. (Refer to the RECOActivity.onCreate())
        //mRecoManager 인스턴스는 여기서 생성됩니다. RECOActivity.onCreate() 메소들르 참고하세요.

        //Set RECOMonitoringListener (Required)
        //RECOMonitoringListener 를 설정합니다. (필수)
        mRecoManager.setMonitoringListener(this);

        //Set scan period and sleep period.
        //The default is 1 second for the scan period and 10 seconds for the sleep period.
        mRecoManager.setScanPeriod(mScanPeriod);
        mRecoManager.setSleepPeriod(mSleepPeriod);
        /**
         * RECOServiceConnectListener와 함께 RECOBeaconManager를 bind 합니다. RECOServiceConnectListener는 RECOActivity에 구현되어 있습니다.
         * monitoring 및 ranging 기능을 사용하기 위해서는, 이 메소드가 "반드시" 호출되어야 합니다.
         * bind후에, onServiceConnect() 콜백 메소드가 호출됩니다. 콜백 메소드 호출 이후 monitoring / ranging 작업을 수행하시기 바랍니다.
         */
        mRecoManager.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stop(mRegions);
        this.unbind();
    }

    @Override
    public void onServiceConnect() {
        this.start(mRegions);
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState recoRegionState, RECOBeaconRegion recoRegion) {
        mInitialSetting = false;
    }

    @Override
    public void didEnterRegion(RECOBeaconRegion recoRegion, Collection<RECOBeacon> beacons) {
        /**
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */
    }


    @Override
    public void didExitRegion(RECOBeaconRegion recoRegion) {

        /**
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion recoRegion) {
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                region.setRegionExpirationTimeMillis(60*1000L);
                mRecoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
