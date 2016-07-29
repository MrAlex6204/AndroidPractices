package com.mralex6204.android.androidchat.contactlist.ui.adapters;

import com.mralex6204.android.androidchat.entities.User;

/**
 * Created by oavera on 8/07/16.
 */
public interface OnItemClickListener {

    void onItemClick(User user);
    void onItemLongClick(User user);

}
