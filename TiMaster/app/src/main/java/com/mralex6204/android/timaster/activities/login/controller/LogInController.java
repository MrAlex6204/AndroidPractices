package com.mralex6204.android.timaster.activities.login.controller;

import android.content.Context;
import android.os.Handler;

import com.mralex6204.android.timaster.activities.login.view.ILogIn;
import com.mralex6204.android.timaster.domain.DbRepository;

/**
 * Created by oavera on 26/10/16.
 */

public class LogInController {
    ILogIn view;
    Context ctx;
    DbRepository dbRepository;

    public LogInController(ILogIn view, Context ctx) {
        this.view = view;
        this.ctx = ctx;

        this.dbRepository = DbRepository.getInstance(ctx);
    }

    public void logIn(String userName, String password) {
        final Handler hndl = new Handler();
        final String user = userName;
        final String pwd = password;

        view.disableInputs();
        view.showProgressBar();

        hndl.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (dbRepository.logIn(user, pwd)) {
                    view.navigateToMain();
                } else {
                    view.hideProgressBar();
                    view.enableInputs();
                }
            }
        }, 250);


    }


}
