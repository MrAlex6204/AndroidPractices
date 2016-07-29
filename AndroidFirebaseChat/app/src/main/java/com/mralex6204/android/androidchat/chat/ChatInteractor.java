package com.mralex6204.android.androidchat.chat;

/**
 * Created by oavera on 27/07/16.
 */
public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();

}
