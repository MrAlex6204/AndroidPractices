package com.mralex6204.android.androidchat.chat.ui;

import com.mralex6204.android.androidchat.entities.ChatMessage;

/**
 * Created by oavera on 27/07/16.
 */
public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
