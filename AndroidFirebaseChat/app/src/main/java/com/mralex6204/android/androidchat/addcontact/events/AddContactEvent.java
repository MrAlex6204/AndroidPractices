package com.mralex6204.android.androidchat.addcontact.events;

/**
 * Created by oavera on 22/07/16.
 */
public class AddContactEvent {
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    boolean error = false;


}
