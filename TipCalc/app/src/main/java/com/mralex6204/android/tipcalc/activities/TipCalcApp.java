package com.mralex6204.android.tipcalc.activities;

import android.app.Application;

/**
 * Created by alex on 1/06/16.
 */
public class TipCalcApp extends Application {
    public static final String PROFILE_URL = "http://mralex6204.github.io/";

    public String getProfileUrl() {
        return PROFILE_URL;
    }


}
