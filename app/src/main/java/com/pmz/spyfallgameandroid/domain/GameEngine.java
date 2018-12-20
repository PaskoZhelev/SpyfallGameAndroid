package com.pmz.spyfallgameandroid.domain;


import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pmz.spyfallgameandroid.util.constant.BaseConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.LOCATIONS_TEXT_FILE;
import static com.pmz.spyfallgameandroid.util.constant.BaseConstants.General.SPY_ROLE;

public class GameEngine {

    private List<Location> locations;
    private Location currentLocation;
    private ArrayList<String> currentRoles;
    private GameSettings settings;
    private Gson gson;
    private static final int MAX_NUM_ROLES = 7;


    public GameEngine(final GameSettings settings, final Context context) {
        this.settings = settings;
        this.gson = new Gson();
        loadLocations(settings.getLocale(), context);
    }

    public void startRound() {
        choseRandomLocation();
        generateRandomRoles();
    }

    public void choseRandomLocation() {
        Random rand = new Random();
        int num = rand.nextInt(locations.size());
        currentLocation = locations.get(num);
    }

    public void generateRandomRoles() {
        ArrayList<String> roles = new ArrayList<>();
        Collections.shuffle(currentLocation.getRoles());
        for(int i = 0; i < settings.getNumPlayers() - 1; i++) {
            roles.add(currentLocation.getRoles().get(i));
        }
        roles.add(SPY_ROLE);
        //shuffle the roles for players
        Collections.shuffle(roles);

        currentRoles = roles;
    }

    private void loadLocations(final String locale, final Context context) {
        locations = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(LOCATIONS_TEXT_FILE + "-" + locale + ".json");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            Type listType = new TypeToken<ArrayList<Location>>() {}.getType();
            locations = gson.fromJson(isr, listType);
        }
        catch (IOException error) {
            System.out.println(error);
        }
    }

    private int generateNumForRoles() {
        Random rand = new Random();
        int num = rand.nextInt(MAX_NUM_ROLES);

        return num;
    }


    public List<Location> getLocations() {
        return locations;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public ArrayList<String> getCurrentRoles() {
        return currentRoles;
    }
}
