package com.pmz.spyfallgameandroid.domain;

import com.pmz.spyfallgameandroid.util.constant.BaseConstants;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.Language.ENGLISH;

public class GameSettings {

    private int numPlayers;
    private String locale;
    private int timerMinutes;

    public GameSettings() {
        generateDefaultValues();
    }

    private void generateDefaultValues() {
        this.numPlayers = 5;
        this.locale = ENGLISH;
        this.timerMinutes = 7;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getTimerMinutes() {
        return timerMinutes;
    }

    public void setTimerMinutes(int timerMinutes) {
        this.timerMinutes = timerMinutes;
    }
}
