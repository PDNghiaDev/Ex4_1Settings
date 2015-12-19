package com.gmail.pdnghiadev.ex4_1settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gmail.pdnghiadev.ex4_1settings.common.UserContract;

public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "SettingsActivity";
    private TextView mTvTime;
    private SeekBar mSkTime;
    private SwitchCompat mSwHighScore;
    private int time;
    private boolean recored;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_settings);

        loadComponents();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSkTime.setOnSeekBarChangeListener(this);
        mSwHighScore.setOnCheckedChangeListener(this);

        sharedPreferences = getSharedPreferences(UserContract.SETTINGS_PREFERENCE, Context.MODE_PRIVATE);

        time = sharedPreferences.getInt(UserContract.SETTINGS_TIME, 10);
        recored = sharedPreferences.getBoolean(UserContract.SETTINGS_HIGHSCORE, true);
        mSwHighScore.setChecked(recored);
        mSkTime.setProgress(time);

    }

    private void loadComponents() {
        mTvTime = (TextView) findViewById(R.id.tv_setting_time);
        mSkTime = (SeekBar) findViewById(R.id.sk_setting_time);
        mSwHighScore = (SwitchCompat) findViewById(R.id.sw_setting_highscore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor = sharedPreferences.edit();
        editor.putInt(UserContract.SETTINGS_TIME, time);
        editor.putBoolean(UserContract.SETTINGS_HIGHSCORE, recored);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        time = progress;
        mTvTime.setText(time + " sec");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mTvTime.setText(time + " sec");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTvTime.setText(time + " sec");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sw_setting_highscore) {
            recored = isChecked;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
