package com.mralex6204.android.androidchat.chat.events;

import com.mralex6204.android.androidchat.entities.ChatMessage;

/**
 * Created by oavera on 27/07/16.
 */
public class ChatEvent {
    ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
