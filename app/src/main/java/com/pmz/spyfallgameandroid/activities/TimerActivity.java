package com.pmz.spyfallgameandroid.activities;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pmz.spyfallgameandroid.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.getSettings;

@EActivity
public class TimerActivity extends BaseActivity {

    @ViewById
    TextView timeField;
    @ViewById
    Button startTimer;
    @ViewById
    Button stopTimer;

    private static final String FORMAT = "%02d:%02d:%02d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity_.class);
            }
        });
    }

    @Click(R.id.startTimer)
    public void startTimer() {
        startTimer.setVisibility(View.INVISIBLE);
        stopTimer.setVisibility(View.VISIBLE);

        new CountDownTimer(TimeUnit.MINUTES.toMillis(getSettings().getTimerMinutes()), 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                timeField.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {

                timeField.setText(R.string.times_up);
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
                mp.start();

                stopTimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.stop();
                        openActivity(MainActivity_.class);
                    }
                });
            }
        }.start();
    }
}
