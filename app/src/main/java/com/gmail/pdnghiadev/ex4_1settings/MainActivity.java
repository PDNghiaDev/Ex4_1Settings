package com.gmail.pdnghiadev.ex4_1settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


import com.gmail.pdnghiadev.ex4_1settings.common.UserContract;
import com.gmail.pdnghiadev.ex4_1settings.model.ResultItem;

import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String TAG_RESULT_FRAGMENT = "ResultFragment";
    public static final int TIME_COUNT = 10000; // 10s
    public static final String TIME_KEY = "time_key";
    public static final String NUMBER_COUNT = "number_count";
    public static final String LAST_TIME = "last_time";
    public static final String TIME = "time";
    private Button mBtnStart, mBtnTap;
    private Chronometer mTime;
    private TextView mCountTap; // Save the count tap when user press
    private long mStartTime; // Get the time when start
    private int mCount; // Save the count tap after load screen again
    private TapCountResultFragment fragment; // Fragment show list highscore
    private long mLastStopTime; //  Get the time when configuration change or press Home
    private long mTimeKey; // Get the time of Chronometer
    private long mDuration;
    private int time_count;
    private boolean recored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        loadComponents();

        // SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(UserContract.SETTINGS_PREFERENCE, MODE_PRIVATE);
        time_count = sharedPreferences.getInt(UserContract.SETTINGS_TIME, 10) * 1000;
        Log.d(TAG, "onCreate: " + time_count);
        recored = sharedPreferences.getBoolean(UserContract.SETTINGS_HIGHSCORE, true);
        Log.d(TAG, "onCreate: " + recored);
        // Tại sao khi nhấn nút Back thì List mất hết ???

        if (savedInstanceState != null) {
            mLastStopTime = savedInstanceState.getLong(LAST_TIME);
            mTimeKey = savedInstanceState.getLong(TIME_KEY);
            if (mTimeKey > 0) {
                mTime.setBase(mTimeKey);
                mBtnStart.setText("Resume");
            } else {
                mTime.setBase(SystemClock.elapsedRealtime());
            }
            mCount = savedInstanceState.getInt(NUMBER_COUNT);
            mCountTap.setText(String.valueOf(mCount));
            mDuration = savedInstanceState.getLong(TIME);
        } else {
            mLastStopTime = 0;
        }

        mTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long totalTime = time_count - mDuration;

                if (mLastStopTime == 0) { // If the screen isn't configuration change
                    if (SystemClock.elapsedRealtime() - mStartTime >= time_count) {
                        pauseTapping();
                    }
                } else { // If the screen is configuration change
                    if (SystemClock.elapsedRealtime() - mStartTime >= totalTime) {
                        pauseTapping();
                        mLastStopTime = 0;
                    }
                }

            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTapping();
            }
        });

        mBtnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                mCountTap.setText(String.valueOf(mCount));
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fragment = (TapCountResultFragment) fm.findFragmentByTag(TAG_RESULT_FRAGMENT);

        // If the Fragment is non-null, then it is being retained
        // over a configuration change
        if (fragment == null) {
            fragment = new TapCountResultFragment();
            fm.beginTransaction().add(R.id.fl_result_fragment, fragment, TAG_RESULT_FRAGMENT).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void startTapping() {

        mStartTime = SystemClock.elapsedRealtime();
        if (mLastStopTime == 0) { // If the screen isn't configuration change
            mTime.setBase(SystemClock.elapsedRealtime());
            mCount = 0;
        } else { // If the screen is configuration change
            long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
            mTime.setBase(mTimeKey + intervalOnPause);
        }
        mTime.start();
        mBtnTap.setEnabled(true);
        mBtnStart.setEnabled(false);

        mCountTap.setText(String.valueOf(mCount));
    }

    private void pauseTapping() {
        mBtnTap.setEnabled(false);
        mTime.stop();
        mBtnStart.setEnabled(true);
        mBtnStart.setText("Start");

        // Pass Object to Fragment
        if (recored) {
            fragment.onClick(new ResultItem(new Date(), mCount));
        }
    }

    private void loadComponents() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnTap = (Button) findViewById(R.id.btn_tap);
        mTime = (Chronometer) findViewById(R.id.ch_time);
        mCountTap = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mTime.stop();
        mLastStopTime = SystemClock.elapsedRealtime();
        mDuration = SystemClock.elapsedRealtime() - mStartTime;
        if (mDuration < time_count) {
            outState.putLong(TIME_KEY, mTime.getBase());
            outState.putLong(LAST_TIME, mLastStopTime);
            outState.putLong(TIME, mDuration);
            outState.putInt(NUMBER_COUNT, mCount);
        }

        mBtnStart.setEnabled(true);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Log.d(TAG, "onOptionsItemSelected: ");
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
