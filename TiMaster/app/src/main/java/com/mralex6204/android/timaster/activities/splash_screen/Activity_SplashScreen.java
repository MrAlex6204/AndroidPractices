package com.mralex6204.android.timaster.activities.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.mralex6204.android.timaster.R;
import com.mralex6204.android.timaster.activities.login.Activity_LogIn;
import com.mralex6204.android.timaster.activities.signup.Activity_SignUp;
import com.mralex6204.android.timaster.activities.splash_screen.controller.SplashScreenController;
import com.mralex6204.android.timaster.activities.splash_screen.view.ISplashScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_SplashScreen extends AppCompatActivity implements ISplashScreen {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private SplashScreenController _controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        _controller = new SplashScreenController(this, this);
        _controller.checkForInstallationConfig();

    }

    @Override
    public void navigateToLogIn() {

        startActivity(new Intent(this, Activity_LogIn.class));
        this.finish();

    }

    @Override
    public void navigateSignUp() {

        startActivity(new Intent(this, Activity_SignUp.class));
        this.finish();

    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

}
