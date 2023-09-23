/*******************************************************************************
 * Copyright (c) 2023 DevCloud Software. All Rights reserved.
 ******************************************************************************/

package systems.devcloud.betterapi.utils;

import systems.devcloud.betterapi.BetterAPI;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {

    private BetterAPI plugin = BetterAPI.getPlugin(BetterAPI.class);
    private String language = plugin.getConfig().getString("general.language");

    private Locale german = new Locale("de", "DE");
    private Locale english = new Locale("en", "US");

    private ResourceBundle englishLanguage = ResourceBundle.getBundle("languages/language", english);
    private ResourceBundle germanLanguage = ResourceBundle.getBundle("languages/language", german);

    public String get(String key) {
        return germanLanguage.getString(key);
    }
    public Locale getLocale() {
        if (language.equalsIgnoreCase("de")) {
            return german;
        } else {
            return english;
        }
    }
}
