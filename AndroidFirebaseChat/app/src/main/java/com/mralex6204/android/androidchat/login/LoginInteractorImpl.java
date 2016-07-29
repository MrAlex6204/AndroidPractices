package com.mralex6204.android.androidchat.login;

import android.util.Log;

/**
 * Created by oavera on 1/07/16.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private  LoginRepository loginRepository;

    public LoginInteractorImpl(){
        loginRepository = new LoginRepositoryImpl();
    }


    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSigUp(String email, String password) {
        loginRepository.signUp(email,password);
    }

    @Override
    public void doSigIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
