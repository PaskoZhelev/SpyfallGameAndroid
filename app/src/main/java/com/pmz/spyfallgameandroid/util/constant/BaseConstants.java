package com.pmz.spyfallgameandroid.util.constant;

import com.pmz.spyfallgameandroid.R;
import com.pmz.spyfallgameandroid.domain.GameSettings;

import java.util.HashMap;
import java.util.Map;

public class BaseConstants {

    public static final GameSettings settings = new GameSettings();

    public static class General {
        public static final String LOCATIONS_TEXT_FILE = "locations";
        public static final String BULGARIAN= "bg";
        public static final String SPY_ROLE = "spy";
    }

    public static class Language {
        public static final String ENGLISH = "en";
        public static final String BULGARIAN= "bg";
        public static final Map<Integer, String> localesMap = new HashMap<>();
        static {
            localesMap.put( R.id.englishBtn, ENGLISH);
            localesMap.put( R.id.bulgarianBtn, BULGARIAN);
        }
    }

    public static GameSettings getSettings() {
        return settings;
    }

}
