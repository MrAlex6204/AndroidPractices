package com.mralex6204.android.androidchat.chat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mralex6204.android.androidchat.chat.events.ChatEvent;
import com.mralex6204.android.androidchat.domain.FirebaseHelper;
import com.mralex6204.android.androidchat.entities.ChatMessage;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by oavera on 28/07/16.
 */
public class ChatRepositoryImpl implements ChatRepository {
    private String recipient;
    private FirebaseHelper helper;
    private EventBus eventBus;
    private ChildEventListener chatEventListener;


    public ChatRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();

    }

    @Override
    public void sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        DatabaseReference chatReference = helper.getChatReference(this.recipient);


        chatMessage.setSender(helper.getAuthUserEmail());
        chatMessage.setMsg(msg);

        chatReference.push().setValue(chatMessage);

    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void subscribe() {

        if (chatEventListener == null) {

            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();

                    //Set true si el message es enviado por mi.
                    chatMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent();

                    chatEvent.setMessage(chatMessage);
                    eventBus.post(chatEvent);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

        }

        helper.getChatReference(this.recipient).addChildEventListener(chatEventListener);

    }

    @Override
    public void unsubscribe() {

        if (chatEventListener != null) {
            helper.getChatReference(this.recipient).removeEventListener(chatEventListener);
        }

    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}
