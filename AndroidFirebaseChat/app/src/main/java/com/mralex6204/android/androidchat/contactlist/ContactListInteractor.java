package com.mralex6204.android.androidchat.contactlist;

/**
 * Created by oavera on 7/07/16.
 */
public interface ContactListInteractor {

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);

}


