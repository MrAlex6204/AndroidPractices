package com.mralex6204.android.androidchat.login;

import com.mralex6204.android.androidchat.login.events.LoginEvent;

/**
 * Created by oavera on 30/06/16.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email,String password);
    void registerNewUser(String email,String password);
    void onEventMainThread(LoginEvent event);


}
