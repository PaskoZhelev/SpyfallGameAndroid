package com.pmz.spyfallgameandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pmz.spyfallgameandroid.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.newGameBtn)
    public void newGameButtonClicked() {
        openActivity(GameActivity_.class);
    }

    @Click(R.id.settingsBtn)
    public void settingsButtonClicked() {
        openActivity(SettingsActivity_.class);
    }
}
