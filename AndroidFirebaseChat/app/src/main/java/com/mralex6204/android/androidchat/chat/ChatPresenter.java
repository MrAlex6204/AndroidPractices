package com.mralex6204.android.androidchat.chat;

import com.mralex6204.android.androidchat.chat.events.ChatEvent;
import com.mralex6204.android.androidchat.entities.ChatMessage;

/**
 * Created by oavera on 27/07/16.
 */
public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);

}
