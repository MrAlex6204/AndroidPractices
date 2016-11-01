package com.mralex6204.android.timaster.activities.signup.controller;

import android.content.Context;
import android.os.Handler;

import com.mralex6204.android.timaster.activities.signup.view.ISignUp;
import com.mralex6204.android.timaster.domain.DbRepository;

/**
 * Created by oavera on 26/10/16.
 */

public class SignUpController {
    DbRepository dbRepository;
    ISignUp view;
    Context ctx;

    public SignUpController(ISignUp view, Context context) {
        this.view = view;
        this.ctx = context;
        dbRepository = DbRepository.getInstance(context);
    }

    public void signUp(String userName, String password, String passwordConfirm, String question, String questionAnswer) {

        if (userName == null || userName.trim() == "") {
            view.displayError("Favor instroducir tu nombre de usuario");
            return;
        }
        if (!password.trim().equals(passwordConfirm.trim())) {
            view.displayError("Favor de confirmar bien el password.");
            return;
        }
        if (question == null || question.trim() == "") {
            view.displayError("Favor de introducir la pregunta de seguridad");
            return;
        }
        if (questionAnswer == null || questionAnswer.trim() == "") {
            view.displayError("Favor de introducir la respuesta de tu pregunta de seguridad");
            return;
        }

        try {
            if (dbRepository.sigUp(userName, password, question, questionAnswer)) {
                final Handler hndl = new Handler();
                view.showProgressBar();

                hndl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.navigateToMain();
                    }
                },500);

            } else {
                view.displayError("Error:La informacion no se pudo guardar!");
            }
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }

    }


}
