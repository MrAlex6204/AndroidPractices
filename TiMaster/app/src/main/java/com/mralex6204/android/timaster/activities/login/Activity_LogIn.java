package com.mralex6204.android.timaster.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mralex6204.android.timaster.R;
import com.mralex6204.android.timaster.activities.login.controller.LogInController;
import com.mralex6204.android.timaster.activities.login.view.ILogIn;
import com.mralex6204.android.timaster.activities.main.Activity_Main;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_LogIn extends AppCompatActivity implements ILogIn {


    @BindView(R.id.txtUserName)
    EditText txtUserName;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private LogInController _controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        ButterKnife.bind(this);

        _controller = new LogInController(this, this);

    }

    @OnClick(R.id.btnSignIn)
    public  void btnSignInClick(){
        _controller.logIn(txtUserName.getText().toString(),txtPassword.getText().toString());
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, Activity_Main.class));
        this.finish();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableInputs() {
        txtPassword.setEnabled(false);
        txtUserName.setEnabled(false);
        btnSignIn.setEnabled(false);
    }

    @Override
    public void enableInputs() {
        txtPassword.setEnabled(true);
        txtUserName.setEnabled(true);
        btnSignIn.setEnabled(true);
    }

    @Override
    public void showError(String error) {

    }
}
