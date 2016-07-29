package com.mralex6204.android.androidchat.contactlist;

import com.mralex6204.android.androidchat.entities.User;

import java.util.List;

/**
 * Created by oavera on 7/07/16.
 */
public interface ContactListRepository {

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void destroyListener();
    void changeConnectionStatus(boolean online);

}
