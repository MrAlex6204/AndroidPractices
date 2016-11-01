package com.mralex6204.android.timaster.activities.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mralex6204.android.timaster.R;
import com.mralex6204.android.timaster.activities.main.Activity_Main;
import com.mralex6204.android.timaster.activities.signup.controller.SignUpController;
import com.mralex6204.android.timaster.activities.signup.view.ISignUp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_SignUp extends AppCompatActivity implements ISignUp {

    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtPassword)
    TextView txtPassword;
    @BindView(R.id.txtPasswordConfirm)
    TextView txtPasswordConfirm;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    @BindView(R.id.txtAnswer)
    TextView txtQuestionAnswer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnSignup)
    Button btnSave;

    private SignUpController _controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_up);
        ButterKnife.bind(this);
        _controller = new SignUpController(this, this);
    }

    @OnClick(R.id.btnSignup)
    public void btnClick() {

        _controller.signUp(
                txtUserName.getText().toString(),
                txtPassword.getText().toString(),
                txtPasswordConfirm.getText().toString(),
                txtQuestion.getText().toString(),
                txtQuestionAnswer.getText().toString());


    }

    @Override
    public void displayError(String error) {

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
}
