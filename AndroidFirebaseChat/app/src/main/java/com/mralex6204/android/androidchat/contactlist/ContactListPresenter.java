package com.mralex6204.android.androidchat.contactlist;

import com.mralex6204.android.androidchat.contactlist.events.ContactListEvent;
import com.mralex6204.android.androidchat.entities.User;

import java.util.List;

/**
 * Created by oavera on 7/07/16.
 */
public interface ContactListPresenter {

    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void signOff();
    String getCurrentUserEmail();
    void removedContact(String email);
    void onEventMainThread(ContactListEvent event);

}
