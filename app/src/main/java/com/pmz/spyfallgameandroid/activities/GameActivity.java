package com.pmz.spyfallgameandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmz.spyfallgameandroid.R;
import com.pmz.spyfallgameandroid.domain.GameEngine;
import com.pmz.spyfallgameandroid.util.constant.BaseConstants;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.SPY;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.SPY_ROLE;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.UNKNOWN_LOCATION;

@EActivity
public class GameActivity extends BaseActivity {

    private GameEngine gameEngine;
    private int currentPlayer;

    @ViewById
    TextView playerNum;
    @ViewById
    TextView locationName;
    @ViewById
    TextView roleName;
    @ViewById
    ImageView locationImg;
    @ViewById
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupObjects();
        startRound();
    }

    private void setupObjects() {
        gameEngine = new GameEngine(getSettings(), getApplicationContext());
    }

    private void startRound() {
        currentPlayer = 0;
        changePlayerTextNumber();
        gameEngine.startRound();
    }

    private void changePlayerTextNumber() {
        playerNum.setText(String.valueOf(currentPlayer + 1));
    }

    private void moveToNextPlayer() {
            currentPlayer++;
    }

    private void activatePlayerLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_player_location_dialog, null);
        locationImg = (ImageView) view.findViewById(R.id.locationImg);
        locationName = (TextView) view.findViewById(R.id.locationName);
        roleName = (TextView) view.findViewById(R.id.roleName);
        okBtn = (Button) view.findViewById(R.id.okBtn);

        String role = gameEngine.getCurrentRoles().get(currentPlayer);
        if(!role.equals(SPY_ROLE)){
            changeImageView(locationImg, gameEngine.getCurrentLocation().getImage());
            locationName.setText(gameEngine.getCurrentLocation().getName());
            roleName.setText(role);
        } else {
            changeImageView(locationImg, SPY_ROLE);
            locationName.setText(UNKNOWN_LOCATION);
            roleName.setText(SPY);
        }

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOkBtn();
                dialog.dismiss();
            }
        });
    }

    @Click(R.id.seeLocationBtn)
    public void clickSeeLocationBtn() {
        activatePlayerLocationDialog();
    }


    public void clickOkBtn() {
        if (currentPlayer != getSettings().getNumPlayers() - 1) {
            moveToNextPlayer();
            changePlayerTextNumber();
        } else {
            openActivity(TimerActivity_.class);
        }

    }

}
