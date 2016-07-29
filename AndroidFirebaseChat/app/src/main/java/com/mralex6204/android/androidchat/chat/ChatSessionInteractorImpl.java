package com.mralex6204.android.androidchat.chat;

/**
 * Created by oavera on 28/07/16.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    ChatRepository repository;

    public ChatSessionInteractorImpl(){
        this.repository = new ChatRepositoryImpl();
    }


    @Override
    public void changeConnectionStatus(boolean online) {

    }
}
