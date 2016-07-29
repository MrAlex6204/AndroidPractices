package com.mralex6204.android.androidchat.login;

/**
 * Created by oavera on 30/06/16.
 */
public interface LoginInteractor {

    void checkSession();
    void doSigUp(String email,String password);
    void doSigIn(String email,String password);

}
