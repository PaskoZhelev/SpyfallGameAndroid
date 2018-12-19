package com.pmz.spyfallgameandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pmz.spyfallgameandroid.R;

import org.androidannotations.annotations.EActivity;

@EActivity
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
