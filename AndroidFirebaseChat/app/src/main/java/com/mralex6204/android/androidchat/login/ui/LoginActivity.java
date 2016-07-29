package com.mralex6204.android.androidchat.login.ui;

import android.content.Intent;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.mralex6204.android.androidchat.R;
import com.mralex6204.android.androidchat.contactlist.ui.ContactListActivity;
import com.mralex6204.android.androidchat.login.LoginPresenter;
import com.mralex6204.android.androidchat.login.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.btnSignin)
    Button btnSignIn;
    @BindView(R.id.btnSignup)
    Button btnSignUp;
    @BindView(R.id.editTxtEmail)
    EditText inputEmail;
    @BindView(R.id.editTxtPassword)
    EditText inputPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layoutMainContainer)
    RelativeLayout container;


    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();

    }

    @Override
    public void onDestroy() {

        loginPresenter.onDestroy();
        super.onDestroy();


    }


    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
        Log.d("Click","signUp");
        loginPresenter.registerNewUser(inputEmail.getText().toString(),
                inputPassword.getText().toString());

    }


    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        Log.d("Click", "signIn");
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
        this.finish();//==>Terminar la actividad para luego ya no regresar a la actividad de login.
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_sigin), error);

        inputPassword.setError(msgError);


    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_sigup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_sigup), error);

        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enable) {
        Log.d("Inputs", "entering into enable inputs");
        inputEmail.setEnabled(enable);
        inputPassword.setEnabled(enable);
        btnSignIn.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }
}
