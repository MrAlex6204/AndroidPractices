package com.mralex6204.android.androidchat.login;

/**
 * Created by oavera on 30/06/16.
 */
public interface LoginRepository {

    void signUp(String email,String password);
    void signIn(String email,String password);
    void checkSession();

}
