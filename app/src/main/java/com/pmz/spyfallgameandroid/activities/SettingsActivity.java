package com.pmz.spyfallgameandroid.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.pmz.spyfallgameandroid.R;
import com.pmz.spyfallgameandroid.domain.GameSettings;
import com.pmz.spyfallgameandroid.util.constant.BaseConstants;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.Language.BULGARIAN;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.Language.ENGLISH;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.Language.localesMap;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.getSettings;

@EActivity
public class SettingsActivity extends BaseActivity {

    @ViewById
    EditText numberPlayers;
    @ViewById
    EditText minutes;
    @ViewById
    RadioGroup languageGroup;
    @ViewById
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setCurrentSettings();
    }

    private void setCurrentSettings() {
       numberPlayers.setText(String.valueOf(getSettings().getNumPlayers()));
       minutes.setText(String.valueOf(getSettings().getTimerMinutes()));
       languageGroup.check(selectLanguageIdRadioButton(getSettings().getLocale()));
    }

    private int selectLanguageIdRadioButton(final String language) {
        switch (language) {
            case ENGLISH:
                return R.id.englishBtn;
            case BULGARIAN:
                return R.id.bulgarianBtn;

                default:
                    return R.id.englishBtn;
        }
    }

    private void saveCurrentSettings() {
        final GameSettings gameSettings = getSettings();
        gameSettings.setNumPlayers(Integer.valueOf(numberPlayers.getText().toString()));
        gameSettings.setTimerMinutes(Integer.valueOf(minutes.getText().toString()));
        gameSettings.setLocale(localesMap.get(languageGroup.getCheckedRadioButtonId()));
        changeLanguageLocale(gameSettings.getLocale());
        Locale current = getResources().getConfiguration().locale;
    }

    @Click(R.id.backBtn)
    public void backButtonClicked() {
        saveCurrentSettings();
        openActivity(MainActivity_.class);
    }
}
