package com.mralex6204.android.androidchat.login;

import android.util.Log;

import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;
import com.mralex6204.android.androidchat.login.events.LoginEvent;
import com.mralex6204.android.androidchat.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by oavera on 1/07/16.
 */
public class LoginPresenterImpl implements  LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {

        loginView = null;
        this.eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        Log.d("LoginPresenterImpl","Checking for authenticated user");
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {

        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSigIn(email,password);

    }

    @Override
    public void registerNewUser(String email, String password) {
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSigUp(email,password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {

        switch (event.getEventType()){

            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverySession();
                break;


        }
    }

    private void onFailedToRecoverySession() {
        if(loginView!=null){

            loginView.hideProgress();
            loginView.enableInputs();

        }
        Log.e("LoginPresenterImpl","onFailedToRecoverySession");

    }

    private  void onSignInSuccess(){
        if(loginView!=null){

            loginView.navigateToMainScreen();

        }
    }

    private void onSignUpSuccess(){

        if(loginView!=null){

            loginView.newUserSuccess();

        }
    }

    private void onSignInError(String error){

        if(loginView!=null){

            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }

    }

    private  void onSignUpError(String error){
        if(loginView!=null){

            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);

        }
    }

}
