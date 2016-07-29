package com.mralex6204.android.androidchat.contactlist.ui;

import com.mralex6204.android.androidchat.entities.User;

/**
 * Created by oavera on 7/07/16.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
