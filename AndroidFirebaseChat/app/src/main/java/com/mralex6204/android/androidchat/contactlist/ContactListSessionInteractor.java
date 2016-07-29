package com.mralex6204.android.androidchat.contactlist;

import com.mralex6204.android.androidchat.entities.User;

import java.util.List;

/**
 * Created by oavera on 7/07/16.
 */
public interface ContactListSessionInteractor {

    void signOff();
    String getCurrrentUserEmail();
    void changeConnectionStatus(boolean online);

}
