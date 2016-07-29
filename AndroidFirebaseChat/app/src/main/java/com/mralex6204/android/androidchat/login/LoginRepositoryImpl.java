package com.mralex6204.android.androidchat.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.domain.FirebaseHelper;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;
import com.mralex6204.android.androidchat.login.events.LoginEvent;

/**
 * Created by oavera on 1/07/16.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper helper;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl() {

        helper = FirebaseHelper.getInstance();
        dataReference = helper.getDataReference();
        myUserReference = helper.getMyUserReference();

        Log.d("LoginRepositoryImpl", "Instance created succesfull");

    }

    @Override
    public void signUp(final String email, final String password) {
        Log.e("LoginRepositoryImpl", "SignUp");
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.e("LoginRepositoryImpl", "Created successfully");
                        postEvent(LoginEvent.onSignUpSuccess);
                        signIn(email,password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LoginRepositoryImpl", "Failed while trying to SignUp");
                        postEvent(LoginEvent.onSignUpError,e.getMessage());
                    }
                });

    }

    @Override
    public void signIn(String email, String password) {
        Log.e("App Name:",FirebaseApp.getInstance().getName());
        Log.e("LoginRepositoryImpl", "Email:" + email + " password:" + password);

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password)
                    //Agregar evento si la authentificacion fue satisfactoria.
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.e("LoginRepositoryImpl", "Usuario Authenticado");
                            initSignIn();

                        }
                    }) //Agregar evento si hay un error al authenticar.
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("LoginRepositoryImpl", "Error al autenticar : " + e.getMessage());
                            postEvent(LoginEvent.onSignInError, e.getMessage());

                        }
                    });

        } catch (Exception ex) {
            postEvent(LoginEvent.onSignInError, ex.getMessage());
        }

    }

    @Override
    public void checkSession() {
        Log.e("LoginRepositoryImpl", "CheckSession");
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            initSignIn();//===>Realizar el SigIn en automatico
        }else {
            postEvent(LoginEvent.onFailedToRecoverSession);

        }



    }


    private  void initSignIn(){

        myUserReference = helper.getMyUserReference();
        myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);

                if (currentUser == null) {//Si aun no hay datos del usuario agregados en la Db
                    registerNewUser();
                }
                helper.changeUserConnectionStatus(User.ONLINE);//Cambiar el status a Online al usr.
                postEvent(LoginEvent.onSignInSuccess);

            }

            public void onCancelled(FirebaseError firebaseError) {

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }

        });

    }

    private void registerNewUser(){
        String email = helper.getAuthUserEmail();//Obtener el email del usr

        if (email != null) {
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBuss = GreenRobotEventBus.getInstance();
        eventBuss.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

}
