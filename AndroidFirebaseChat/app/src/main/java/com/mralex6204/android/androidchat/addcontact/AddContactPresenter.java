package com.mralex6204.android.androidchat.addcontact;

import com.mralex6204.android.androidchat.addcontact.events.AddContactEvent;

/**
 * Created by oavera on 22/07/16.
 */
public interface AddContactPresenter {
    /*  */
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);

}
