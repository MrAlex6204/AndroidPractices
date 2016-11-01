package com.mralex6204.android.timaster.activities.login.view;

/**
 * Created by oavera on 26/10/16.
 */

public interface ILogIn {

    void navigateToMain();
    void showProgressBar();
    void hideProgressBar();
    void disableInputs();
    void enableInputs();
    void showError(String error);

}
