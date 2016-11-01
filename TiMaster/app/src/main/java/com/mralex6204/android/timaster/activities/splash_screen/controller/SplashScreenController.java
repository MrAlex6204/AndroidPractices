package com.mralex6204.android.timaster.activities.splash_screen.controller;

import android.content.Context;
import android.os.Handler;

import com.mralex6204.android.timaster.activities.splash_screen.view.ISplashScreen;
import com.mralex6204.android.timaster.domain.DbRepository;
import com.mralex6204.android.timaster.models.Config;

/**
 * Created by oavera on 25/10/16.
 */

public class SplashScreenController {
    DbRepository dbRepository;
    ISplashScreen view;
    Context ctx;

    public SplashScreenController(ISplashScreen view, Context context) {
        this.view = view;
        this.ctx = context;
        dbRepository = DbRepository.getInstance(context);
    }

    public void checkForInstallationConfig() {
        final Handler hnd = new Handler();

        view.showProgressbar();

        //Create a delay
        hnd.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (dbRepository.getConfigName(Config.USER_NAME) == null) {
                    view.navigateSignUp();
                } else {
                    view.navigateToLogIn();
                }

            }
        }, 1000);


    }

}
