package com.pmz.spyfallgameandroid.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmz.spyfallgameandroid.R;
import com.pmz.spyfallgameandroid.domain.GameEngine;
import com.pmz.spyfallgameandroid.domain.Location;
import com.pmz.spyfallgameandroid.util.constant.BaseConstants;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.SPY_PLAYER_NUM;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.SPY_ROLE;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.getSettings;

@EActivity
public class TimerActivity extends BaseActivity {

    @ViewById
    TextView timeField;
    @ViewById
    Button startTimer;
    @ViewById
    Button stopTimer;
    @ViewById
    Button locationsBtn;

    private CountDownTimer timer;
    private int spyPlayerNum;
    private static final String FORMAT = "%02d:%02d:%02d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        spyPlayerNum = intent.getIntExtra(SPY_PLAYER_NUM, 0);
    }

    @Click(R.id.startTimer)
    public void startTimer() {
        startTimer.setVisibility(View.INVISIBLE);
        stopTimer.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(TimeUnit.MINUTES.toMillis(getSettings().getTimerMinutes()), 1000) { // adjust the milli seconds here

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
                        stopTimer();
                    }
                });
            }
        };
        timer.start();
    }

    @Click(R.id.stopTimer)
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        stopTimer.setText(R.string.reveal_spy);
        locationsBtn.setVisibility(View.VISIBLE);
        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player = getResources().getString(R.string.player);
                timeField.setText(String.format("%s %s", player, String.valueOf(spyPlayerNum)));

                stopTimer.setText(R.string.finish);
                stopTimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(MainActivity_.class);
                    }
                });

            }
        });
    }

    @Click(R.id.locationsBtn)
    public void locationsBtnClicked() {
        activateLocationsDialog();
    }

    private void activateLocationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_locations_dialog, null);
        TextView locationsText = (TextView) view.findViewById(R.id.locationsText);
        Button okBtn2 = (Button) view.findViewById(R.id.okBtn2);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        GameEngine gameEngine = new GameEngine(getSettings(), getApplicationContext());
        StringBuilder sb = new StringBuilder();
        for (Location loc :
                gameEngine.getLocations()) {
            sb.append(loc.getName());
            sb.append("\n");
        }
        locationsText.setText(sb.toString());

        okBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
