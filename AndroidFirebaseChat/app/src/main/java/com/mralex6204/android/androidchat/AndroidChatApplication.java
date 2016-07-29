package com.mralex6204.android.androidchat;

import android.app.Application;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by oavera on 30/06/16.
 */
public class AndroidChatApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        setupFireBase();
    }

    private void setupFireBase() {

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);


    }


}
